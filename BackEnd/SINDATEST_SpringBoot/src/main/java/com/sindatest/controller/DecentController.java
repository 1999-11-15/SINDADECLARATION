package com.sindatest.controller;

import com.sindatest.entity.Decent;
import com.sindatest.entity.id.DecentId;
import com.sindatest.service.DecentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST Controller pour la table DECENT.
 * Base URL: /api/declarations
 */
@RestController
@CrossOrigin("*") // Allow all origins for testing
@RequestMapping("/api/declarations")
@RequiredArgsConstructor
public class DecentController {

    private final DecentService decentService;

    // ─── GET (Retrieval) ─────────────────────────────────────

    /** Toutes les déclarations */
    @GetMapping("/all")
    public ResponseEntity<List<Decent>> findAll() {
        return ResponseEntity.ok(decentService.findAll());
    }

    /** Par ID composite (Query Params: ?debur=10&deimpexp=X&derepert=2024) */
    @GetMapping("/details")
    public ResponseEntity<Decent> findById(@ModelAttribute DecentId id) {
        return ResponseEntity.ok(decentService.findById(id));
    }

    /** Par bureau */
    @GetMapping("/bureau")
    public ResponseEntity<List<Decent>> findByBureau(@RequestParam("debur") Integer debur) {
        return ResponseEntity.ok(decentService.findByBureau(debur));
    }

    /** Par opérateur */
    @GetMapping("/operateur")
    public ResponseEntity<List<Decent>> findByOperateur(@RequestParam("deimpexp") String deimpexp) {
        return ResponseEntity.ok(decentService.findByOperateur(deimpexp));
    }

    /** Par nature (ex: SAFA) */
    @GetMapping("/nature")
    public ResponseEntity<List<Decent>> findByNature(@RequestParam("denature") String denature) {
        return ResponseEntity.ok(decentService.findByNature(denature));
    }

    /** Déclarations actives par bureau + opérateur */
    @GetMapping("/actives")
    public ResponseEntity<List<Decent>> findActives(
            @RequestParam("debur") Integer debur,
            @RequestParam("deimpexp") String deimpexp) {
        return ResponseEntity.ok(decentService.findActiveByBureau(debur, deimpexp));
    }

    // ─── POST (Creation & State Changes) ─────────────────────

    // ─── CREATION ────────────────────────────────────────────

    /** Créer une nouvelle déclaration */
    @PostMapping("/create")
    public ResponseEntity<Decent> create(@Valid @RequestBody Decent decent) {
        Decent created = decentService.create(decent);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    // ─── Actions métier ──────────────────────────────────────

    /** Valider une déclaration */
    @PostMapping("/valider")
    public ResponseEntity<Decent> valider(@RequestBody DecentId id) {
        return ResponseEntity.ok(decentService.validerDeclaration(id));
    }

    /** Annuler une déclaration */
    @PostMapping("/annuler")
    public ResponseEntity<Decent> annuler(@RequestBody DecentId id) {
        return ResponseEntity.ok(decentService.annulerDeclaration(id));
    }
}
