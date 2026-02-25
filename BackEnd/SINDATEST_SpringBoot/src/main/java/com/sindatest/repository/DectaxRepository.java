package com.sindatest.repository;

import com.sindatest.entity.Dectax;
import com.sindatest.entity.id.DectaxId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

/**
 * Repository pour la table DECTAX (détails taxes par article).
 */
@Repository
public interface DectaxRepository extends JpaRepository<Dectax, DectaxId> {

    /** Toutes les taxes d'un article */
    List<Dectax> findByDeburAndDeimpexpAndDerepertAndDanumart(
            Integer debur, String deimpexp, Long derepert, Integer danumart);

    /** Toutes les taxes d'une déclaration */
    List<Dectax> findByDeburAndDeimpexpAndDerepert(Integer debur, String deimpexp, Long derepert);

    /** Taxes par code taxe */
    List<Dectax> findByDtcodtax(Integer dtcodtax);

    /** Montant total des taxes d'une déclaration */
    @Query("SELECT COALESCE(SUM(t.dtmtaxe), 0) FROM Dectax t " +
           "WHERE t.debur = :bur AND t.deimpexp = :imp AND t.derepert = :rep")
    BigDecimal sumTaxesByDeclaration(@Param("bur") Integer bur,
                                     @Param("imp") String imp,
                                     @Param("rep") Long rep);
}
