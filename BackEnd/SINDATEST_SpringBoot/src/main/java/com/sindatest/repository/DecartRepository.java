package com.sindatest.repository;

import com.sindatest.entity.Decart;
import com.sindatest.entity.id.DecartId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository pour la table DECART (articles de la déclaration).
 */
@Repository
public interface DecartRepository extends JpaRepository<Decart, DecartId> {

    /** Tous les articles d'une déclaration */
    List<Decart> findByDeburAndDeimpexpAndDerepert(Integer debur, String deimpexp, Long derepert);

    /** Articles par nomenclature */
    List<Decart> findByDanomencl(String danomencl);

    /** Articles par pays d'origine */
    List<Decart> findByDapyorigi(Integer dapyorigi);

    /** Articles par régime de déclaration */
    List<Decart> findByDaregdecl(Integer daregdecl);

    /** Articles non annulés d'une déclaration */
    @Query("SELECT a FROM Decart a WHERE a.debur = :bur AND a.deimpexp = :imp " +
           "AND a.derepert = :rep AND a.dannulat != 1")
    List<Decart> findActiveArticles(@Param("bur") Integer bur,
                                    @Param("imp") String imp,
                                    @Param("rep") Long rep);
}
