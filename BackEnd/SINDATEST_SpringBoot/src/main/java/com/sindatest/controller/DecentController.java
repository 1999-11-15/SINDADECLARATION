package com.sindatest.controller;

import com.sindatest.entity.Decent;
import com.sindatest.entity.DecentSearchDTO;
import com.sindatest.entity.id.DecentId;
import com.sindatest.service.DecentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

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

    // ─── GET (Simple Searches) ───────────────────────────────

    /**
     * Recherche simple Mode 1 — 3 champs.
     * GET
     * /api/declarations/simple-search?debur=13&denumdec=ABC&dedatin=2024-01-15T00:00:00
     * Tous les paramètres sont optionnels, seuls les non-nuls sont pris en compte.
     */
    @GetMapping("/simple-search")
    public ResponseEntity<List<Decent>> simpleSearch(
            @RequestParam(name = "debur", required = false) Integer debur,
            @RequestParam(name = "denumdec", required = false) String denumdec,
            @RequestParam(name = "dedatin", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dedatin) {
        return ResponseEntity.ok(decentService.simpleSearch(debur, denumdec, dedatin));
    }

    /**
     * Recherche IMP/EXP Mode 2 — 4 champs.
     * GET
     * /api/declarations/impexp-search?debur=13&deimpexp=00000001&derepert=12345&dedatin=2024-01-15T00:00:00
     * Tous les paramètres sont optionnels, seuls les non-nuls sont pris en compte.
     */
    @GetMapping("/impexp-search")
    public ResponseEntity<List<Decent>> impExpSearch(
            @RequestParam(name = "debur", required = false) Integer debur,
            @RequestParam(name = "deimpexp", required = false) String deimpexp,
            @RequestParam(name = "derepert", required = false) Long derepert,
            @RequestParam(name = "dedatin", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dedatin) {
        return ResponseEntity.ok(decentService.impExpSearch(debur, deimpexp, derepert, dedatin));
    }

    // ─── POST (Search) ───────────────────────────────────────

    /**
     * Recherche multicritère dynamique avec pagination.
     * POST /api/declarations/search?page=0&size=10&sortBy=dedatin&direction=DESC
     * Body: DecentSearchDTO (JSON) — seuls les champs renseignés sont pris en
     * compte.
     */
    @PostMapping("/search")
    public ResponseEntity<Page<Decent>> search(
            @RequestBody DecentSearchDTO searchDTO,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size,
            @RequestParam(name = "sortBy", defaultValue = "dedatin") String sortBy,
            @RequestParam(name = "direction", defaultValue = "DESC") String direction) {

        Sort sort = direction.equalsIgnoreCase("ASC")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<Decent> results = decentService.searchDeclarations(searchDTO, pageable);
        return ResponseEntity.ok(results);
    }

    // ─── POST (Creation & State Changes) ─────────────────────

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
