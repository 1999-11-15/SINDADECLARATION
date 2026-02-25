package com.sindatest.repository;

import com.sindatest.entity.Decent;
import com.sindatest.entity.id.DecentId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository pour la table DECENT (en-tête déclaration).
 */
@Repository
public interface DecentRepository extends JpaRepository<Decent, DecentId>, JpaSpecificationExecutor<Decent> {

    /** Recherche par bureau de dédouanement */
    List<Decent> findByDebur(Integer debur);

    /** Recherche par opérateur (code douane) */
    List<Decent> findByDeimpexp(String deimpexp);

    /** Recherche par type de déclaration */
    List<Decent> findByDetypdec(String detypdec);

    /** Recherche par nature (ex : SAFA) */
    List<Decent> findByDenature(String denature);

    /** Déclarations non annulées */
    List<Decent> findByDeannulNot(Integer deannul);

    /** Déclarations validées */
    List<Decent> findByDevalid(Integer devalid);

    /** Recherche par numéro déclaration serial */
    Decent findByDenumdec(String denumdec);

    /** Recherche avec critères multiples (JPQL) */
    @Query("SELECT d FROM Decent d WHERE d.debur = :bur AND d.deimpexp = :imp AND d.deannul != 1")
    List<Decent> findActiveByBureau(@Param("bur") Integer bur, @Param("imp") String imp);
}
