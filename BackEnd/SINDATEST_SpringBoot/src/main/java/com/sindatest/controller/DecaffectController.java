package com.sindatest.controller;

import com.sindatest.entity.Decaffect;
import com.sindatest.entity.id.DecentId;
import com.sindatest.service.DecaffectService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/declarations/affectation")
@RequiredArgsConstructor
public class DecaffectController {

    private final DecaffectService service;

    @GetMapping("/all")
    public ResponseEntity<List<Decaffect>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    /**
     * Rechercher les affectations via GET (Query Params).
     */
    @GetMapping("/search")
    public ResponseEntity<List<Decaffect>> searchByDeclaration(@ModelAttribute DecentId id) {
        return ResponseEntity.ok(service.findByDeclaration(id.getDebur(), id.getDeimpexp(), id.getDerepert()));
    }

    @PostMapping("/all")
    public ResponseEntity<List<Decaffect>> findAllPost() {
        return ResponseEntity.ok(service.findAll());
    }

    @PostMapping("/create")
    public ResponseEntity<Decaffect> create(@RequestBody Decaffect entity) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(entity));
    }
}
