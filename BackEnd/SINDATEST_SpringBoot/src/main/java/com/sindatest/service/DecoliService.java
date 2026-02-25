package com.sindatest.service;

import com.sindatest.entity.Decoli;
import com.sindatest.repository.DecoliRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service métier pour la table DECOLI (colis).
 */
@Service
@RequiredArgsConstructor
public class DecoliService {

    private final DecoliRepository decoliRepository;

    // ─── Lecture ─────────────────────────────────────────────

    @Transactional
    public Decoli create(Decoli decoli) {
        return decoliRepository.save(decoli);
    }

    // ─── Recherches ──────────────────────────────────────────

    public List<Decoli> findByDeclaration(Integer debur, String deimpexp, Long derepert) {
        return decoliRepository.findByDeburAndDeimpexpAndDerepert(debur, deimpexp, derepert);
    }

    public Long countTotalColis(Integer debur, String deimpexp, Long derepert) {
        return decoliRepository.countTotalColis(debur, deimpexp, derepert);
    }
}
