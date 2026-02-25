package com.sindatest.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import com.sindatest.entity.Decent;
import com.sindatest.entity.DecentSearchDTO;
import com.sindatest.service.DeclarationRechercheService;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/declarations")
public class DeclarationRechercheController {

    private final DeclarationRechercheService service;

    public DeclarationRechercheController(DeclarationRechercheService service) {
        this.service = service;
    }

    /**
     * 🔍 Recherche avancée des déclarations
     */
    @PostMapping("/search")
    public Page<Decent> rechercherDeclarations(
            @RequestBody DecentSearchDTO dto,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "sortBy", defaultValue = "dedatin") String sortBy,
            @RequestParam(value = "direction", defaultValue = "DESC") String direction) {

        Sort sort = direction.equalsIgnoreCase("ASC")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(page, size, sort);

        return service.rechercherDeclarations(dto, pageable);
    }
}
