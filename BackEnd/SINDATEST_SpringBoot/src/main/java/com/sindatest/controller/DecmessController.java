package com.sindatest.controller;

import com.sindatest.entity.Decmess;
import com.sindatest.entity.id.DecentId;
import com.sindatest.service.DecmessService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/declarations/messages")
@RequiredArgsConstructor
public class DecmessController {

    private final DecmessService service;

    @GetMapping("/all")
    public ResponseEntity<List<Decmess>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    /**
     * Rechercher les messages via GET (Query Params).
     */
    @GetMapping("/search")
    public ResponseEntity<List<Decmess>> searchByDeclaration(@ModelAttribute DecentId id) {
        return ResponseEntity.ok(service.findByDeclaration(id.getDebur(), id.getDeimpexp(), id.getDerepert()));
    }

    @PostMapping("/all")
    public ResponseEntity<List<Decmess>> findAllPost() {
        return ResponseEntity.ok(service.findAll());
    }

    @PostMapping("/create")
    public ResponseEntity<Decmess> create(@RequestBody Decmess entity) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(entity));
    }
}
