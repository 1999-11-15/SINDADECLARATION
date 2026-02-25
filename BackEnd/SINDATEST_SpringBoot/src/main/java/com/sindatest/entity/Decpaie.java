package com.sindatest.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sindatest.entity.id.DecpaieId;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Entity pour la table DECPAIE — Paiements liés à la déclaration.
 * Schema Oracle: SINDATEST.DECPAIE
 * Relation: N:1 vers DECENT
 *
 * Natures de paiement clés :
 * 15 = OBC (ordre de crédit bancaire)
 * 17 = Virement DDM
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "DECPAIE", schema = "SINDATEST")
@IdClass(DecpaieId.class)
public class Decpaie {

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

    @Id
    @Column(name = "DPDATOP")
    private LocalDateTime dpdatop; // Date régularisation

    @Id
    @Column(name = "DPNUMEROP", precision = 6, scale = 0, nullable = false)
    private Long dpnumerop; // Numéro opération / quittance

    @Id
    @Column(name = "DPNATPAIE", length = 2, nullable = false)
    private String dpnatpaie; // Nature du paiement

    // ─── Détails opération ───────────────────────────────────
    @Column(name = "DPTYPOP", precision = 2, scale = 0)
    private Integer dptypop; // Code type opération

    @Column(name = "DPORGINOP", length = 7)
    private String dporginop; // Origine de l'opération

    // ─── Moyen de paiement ───────────────────────────────────
    @Column(name = "DPORIGPAIE", length = 7)
    private String dporigpaie; // Origine du moyen de paiement

    @Column(name = "DPNUMDOC", precision = 6, scale = 0)
    private Long dpnumdoc; // Numéro document

    @Column(name = "DPDATDOC")
    private LocalDateTime dpdatdoc; // Date document

    @Column(name = "DPMONTPAIE", precision = 12, scale = 3)
    private BigDecimal dpmontpaie; // Montant du paiement

    // ─── Bancaire ────────────────────────────────────────────
    @Column(name = "DPCOMPTE", length = 30)
    private String dpcompte; // Numéro compte bancaire

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
