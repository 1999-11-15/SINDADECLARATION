package com.sindatest.service;

import com.sindatest.entity.Decmess;

import com.sindatest.repository.DecmessRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class DecmessService {

    private final DecmessRepository repository;

    public List<Decmess> findAll() {
        return repository.findAll();
    }

    public List<Decmess> findByDeclaration(Integer debur, String deimpexp, Long derepert) {
        return repository.findByDeburAndDeimpexpAndDerepert(debur, deimpexp, derepert);
    }

    public Decmess create(Decmess entity) {
        return repository.save(entity);
    }
}
