package com.sindatest.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sindatest.entity.id.DecartId;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Entity pour la table DECART — Articles de la déclaration douanière.
 * Schema Oracle: SINDATEST.DECART
 * Relation: N:1 vers DECENT (via DEBUR + DEIMPEXP + DEREPERT)
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "DECART", schema = "SINDATEST")
@IdClass(DecartId.class)
public class Decart {

    // ─── Clé primaire composite ──────────────────────────────
    @Id
    @Column(name = "DEBUR", precision = 2, scale = 0, nullable = false)
    private Integer debur;

    @Id
    @Column(name = "DEIMPEXP", length = 8, nullable = false)
    private String deimpexp;

    @Id
    @Column(name = "DEREPERT", precision = 8, scale = 0, nullable = false)
    private Long derepert;

    @Id
    @Column(name = "DANUMART", precision = 3, scale = 0, nullable = false)
    private Integer danumart; // Numéro d'ordre article

    // ─── Nomenclature & régimes ──────────────────────────────
    @Column(name = "DANOMENCL", length = 11)
    private String danomencl; // Nomenclature produit/article

    @Column(name = "DAREGDECL", precision = 3, scale = 0)
    private Integer daregdecl; // Code régime déclaration

    @Column(name = "DAREGTRAN", precision = 3, scale = 0)
    private Integer daregtran; // Code régime transit intérieur

    @Column(name = "DAREGPREC", precision = 3, scale = 0)
    private Integer daregprec; // Code régime douanier précédent

    @Column(name = "DAPYORIGI", precision = 3, scale = 0)
    private Integer dapyorigi; // Code pays d'origine

    // ─── Ajustement & fiscalité ──────────────────────────────
    @Column(name = "DACFAJUST", precision = 6, scale = 5)
    private BigDecimal dacfajust; // Taux coefficient ajustement

    @Column(name = "DAQALFISC", precision = 1, scale = 0)
    private Integer daqalfisc; // Code qualité fiscale (1, 2, 9)

    @Column(name = "DAIMPSPEC", precision = 3, scale = 0)
    private Integer daimpspec; // Code imposition spéciale

    @Column(name = "DAREGLEMF", precision = 2, scale = 0)
    private Integer dareglemf; // Code statistiques

    // ─── Quantités ───────────────────────────────────────────
    @Column(name = "DACODQCS", precision = 2, scale = 0)
    private Integer dacodqcs; // Code quantité complémentaire statistique

    @Column(name = "DAQCS", precision = 9, scale = 0)
    private Long daqcs; // Quantité complémentaire statistique

    @Column(name = "DAPOIBRUT", precision = 10, scale = 0)
    private Long dapoibrut; // Poids brut (KG)

    @Column(name = "DAPOINET", precision = 10, scale = 0)
    private Long dapoinet; // Poids net (KG)

    @Column(name = "DACODQCI", precision = 2, scale = 0)
    private Integer dacodqci; // Code quantité complémentaire imposition

    @Column(name = "DAQCI", precision = 9, scale = 0)
    private Long daqci; // Quantité complémentaire imposition

    // ─── Valeurs financières ─────────────────────────────────
    @Column(name = "DAPLUVALU", precision = 13, scale = 3)
    private BigDecimal dapluvalu; // Plus-value en dinars

    @Column(name = "DAVALFOB", precision = 11, scale = 3)
    private BigDecimal davalfob; // Valeur FOB en dinars

    @Column(name = "DAVALD", precision = 12, scale = 3)
    private BigDecimal davald; // Valeur en douane en dinars

    @Column(name = "DAPFN", precision = 14, scale = 3)
    private BigDecimal dapfn; // Prix facture net en devise

    // ─── Documents ───────────────────────────────────────────
    @Column(name = "DADOC1", precision = 3, scale = 0)
    private Integer dadoc1; // Code document R.P.F

    @Column(name = "DADOC2_3", precision = 6, scale = 0)
    private Long dadoc2_3; // Numéro document R.F.P

    @Column(name = "DADOC4", precision = 3, scale = 0)
    private Integer dadoc4; // Rubrique document R.F.P

    @Column(name = "DADOC5", precision = 3, scale = 0)
    private Integer dadoc5; // Code document tarif préférentiel

    @Column(name = "DADOC6", precision = 3, scale = 0)
    private Integer dadoc6; // Groupe autres documents

    // ─── TCE ─────────────────────────────────────────────────
    @Column(name = "DACODTCCE", precision = 3, scale = 0)
    private Integer dacodtcce; // Code TITRE CCE

    @Column(name = "DANUMTCCE", precision = 7, scale = 0)
    private Long danumtcce; // Numéro TITRE CCE

    // ─── Domiciliation bancaire ──────────────────────────────
    @Column(name = "DAREGLEM", precision = 2, scale = 0)
    private Integer dareglem; // Code règlement financier

    @Column(name = "DADELAI", precision = 2, scale = 0)
    private Integer dadelai; // Code délai règlement

    @Column(name = "DACODBANC", precision = 2, scale = 0)
    private Integer dacodbanc; // Code banque domiciliation

    @Column(name = "DAGUICHE", precision = 2, scale = 0)
    private Integer daguiche; // Code guichet

    @Column(name = "DADOSBANC", precision = 7, scale = 0)
    private Long dadosbanc; // Numéro dossier domiciliation

    @Column(name = "DANBANC", precision = 4, scale = 0)
    private Integer danbanc; // Année domiciliation bancaire

    // ─── Sous-position & divers ──────────────────────────────
    @Column(name = "DASSPOSIT", length = 4)
    private String dassposit; // Sous-position tarifaire

    @Column(name = "DANBERRG", precision = 2, scale = 0)
    private Integer danberrg; // Nbr erreurs graves

    @Column(name = "DANBERRNG", precision = 2, scale = 0)
    private Integer danberrng; // Nbr erreurs non graves

    @Column(name = "DANNULAT", precision = 1, scale = 0)
    private Integer dannulat; // Code annulation article

    @Column(name = "DANOMALI", length = 60)
    private String danomali; // Codes rubriques erronées

    @Column(name = "FLAG_ARCH", precision = 1, scale = 0)
    private Integer flagArch; // Flag archivage (default 0)

    // ═══════════════════════════════════════════════════════════
    // Relations
    // ═══════════════════════════════════════════════════════════

    /** Relation vers l'en-tête déclaration (DECENT) */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({
            @JoinColumn(name = "DEBUR", referencedColumnName = "DEBUR", insertable = false, updatable = false),
            @JoinColumn(name = "DEIMPEXP", referencedColumnName = "DEIMPEXP", insertable = false, updatable = false),
            @JoinColumn(name = "DEREPERT", referencedColumnName = "DEREPERT", insertable = false, updatable = false)
    })
    @ToString.Exclude
    @JsonIgnore
    private Decent decent;

    /** Relation vers les détails de taxes (DECTAX) */
    @OneToMany(mappedBy = "decart", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<Dectax> taxes;
}
