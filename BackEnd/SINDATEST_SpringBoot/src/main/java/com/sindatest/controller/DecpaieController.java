package com.sindatest.controller;

import com.sindatest.entity.Decpaie;
import com.sindatest.service.DecpaieService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * REST Controller pour la table DECPAIE.
 * Base URL: /api/declarations/{debur}/{deimpexp}/{derepert}/paiements
 */
@RestController
@RequestMapping("/api/declarations/paiements")
@RequiredArgsConstructor
public class DecpaieController {

    private final DecpaieService decpaieService;

    // ─── GET (Retrieval) ─────────────────────────────────────

    /** Tous les paiements d'une déclaration */
    @GetMapping("/search")
    public ResponseEntity<List<Decpaie>> findByDeclaration(@ModelAttribute com.sindatest.entity.id.DecentId id) {
        return ResponseEntity.ok(decpaieService.findByDeclaration(id.getDebur(), id.getDeimpexp(), id.getDerepert()));
    }

    /** Paiements par groupe taxe */
    @GetMapping("/search/groupe")
    public ResponseEntity<List<Decpaie>> findByGroupe(@ModelAttribute Decpaie criteria) {
        return ResponseEntity.ok(decpaieService.findByGroupeTaxe(criteria.getDebur(), criteria.getDeimpexp(),
                criteria.getDerepert(), criteria.getDrgtax()));
    }

    /** Montant total payé */
    @GetMapping("/total")
    public ResponseEntity<BigDecimal> montantTotal(@ModelAttribute com.sindatest.entity.id.DecentId id) {
        return ResponseEntity.ok(decpaieService.sumPayments(id.getDebur(), id.getDeimpexp(), id.getDerepert()));
    }

    /** Recherche par numéro quittance (global) */
    @GetMapping("/search/quittance")
    public ResponseEntity<List<Decpaie>> findByQuittance(@RequestParam("dpnumerop") Long dpnumerop) {
        return ResponseEntity.ok(decpaieService.findByQuittance(dpnumerop));
    }

    // ─── POST (Creation) ─────────────────────────────────────

    @PostMapping("/create")
    public ResponseEntity<Decpaie> create(@Valid @RequestBody Decpaie decpaie) {
        if (decpaie.getDpdatop() == null) {
            decpaie.setDpdatop(LocalDateTime.now());
        }
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(decpaieService.enregistrerPaiement(decpaie));
    }
}
