package com.sindatest.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sindatest.entity.id.DecoliId;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Entity pour la table DECOLI — Colis de la déclaration douanière.
 * Schema Oracle: SINDATEST.DECOLI
 * Relation: N:1 vers DECENT
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "DECOLI", schema = "SINDATEST")
@IdClass(DecoliId.class)
public class Decoli {

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
    @Column(name = "DCNUMLM", precision = 1, scale = 0, nullable = false)
    private Integer dcnumlm; // Marque et numéro du colis (manifeste)

    // ─── Manifeste & escale ──────────────────────────────────
    @Column(name = "DCNUMESC", precision = 5, scale = 0)
    private Integer dcnumesc; // Numéro d'escale (manifeste)

    @Column(name = "DCANESC", precision = 4, scale = 0)
    private Integer dcanesc; // Année escale du manifeste

    @Column(name = "DCTITRAN", length = 11)
    private String dctitran; // Numéro titre de transport

    @Column(name = "DCRUBR", precision = 4, scale = 0)
    private Integer dcrubr; // Numéro rubrique manifeste

    // ─── Description du colis ────────────────────────────────
    @Column(name = "DCNBCOLI", precision = 5, scale = 0)
    private Integer dcnbcoli; // Nombre de colis déclarés

    @Column(name = "DCNATCOL", length = 30)
    private String dcnatcol; // Nature des colis

    @Column(name = "DCMARQUE", length = 140)
    private String dcmarque; // Marques et numéros des colis

    // ─── Divers ──────────────────────────────────────────────
    @Column(name = "DCFILL", length = 2)
    private String dcfill;

    @Column(name = "DCNBERRE", precision = 2, scale = 0)
    private Integer dcnberre; // Nombre d'erreurs détectées

    @Column(name = "DCANOMAL", length = 40)
    private String dcanomal; // Rubriques erronées

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
}
