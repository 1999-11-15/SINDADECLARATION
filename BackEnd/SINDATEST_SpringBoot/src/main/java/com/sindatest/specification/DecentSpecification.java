package com.sindatest.specification;

import com.sindatest.entity.Decent;
import com.sindatest.entity.DecentSearchDTO;
import com.sindatest.entity.Decart;
import com.sindatest.entity.Decoli;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

/**
 * Spécification JPA pour la recherche avancée de déclarations.
 * Supporte les champs de l'entête (DECENT), les articles (DECART) et les colis
 * (DECOLI).
 */
public class DecentSpecification {

    public static Specification<Decent> advancedSearch(DecentSearchDTO dto) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            // ─── Champs communs aux deux modes ───────────────────────
            if (dto.getDebur() != null) {
                predicates.add(cb.equal(root.get("debur"), dto.getDebur()));
            }
            if (dto.getDerepert() != null) {
                predicates.add(cb.equal(root.get("derepert"), dto.getDerepert()));
            }

            // ─── Mode 1 : Numéro de déclaration ─────────────────────
            if (dto.getDenumdec() != null && !dto.getDenumdec().isEmpty()) {
                predicates.add(cb.equal(root.get("denumdec"), dto.getDenumdec()));
            }

            // ─── Mode 2 : IMP/EXP ────────────────────────────────────
            if (dto.getDeimpexp() != null && !dto.getDeimpexp().isEmpty()) {
                predicates.add(cb.equal(root.get("deimpexp"), dto.getDeimpexp()));
            }
            if (dto.getDedatin() != null) {
                // Recherche sur la journée entière de dedatin
                predicates.add(
                        cb.greaterThanOrEqualTo(root.get("dedatin"), dto.getDedatin().toLocalDate().atStartOfDay()));
                predicates.add(
                        cb.lessThan(root.get("dedatin"), dto.getDedatin().toLocalDate().plusDays(1).atStartOfDay()));
            }

            // ─── Recherche avancée : champs DECENT ──────────────────
            if (dto.getDetypdec() != null && !dto.getDetypdec().isEmpty()) {
                predicates.add(cb.equal(root.get("detypdec"), dto.getDetypdec()));
            }

            // Plage de dates (startDate / endDate) sur dedatin
            if (dto.getStartDate() != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("dedatin"), dto.getStartDate()));
            }
            if (dto.getEndDate() != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("dedatin"), dto.getEndDate()));
            }

            // ─── Recherche avancée : JOIN DECART ────────────────────
            boolean joinArticleNeeded = (dto.getNumeroTCE() != null && !dto.getNumeroTCE().isEmpty())
                    || (dto.getDaregdecl() != null)
                    || (dto.getDanomencl() != null && !dto.getDanomencl().isEmpty());

            if (joinArticleNeeded) {
                Join<Decent, Decart> articles = root.join("articles", JoinType.INNER);

                if (dto.getNumeroTCE() != null && !dto.getNumeroTCE().isEmpty()) {
                    try {
                        Long numTce = Long.parseLong(dto.getNumeroTCE());
                        predicates.add(cb.equal(articles.get("danumtcce"), numTce));
                    } catch (NumberFormatException e) {
                        predicates.add(cb.disjunction());
                    }
                }
                if (dto.getDaregdecl() != null) {
                    predicates.add(cb.equal(articles.get("daregdecl"), dto.getDaregdecl()));
                }
                if (dto.getDanomencl() != null && !dto.getDanomencl().isEmpty()) {
                    predicates.add(cb.equal(articles.get("danomencl"), dto.getDanomencl()));
                }
                query.distinct(true);
            }

            // ─── Recherche avancée : JOIN DECOLI ─────────────────────
            boolean colisJoinNeeded = (dto.getDcnumesc() != null) || (dto.getDcrubr() != null);

            if (colisJoinNeeded) {
                Join<Decent, Decoli> colis = root.join("colis", JoinType.INNER);

                if (dto.getDcnumesc() != null) {
                    predicates.add(cb.equal(colis.get("dcnumesc"), dto.getDcnumesc()));
                }
                if (dto.getDcrubr() != null) {
                    predicates.add(cb.equal(colis.get("dcrubr"), dto.getDcrubr()));
                }
                query.distinct(true);
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }

    /**
     * Recherche simple par numéro de déclaration (Mode 1 — 3 champs).
     * Champs : debur, denumdec, dedatin. Les champs null sont ignorés.
     * dedatin effectue une recherche sur la journée entière.
     */
    public static Specification<Decent> simpleSearch(Integer debur, String denumdec,
            java.time.LocalDateTime dedatin) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (debur != null) {
                predicates.add(cb.equal(root.get("debur"), debur));
            }
            if (denumdec != null && !denumdec.isEmpty()) {
                predicates.add(cb.equal(root.get("denumdec"), denumdec));
            }
            if (dedatin != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("dedatin"),
                        dedatin.toLocalDate().atStartOfDay()));
                predicates.add(cb.lessThan(root.get("dedatin"),
                        dedatin.toLocalDate().plusDays(1).atStartOfDay()));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }

    /**
     * Recherche IMP/EXP (Mode 2 — 4 champs).
     * Champs : debur, deimpexp, derepert, dedatin. Les champs null sont ignorés.
     * dedatin effectue une recherche sur la journée entière.
     */
    public static Specification<Decent> impExpSearch(Integer debur, String deimpexp,
            Long derepert, java.time.LocalDateTime dedatin) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (debur != null) {
                predicates.add(cb.equal(root.get("debur"), debur));
            }
            if (deimpexp != null && !deimpexp.isEmpty()) {
                predicates.add(cb.equal(root.get("deimpexp"), deimpexp));
            }
            if (derepert != null) {
                predicates.add(cb.equal(root.get("derepert"), derepert));
            }
            if (dedatin != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("dedatin"),
                        dedatin.toLocalDate().atStartOfDay()));
                predicates.add(cb.lessThan(root.get("dedatin"),
                        dedatin.toLocalDate().plusDays(1).atStartOfDay()));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
