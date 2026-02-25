package com.sindatest.repository;

import com.sindatest.entity.Decaffect;
import com.sindatest.entity.id.DecaffectId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DecaffectRepository
        extends JpaRepository<Decaffect, DecaffectId>, JpaSpecificationExecutor<Decaffect> {
    List<Decaffect> findByDeburAndDeimpexpAndDerepert(Integer debur, String deimpexp, Long derepert);
}
