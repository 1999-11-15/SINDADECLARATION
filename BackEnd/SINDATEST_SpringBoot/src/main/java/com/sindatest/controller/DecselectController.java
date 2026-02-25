package com.sindatest.controller;

import com.sindatest.entity.Decselect;
import com.sindatest.entity.id.DecentId;
import com.sindatest.service.DecselectService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/declarations/selection")
@RequiredArgsConstructor
public class DecselectController {

    private final DecselectService service;

    @GetMapping("/all")
    public ResponseEntity<List<Decselect>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    /**
     * Rechercher les sélections via GET (Query Params).
     */
    @GetMapping("/search")
    public ResponseEntity<List<Decselect>> searchByDeclaration(@ModelAttribute DecentId id) {
        return ResponseEntity.ok(service.findByDeclaration(id.getDebur(), id.getDeimpexp(), id.getDerepert()));
    }

    @PostMapping("/all")
    public ResponseEntity<List<Decselect>> findAllPost() {
        return ResponseEntity.ok(service.findAll());
    }

    @PostMapping("/create")
    public ResponseEntity<Decselect> create(@RequestBody Decselect entity) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(entity));
    }
}
