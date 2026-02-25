package com.sindatest.repository;

import com.sindatest.entity.Decmess;
import com.sindatest.entity.id.DecmessId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DecmessRepository extends JpaRepository<Decmess, DecmessId>, JpaSpecificationExecutor<Decmess> {
    List<Decmess> findByDeburAndDeimpexpAndDerepert(Integer debur, String deimpexp, Long derepert);
}
