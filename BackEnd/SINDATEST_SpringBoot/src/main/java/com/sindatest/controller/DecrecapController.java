package com.sindatest.controller;

import com.sindatest.entity.Decrecap;
import com.sindatest.entity.id.DecrecapId;
import com.sindatest.service.DecrecapService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * REST Controller pour la table DECRECAP.
 * Base URL: /api/declarations/{debur}/{deimpexp}/{derepert}/recapitulatif
 */
@RestController
@RequestMapping("/api/declarations/recapitulatif")
@RequiredArgsConstructor
public class DecrecapController {

    private final DecrecapService decrecapService;

    // ─── GET (Retrieval) ─────────────────────────────────────

    @GetMapping("/search")
    public ResponseEntity<List<Decrecap>> findByDeclaration(@ModelAttribute com.sindatest.entity.id.DecentId id) {
        return ResponseEntity.ok(decrecapService.findByDeclaration(id.getDebur(), id.getDeimpexp(), id.getDerepert()));
    }

    @GetMapping("/details")
    public ResponseEntity<Decrecap> findById(@ModelAttribute DecrecapId id) {
        return ResponseEntity.ok(decrecapService.findById(id));
    }

    /** Taxes non payées */
    @GetMapping("/impayees")
    public ResponseEntity<List<Decrecap>> findImpayees(@ModelAttribute com.sindatest.entity.id.DecentId id) {
        return ResponseEntity.ok(decrecapService.findUnpaid(id.getDebur(), id.getDeimpexp(), id.getDerepert()));
    }

    /** Montant total impayé */
    @GetMapping("/montant-impaye")
    public ResponseEntity<BigDecimal> montantImpaye(@ModelAttribute com.sindatest.entity.id.DecentId id) {
        return ResponseEntity.ok(decrecapService.sumUnpaidTaxes(id.getDebur(), id.getDeimpexp(), id.getDerepert()));
    }

    // ─── POST (Creation & Actions) ───────────────────────────

    @PostMapping("/create")
    public ResponseEntity<Decrecap> create(@Valid @RequestBody Decrecap decrecap) {
        return ResponseEntity.status(HttpStatus.CREATED).body(decrecapService.create(decrecap));
    }

    // ─── Actions métier ──────────────────────────────────────

    /** Marquer une taxe comme payée */
    @PostMapping("/payer")
    public ResponseEntity<Decrecap> marquerPaye(@RequestBody DecrecapId id) {
        return ResponseEntity.ok(decrecapService.marquerPaye(id));
    }

    /** Enregistrer une quittance */
    @PostMapping("/quittance")
    public ResponseEntity<Decrecap> enregistrerQuittance(@RequestBody Map<String, Object> body) {
        Integer debur = (Integer) body.get("debur");
        String deimpexp = (String) body.get("deimpexp");
        // Handle Long conversion carefully from Map (JSON numbers might be Integer or
        // Long or Double)
        Long derepert = ((Number) body.get("derepert")).longValue();
        Integer drgtax = (Integer) body.get("drgtax");
        String numeroquittance = (String) body.get("numeroquittance");

        DecrecapId id = new DecrecapId(debur, deimpexp, derepert, drgtax);
        return ResponseEntity.ok(decrecapService.enregistrerQuittance(id, numeroquittance));
    }
}
