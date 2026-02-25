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
 * Supporte les champs de l'entête (DECENT) et des articles (DECART).
 */
public class DecentSpecification {

    public static Specification<Decent> advancedSearch(DecentSearchDTO dto) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            // ─── Identification déclaration (DECENT) ────────────────//////
            if (dto.getDebur() != null) {
                predicates.add(cb.equal(root.get("debur"), dto.getDebur()));
            }
            if (dto.getDeimpexp() != null && !dto.getDeimpexp().isEmpty()) {
                predicates.add(cb.equal(root.get("deimpexp"), dto.getDeimpexp()));
            }
            if (dto.getDerepert() != null) {
                predicates.add(cb.equal(root.get("derepert"), dto.getDerepert()));
            }

            // ─── Déclaration Fields (DECENT) ──────────────────────
            if (dto.getCodeDeclaration() != null && !dto.getCodeDeclaration().isEmpty()) {
                // Maps to denumdec (String)
                predicates.add(cb.equal(root.get("denumdec"), dto.getCodeDeclaration()));
            }
            if (dto.getDetypdec() != null && !dto.getDetypdec().isEmpty()) {
                predicates.add(cb.equal(root.get("detypdec"), dto.getDetypdec()));
            }
            if (dto.getDedatin() != null) {
                predicates.add(cb.equal(root.get("dedatin"), dto.getDedatin()));
            }

            // Date Range for dedatin
            if (dto.getStartDate() != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("dedatin"), dto.getStartDate()));
            }
            if (dto.getEndDate() != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("dedatin"), dto.getEndDate()));
            }

            // ─── Article Fields (DECART - JOIN) ───────────────────
            boolean joinNeeded = (dto.getNumeroTCE() != null && !dto.getNumeroTCE().isEmpty())
                    || (dto.getDaregdecl() != null)
                    || (dto.getDanomencl() != null && !dto.getDanomencl().isEmpty());

            if (joinNeeded) {
                // Join avec DECART
                Join<Decent, Decart> articles = root.join("articles", JoinType.INNER);

                // numeroTCE (String dans DTO) -> danumtcce (Long dans DB)
                if (dto.getNumeroTCE() != null && !dto.getNumeroTCE().isEmpty()) {
                    try {
                        Long numTce = Long.parseLong(dto.getNumeroTCE());
                        predicates.add(cb.equal(articles.get("danumtcce"), numTce));
                    } catch (NumberFormatException e) {
                        // Si le format n'est pas numérique, impossible de trouver une correspondance
                        predicates.add(cb.disjunction());
                    }
                }

                if (dto.getDaregdecl() != null) {
                    predicates.add(cb.equal(articles.get("daregdecl"), dto.getDaregdecl()));
                }

                if (dto.getDanomencl() != null && !dto.getDanomencl().isEmpty()) {
                    predicates.add(cb.equal(articles.get("danomencl"), dto.getDanomencl()));
                }
                // Éviter les doublons de DECENT si plusieurs articles correspondent
                query.distinct(true);
            }

            // ─── Colis Fields (DECOLI - JOIN) ──────────────────────
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
}
