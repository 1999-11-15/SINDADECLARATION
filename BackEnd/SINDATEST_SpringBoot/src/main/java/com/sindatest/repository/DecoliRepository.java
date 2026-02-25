package com.sindatest.repository;

import com.sindatest.entity.Decoli;
import com.sindatest.entity.id.DecoliId;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository pour la table DECOLI (colis de la déclaration).
 */
@Repository
public interface DecoliRepository extends JpaRepository<Decoli, DecoliId> {

    /** Tous les colis d'une déclaration */
    List<Decoli> findByDeburAndDeimpexpAndDerepert(Integer debur, String deimpexp, Long derepert);

    /** Total des colis d'une déclaration */
    @Query("SELECT COALESCE(SUM(c.dcnbcoli), 0) FROM Decoli c " +
           "WHERE c.debur = :bur AND c.deimpexp = :imp AND c.derepert = :rep")
    Long countTotalColis(@Param("bur") Integer bur,
                         @Param("imp") String imp,
                         @Param("rep") Long rep);

    /** Recherche par titre de transport */
    List<Decoli> findByDctitran(String dctitran);
}
