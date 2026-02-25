package com.sindatest.service;

import com.sindatest.entity.Decart;
import com.sindatest.repository.DecartRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service métier pour la table DECART (articles).
 */
@Service
@RequiredArgsConstructor
public class DecartService {

    private final DecartRepository decartRepository;

    // ─── Lecture ─────────────────────────────────────────────

    public List<Decart> findAll() {
        return decartRepository.findAll();
    }

    @Transactional
    public Decart create(Decart decart) {
        return decartRepository.save(decart);
    }

    // ─── Recherches par déclaration ──────────────────────────

    public List<Decart> findByDeclaration(Integer debur, String deimpexp, Long derepert) {
        return decartRepository.findByDeburAndDeimpexpAndDerepert(debur, deimpexp, derepert);
    }

    public List<Decart> findActiveArticles(Integer debur, String deimpexp, Long derepert) {
        return decartRepository.findActiveArticles(debur, deimpexp, derepert);
    }
}
