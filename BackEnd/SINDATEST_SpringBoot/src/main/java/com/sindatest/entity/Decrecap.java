package com.sindatest.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sindatest.entity.id.DecrecapId;
import jakarta.persistence.*;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Entity pour la table DECRECAP — Récapitulatif des taxes par groupe.
 * Schema Oracle: SINDATEST.DECRECAP
 * Relation: N:1 vers DECENT
 *
 * Groupes de taxes importants :
 * 621 = Droits de douane principaux
 * 635 = Consignation (engagement D)
 * 636 = Consignation (engagement E)
 * 688 = Taxe spéciale
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "DECRECAP", schema = "SINDATEST")
@IdClass(DecrecapId.class)
public class Decrecap {

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
    @Column(name = "DRGTAX", precision = 3, scale = 0, nullable = false)
    private Integer drgtax; // Code du groupe taxe

    // ─── Détails récapitulatif ───────────────────────────────
    @Column(name = "DRMGTAX", precision = 12, scale = 3)
    private BigDecimal drmgtax; // Montant des taxes du groupe

    @Column(name = "DRORIGINE", length = 7)
    private String drorigine; // Origine (amende, consignation, N° quittance…)

    @Column(name = "DRFLAG", precision = 2, scale = 0)
    private Integer drflag; // État taxe : payée (1), non payée (0),
                            // annulée, passe outre, etc.

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
