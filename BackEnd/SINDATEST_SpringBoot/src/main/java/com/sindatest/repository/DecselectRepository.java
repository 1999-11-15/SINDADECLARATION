package com.sindatest.repository;

import com.sindatest.entity.Decselect;
import com.sindatest.entity.id.DecselectId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DecselectRepository
        extends JpaRepository<Decselect, DecselectId>, JpaSpecificationExecutor<Decselect> {
    List<Decselect> findByDeburAndDeimpexpAndDerepert(Integer debur, String deimpexp, Long derepert);
}
