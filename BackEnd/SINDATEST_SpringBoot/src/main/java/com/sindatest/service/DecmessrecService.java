package com.sindatest.service;

import com.sindatest.entity.Decmessrec;

import com.sindatest.repository.DecmessrecRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class DecmessrecService {

    private final DecmessrecRepository repository;

    public List<Decmessrec> findAll() {
        return repository.findAll();
    }

    public List<Decmessrec> findByDeclaration(Integer debur, String deimpexp, Long derepert) {
        return repository.findByDeburAndDeimpexpAndDerepert(debur, deimpexp, derepert);
    }

    public Decmessrec create(Decmessrec entity) {
        return repository.save(entity);
    }
}
