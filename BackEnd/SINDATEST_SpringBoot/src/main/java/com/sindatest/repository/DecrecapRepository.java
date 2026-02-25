package com.sindatest.repository;

import com.sindatest.entity.Decrecap;
import com.sindatest.entity.id.DecrecapId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

/**
 * Repository pour la table DECRECAP (récapitulatif taxes par groupe).
 */
@Repository
public interface DecrecapRepository extends JpaRepository<Decrecap, DecrecapId> {

    /** Tous les récapitulatifs d'une déclaration */
    List<Decrecap> findByDeburAndDeimpexpAndDerepert(Integer debur, String deimpexp, Long derepert);

    /** Récapitulatif par groupe de taxe spécifique */
    Decrecap findByDeburAndDeimpexpAndDerepertAndDrgtax(
            Integer debur, String deimpexp, Long derepert, Integer drgtax);

    /** Taxes non payées d'une déclaration */
    @Query("SELECT r FROM Decrecap r WHERE r.debur = :bur AND r.deimpexp = :imp " +
           "AND r.derepert = :rep AND r.drflag = 0")
    List<Decrecap> findUnpaid(@Param("bur") Integer bur,
                              @Param("imp") String imp,
                              @Param("rep") Long rep);

    /** Montant total impayé d'une déclaration */
    @Query("SELECT COALESCE(SUM(r.drmgtax), 0) FROM Decrecap r " +
           "WHERE r.debur = :bur AND r.deimpexp = :imp AND r.derepert = :rep AND r.drflag = 0")
    BigDecimal sumUnpaidTaxes(@Param("bur") Integer bur,
                              @Param("imp") String imp,
                              @Param("rep") Long rep);
}
