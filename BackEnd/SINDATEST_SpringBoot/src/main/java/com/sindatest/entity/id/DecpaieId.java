package com.sindatest.entity.id;

import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Clé primaire composite pour la table DECPAIE.
 * PK: DEBUR + DEIMPEXP + DEREPERT + DRGTAX + DPDATOP + DPNUMEROP + DPNATPAIE
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DecpaieId implements Serializable {
    private Integer       debur;
    private String        deimpexp;
    private Long          derepert;
    private Integer       drgtax;       // Code groupe taxe
    private LocalDateTime dpdatop;      // Date régularisation
    private Long          dpnumerop;    // Numéro opération / quittance
    private String        dpnatpaie;    // Nature du paiement
}
