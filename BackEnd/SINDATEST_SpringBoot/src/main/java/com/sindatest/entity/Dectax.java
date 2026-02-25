package com.sindatest.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sindatest.entity.id.DectaxId;
import jakarta.persistence.*;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Entity pour la table DECTAX — Détails des taxes par article.
 * Schema Oracle: SINDATEST.DECTAX
 * Relation: N:1 vers DECART (via DEBUR + DEIMPEXP + DEREPERT + DANUMART)
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "DECTAX", schema = "SINDATEST")
@IdClass(DectaxId.class)
public class Dectax {

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
    private Integer danumart; // Numéro ordre article

    @Id
    @Column(name = "DTCODTAX", precision = 3, scale = 0, nullable = false)
    private Integer dtcodtax; // Code taxe

    // ─── Détails taxe ────────────────────────────────────────
    @Column(name = "DTASSIET", length = 1)
    private String dtassiet; // Code assiette

    @Column(name = "DTFILLER1", precision = 1, scale = 0)
    private Integer dtfiller1; // Non utilisé

    @Column(name = "DTTAUX", precision = 12, scale = 6)
    private BigDecimal dttaux; // Valeur taux taxe

    @Column(name = "DTMTASSIE", precision = 14, scale = 3)
    private BigDecimal dtmtassie; // Montant assiette

    @Column(name = "DTMTAXE", precision = 14, scale = 3)
    private BigDecimal dtmtaxe; // Montant taxe

    @Column(name = "FLAG_ARCH", precision = 1, scale = 0)
    private Integer flagArch; // Flag archivage (default 0)

    // ═══════════════════════════════════════════════════════════
    // Relations
    // ═══════════════════════════════════════════════════════════

    /** Relation vers l'article (DECART) */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({
            @JoinColumn(name = "DEBUR", referencedColumnName = "DEBUR", insertable = false, updatable = false),
            @JoinColumn(name = "DEIMPEXP", referencedColumnName = "DEIMPEXP", insertable = false, updatable = false),
            @JoinColumn(name = "DEREPERT", referencedColumnName = "DEREPERT", insertable = false, updatable = false),
            @JoinColumn(name = "DANUMART", referencedColumnName = "DANUMART", insertable = false, updatable = false)
    })
    @ToString.Exclude
    @JsonIgnore
    private Decart decart;
}
