package com.sindatest.repository;

import com.sindatest.entity.Decmessrec;
import com.sindatest.entity.id.DecmessrecId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DecmessrecRepository
        extends JpaRepository<Decmessrec, DecmessrecId>, JpaSpecificationExecutor<Decmessrec> {
    List<Decmessrec> findByDeburAndDeimpexpAndDerepert(Integer debur, String deimpexp, Long derepert);
}
