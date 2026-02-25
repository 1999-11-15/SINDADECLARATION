package com.sindatest.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.sindatest.entity.Decent;
import com.sindatest.entity.DecentSearchDTO;
import com.sindatest.repository.DecentRepositoryDTO;
import com.sindatest.specification.DecentSpecification;


@Service
public class DeclarationRechercheService {

    private final DecentRepositoryDTO repository;

    public DeclarationRechercheService(DecentRepositoryDTO repository) {
        this.repository = repository;
    }

    public Page<Decent> rechercherDeclarations(
            DecentSearchDTO dto,
            Pageable pageable) {

        return repository.findAll(
                DecentSpecification.advancedSearch(dto),
                pageable
        );
    }
}
