package com.sindatest.service;

import com.sindatest.entity.Decpaie;
import com.sindatest.repository.DecpaieRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

/**
 * Service métier pour la table DECPAIE (paiements).
 */
@Service
@RequiredArgsConstructor
public class DecpaieService {

    private final DecpaieRepository decpaieRepository;

    // ─── Création ────────────────────────────────────────────

    @Transactional
    public Decpaie enregistrerPaiement(Decpaie decpaie) {
        return decpaieRepository.save(decpaie);
    }

    // ─── Recherches ──────────────────────────────────────────

    public List<Decpaie> findByDeclaration(Integer debur, String deimpexp, Long derepert) {
        return decpaieRepository.findByDeburAndDeimpexpAndDerepert(debur, deimpexp, derepert);
    }

    public List<Decpaie> findByGroupeTaxe(Integer debur, String deimpexp, Long derepert, Integer drgtax) {
        return decpaieRepository.findByDeburAndDeimpexpAndDerepertAndDrgtax(debur, deimpexp, derepert, drgtax);
    }

    public BigDecimal sumPayments(Integer debur, String deimpexp, Long derepert) {
        return decpaieRepository.sumPayments(debur, deimpexp, derepert);
    }

    public List<Decpaie> findByQuittance(Long numeroquittance) {
        return decpaieRepository.findByDpnumerop(numeroquittance);
    }
}
