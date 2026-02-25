package com.sindatest.controller;

import com.sindatest.entity.Decmessrec;
import com.sindatest.entity.id.DecentId;
import com.sindatest.service.DecmessrecService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/declarations/messages-recevabilite")
@RequiredArgsConstructor
public class DecmessrecController {

    private final DecmessrecService service;

    @GetMapping("/all")
    public ResponseEntity<List<Decmessrec>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    /**
     * Rechercher les messages (recevabilité) via GET (Query Params).
     */
    @GetMapping("/search")
    public ResponseEntity<List<Decmessrec>> searchByDeclaration(@ModelAttribute DecentId id) {
        return ResponseEntity.ok(service.findByDeclaration(id.getDebur(), id.getDeimpexp(), id.getDerepert()));
    }

    @PostMapping("/all")
    public ResponseEntity<List<Decmessrec>> findAllPost() {
        return ResponseEntity.ok(service.findAll());
    }

    @PostMapping("/create")
    public ResponseEntity<Decmessrec> create(@RequestBody Decmessrec entity) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(entity));
    }
}
