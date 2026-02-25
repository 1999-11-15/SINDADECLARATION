package com.sindatest.service;

import com.sindatest.entity.Decselect;

import com.sindatest.repository.DecselectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class DecselectService {

    private final DecselectRepository repository;

    public List<Decselect> findAll() {
        return repository.findAll();
    }

    public List<Decselect> findByDeclaration(Integer debur, String deimpexp, Long derepert) {
        return repository.findByDeburAndDeimpexpAndDerepert(debur, deimpexp, derepert);
    }

    public Decselect create(Decselect entity) {
        return repository.save(entity);
    }
}
