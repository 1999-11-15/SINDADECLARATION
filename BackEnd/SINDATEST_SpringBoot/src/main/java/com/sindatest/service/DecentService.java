package com.sindatest.service;

import com.sindatest.entity.Decart;
import com.sindatest.entity.Decent;
import com.sindatest.entity.Decoli;
import com.sindatest.entity.Dectax;
import com.sindatest.entity.id.DecentId;
import com.sindatest.repository.DecentRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Service métier pour la table DECENT.
 * Gère : création, validation, annulation de la déclaration.
 */
@Service
@RequiredArgsConstructor
public class DecentService {

    private final DecentRepository decentRepository;

    // ─── Lecture ─────────────────────────────────────────────

    public List<Decent> findAll() {
        return decentRepository.findAll();
    }

    public Decent findById(DecentId id) {
        return decentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Déclaration non trouvée : " + id));
    }

    // ─── Création ────────────────────────────────────────────

    @Transactional
    public Decent create(Decent decent) {

        if (decent.getDedatin() == null) {
            decent.setDedatin(LocalDateTime.now());
        }

        if (decent.getDeannul() == null) {
            decent.setDeannul(0);
        }

        // Auto-fill child keys and relations
        if (decent.getArticles() != null) {
            decent.setDetotart(decent.getArticles().size());
            for (int i = 0; i < decent.getArticles().size(); i++) {
                Decart article = decent.getArticles().get(i);
                article.setDecent(decent);
                article.setDebur(decent.getDebur());
                article.setDeimpexp(decent.getDeimpexp());
                article.setDerepert(decent.getDerepert());

                if (article.getDanumart() == null) {
                    article.setDanumart(i + 1);
                }

                if (article.getTaxes() != null) {
                    for (int j = 0; j < article.getTaxes().size(); j++) {
                        Dectax tax = article.getTaxes().get(j);
                        tax.setDecart(article);
                        tax.setDebur(decent.getDebur());
                        tax.setDeimpexp(decent.getDeimpexp());
                        tax.setDerepert(decent.getDerepert());
                        tax.setDanumart(article.getDanumart());
                    }
                }
            }
        }

        if (decent.getColis() != null) {
            for (int i = 0; i < decent.getColis().size(); i++) {
                Decoli colis = decent.getColis().get(i);
                colis.setDecent(decent);
                colis.setDebur(decent.getDebur());
                colis.setDeimpexp(decent.getDeimpexp());
                colis.setDerepert(decent.getDerepert());

                if (colis.getDcnumlm() == null) {
                    colis.setDcnumlm(i + 1);
                }
            }
        }

        if (decent.getPaiements() != null) {
            decent.getPaiements().forEach(p -> {
                p.setDecent(decent);
                p.setDebur(decent.getDebur());
                p.setDeimpexp(decent.getDeimpexp());
                p.setDerepert(decent.getDerepert());
            });
        }

        if (decent.getRecapitulatifs() != null) {
            decent.getRecapitulatifs().forEach(r -> {
                r.setDecent(decent);
                r.setDebur(decent.getDebur());
                r.setDeimpexp(decent.getDeimpexp());
                r.setDerepert(decent.getDerepert());
            });
        }

        return decentRepository.save(decent);
    }

    // ─── Logique métier ──────────────────────────────────────

    @Transactional
    public Decent validerDeclaration(DecentId id) {
        Decent decent = findById(id);
        if (decent.getDevalid() != null && decent.getDevalid() == 1) {
            throw new RuntimeException("La déclaration est déjà validée.");
        }
        if (decent.getDeannul() != null && decent.getDeannul() == 1) {
            throw new RuntimeException("Impossible de valider une déclaration annulée.");
        }
        decent.setDevalid(1);
        decent.setDedatvali(LocalDateTime.now());
        return decentRepository.save(decent);
    }

    @Transactional
    public Decent annulerDeclaration(DecentId id) {
        Decent decent = findById(id);
        if (decent.getDeannul() != null && decent.getDeannul() == 1) {
            throw new RuntimeException("La déclaration est déjà annulée.");
        }
        decent.setDeannul(1);
        return decentRepository.save(decent);
    }

    // ─── Recherches ──────────────────────────────────────────

    public List<Decent> findByBureau(Integer debur) {
        return decentRepository.findByDebur(debur);
    }

    public List<Decent> findByOperateur(String deimpexp) {
        return decentRepository.findByDeimpexp(deimpexp);
    }

    public List<Decent> findByNature(String denature) {
        return decentRepository.findByDenature(denature);
    }

    public List<Decent> findActiveByBureau(Integer debur, String deimpexp) {
        return decentRepository.findActiveByBureau(debur, deimpexp);
    }
}
