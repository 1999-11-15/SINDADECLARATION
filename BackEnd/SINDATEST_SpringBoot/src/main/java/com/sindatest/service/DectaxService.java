package com.sindatest.service;

import com.sindatest.entity.Dectax;
import com.sindatest.repository.DectaxRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

/**
 * Service métier pour la table DECTAX (détails taxes par article).
 */
@Service
@RequiredArgsConstructor
public class DectaxService {

    private final DectaxRepository dectaxRepository;

    // ─── Création ────────────────────────────────────────────

    @Transactional
    public Dectax create(Dectax dectax) {
        return dectaxRepository.save(dectax);
    }

    // ─── Recherches ──────────────────────────────────────────

    public List<Dectax> findByArticle(Integer debur, String deimpexp, Long derepert, Integer danumart) {
        return dectaxRepository.findByDeburAndDeimpexpAndDerepertAndDanumart(debur, deimpexp, derepert, danumart);
    }

    public List<Dectax> findByDeclaration(Integer debur, String deimpexp, Long derepert) {
        return dectaxRepository.findByDeburAndDeimpexpAndDerepert(debur, deimpexp, derepert);
    }

    public BigDecimal sumTaxesByDeclaration(Integer debur, String deimpexp, Long derepert) {
        return dectaxRepository.sumTaxesByDeclaration(debur, deimpexp, derepert);
    }
}
