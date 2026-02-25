package com.sindatest.controller;

import com.sindatest.entity.Decart;
import com.sindatest.service.DecartService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST Controller pour la table DECART.
 * Base URL: /api/declarations/{debur}/{deimpexp}/{derepert}/articles
 */
@RestController
@RequestMapping("/api/declarations/articles")
@RequiredArgsConstructor
public class DecartController {

    private final DecartService decartService;

    // ─── GET (Retrieval) ─────────────────────────────────────

    /** Tous les articles d'une déclaration */
    @GetMapping("/search")
    public ResponseEntity<List<Decart>> findByDeclaration(@ModelAttribute com.sindatest.entity.id.DecentId id) {
        return ResponseEntity.ok(decartService.findByDeclaration(id.getDebur(), id.getDeimpexp(), id.getDerepert()));
    }

    /** Articles actifs (non annulés) */
    @GetMapping("/search/actifs")
    public ResponseEntity<List<Decart>> findActifs(@ModelAttribute com.sindatest.entity.id.DecentId id) {
        return ResponseEntity.ok(decartService.findActiveArticles(id.getDebur(), id.getDeimpexp(), id.getDerepert()));
    }

    // ─── POST (Creation) ─────────────────────────────────────

    @PostMapping("/create")
    public ResponseEntity<Decart> create(@Valid @RequestBody Decart decart) {
        return ResponseEntity.status(HttpStatus.CREATED).body(decartService.create(decart));
    }
}
