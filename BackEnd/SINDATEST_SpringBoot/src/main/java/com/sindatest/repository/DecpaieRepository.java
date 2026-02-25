package com.sindatest.repository;

import com.sindatest.entity.Decpaie;
import com.sindatest.entity.id.DecpaieId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

/**
 * Repository pour la table DECPAIE (paiements de la déclaration).
 */
@Repository
public interface DecpaieRepository extends JpaRepository<Decpaie, DecpaieId> {

    /** Tous les paiements d'une déclaration */
    List<Decpaie> findByDeburAndDeimpexpAndDerepert(Integer debur, String deimpexp, Long derepert);

    /** Paiements par groupe de taxe */
    List<Decpaie> findByDeburAndDeimpexpAndDerepertAndDrgtax(
            Integer debur, String deimpexp, Long derepert, Integer drgtax);

    /** Paiements par nature */
    List<Decpaie> findByDpnatpaie(String dpnatpaie);

    /** Montant total payé pour une déclaration */
    @Query("SELECT COALESCE(SUM(p.dpmontpaie), 0) FROM Decpaie p " +
           "WHERE p.debur = :bur AND p.deimpexp = :imp AND p.derepert = :rep")
    BigDecimal sumPayments(@Param("bur") Integer bur,
                           @Param("imp") String imp,
                           @Param("rep") Long rep);

    /** Paiement par numéro de quittance */
    List<Decpaie> findByDpnumerop(Long dpnumerop);
}
