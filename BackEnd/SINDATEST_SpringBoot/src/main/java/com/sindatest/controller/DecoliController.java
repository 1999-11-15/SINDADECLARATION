package com.sindatest.controller;

import com.sindatest.entity.Decoli;
import com.sindatest.service.DecoliService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST Controller pour la table DECOLI.
 * Base URL: /api/declarations/{debur}/{deimpexp}/{derepert}/colis
 */
@RestController
@RequestMapping("/api/declarations/colis")
@RequiredArgsConstructor
public class DecoliController {

    private final DecoliService decoliService;

    // ─── GET (Retrieval) ─────────────────────────────────────

    @GetMapping("/search")
    public ResponseEntity<List<Decoli>> findByDeclaration(@ModelAttribute com.sindatest.entity.id.DecentId id) {
        return ResponseEntity.ok(decoliService.findByDeclaration(id.getDebur(), id.getDeimpexp(), id.getDerepert()));
    }

    /** Total des colis */
    @GetMapping("/total")
    public ResponseEntity<Long> countTotal(@ModelAttribute com.sindatest.entity.id.DecentId id) {
        return ResponseEntity.ok(decoliService.countTotalColis(id.getDebur(), id.getDeimpexp(), id.getDerepert()));
    }

    // ─── POST (Creation) ─────────────────────────────────────

    @PostMapping("/create")
    public ResponseEntity<Decoli> create(@Valid @RequestBody Decoli decoli) {
        // Assuming decoli has all keys set
        return ResponseEntity.status(HttpStatus.CREATED).body(decoliService.create(decoli));
    }
}
