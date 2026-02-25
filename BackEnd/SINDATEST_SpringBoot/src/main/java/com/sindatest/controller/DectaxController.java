package com.sindatest.controller;

import com.sindatest.entity.Dectax;
import com.sindatest.service.DectaxService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

/**
 * REST Controller pour la table DECTAX.
 * Base URL:
 * /api/declarations/{debur}/{deimpexp}/{derepert}/articles/{danumart}/taxes
 */
@RestController
@RequestMapping("/api/declarations/taxes")
@RequiredArgsConstructor
public class DectaxController {

    private final DectaxService dectaxService;

    // ─── GET (Retrieval) ─────────────────────────────────────

    /** Taxes d'un article */
    @GetMapping("/search")
    public ResponseEntity<List<Dectax>> findByArticle(@ModelAttribute com.sindatest.entity.id.DecartId id) {
        return ResponseEntity
                .ok(dectaxService.findByArticle(id.getDebur(), id.getDeimpexp(), id.getDerepert(), id.getDanumart()));
    }

    /** Total taxes de toute la déclaration */
    @GetMapping("/total-declaration")
    public ResponseEntity<BigDecimal> sumTaxes(@ModelAttribute com.sindatest.entity.id.DecentId id) {
        return ResponseEntity
                .ok(dectaxService.sumTaxesByDeclaration(id.getDebur(), id.getDeimpexp(), id.getDerepert()));
    }

    // ─── POST (Creation) ─────────────────────────────────────

    @PostMapping("/create")
    public ResponseEntity<Dectax> create(@Valid @RequestBody Dectax dectax) {
        return ResponseEntity.status(HttpStatus.CREATED).body(dectaxService.create(dectax));
    }
}
