package com.sindatest.repository;

import com.sindatest.entity.Decent;
import com.sindatest.entity.id.DecentId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface DecentRepositoryDTO
        extends JpaRepository<Decent, DecentId>,
                JpaSpecificationExecutor<Decent> {
}
