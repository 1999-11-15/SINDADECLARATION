package com.sindatest.service;

import com.sindatest.entity.Decaffect;

import com.sindatest.repository.DecaffectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class DecaffectService {

    private final DecaffectRepository repository;

    public List<Decaffect> findAll() {
        return repository.findAll();
    }

    public List<Decaffect> findByDeclaration(Integer debur, String deimpexp, Long derepert) {
        return repository.findByDeburAndDeimpexpAndDerepert(debur, deimpexp, derepert);
    }

    public Decaffect create(Decaffect entity) {
        return repository.save(entity);
    }
}
