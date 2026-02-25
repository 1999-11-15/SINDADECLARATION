package com.sindatest.service;

import com.sindatest.entity.Decrecap;
import com.sindatest.entity.id.DecrecapId;
import com.sindatest.repository.DecrecapRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

/**
 * Service métier pour la table DECRECAP (récapitulatif taxes).
 *
 * DRFLAG valeurs :
 * 0 = Non payé
 * 1 = Payé
 * 5 = Crédit enlevage
 * 7 = Consignation (688)
 */
@Service
@RequiredArgsConstructor
public class DecrecapService {

    private final DecrecapRepository decrecapRepository;

    // ─── Lecture ─────────────────────────────────────────────

    public Decrecap findById(DecrecapId id) {
        return decrecapRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Récapitulatif non trouvé : " + id));
    }

    @Transactional
    public Decrecap create(Decrecap decrecap) {
        return decrecapRepository.save(decrecap);
    }

    // ─── Logique métier ──────────────────────────────────────

    @Transactional
    public Decrecap marquerPaye(DecrecapId id) {
        Decrecap recap = findById(id);
        recap.setDrflag(1);
        return decrecapRepository.save(recap);
    }

    @Transactional
    public Decrecap enregistrerQuittance(DecrecapId id, String numeroquittance) {
        Decrecap recap = findById(id);
        recap.setDrorigine(numeroquittance);
        return decrecapRepository.save(recap);
    }

    // ─── Recherches ──────────────────────────────────────────

    public List<Decrecap> findByDeclaration(Integer debur, String deimpexp, Long derepert) {
        return decrecapRepository.findByDeburAndDeimpexpAndDerepert(debur, deimpexp, derepert);
    }

    public List<Decrecap> findUnpaid(Integer debur, String deimpexp, Long derepert) {
        return decrecapRepository.findUnpaid(debur, deimpexp, derepert);
    }

    public BigDecimal sumUnpaidTaxes(Integer debur, String deimpexp, Long derepert) {
        return decrecapRepository.sumUnpaidTaxes(debur, deimpexp, derepert);
    }
}
