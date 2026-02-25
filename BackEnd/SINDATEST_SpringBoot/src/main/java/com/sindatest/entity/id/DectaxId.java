package com.sindatest.entity.id;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Clé primaire composite pour la table DECTAX.
 * PK: DEBUR + DEIMPEXP + DEREPERT + DANUMART + DTCODTAX
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DectaxId implements Serializable {
    private Integer debur;
    private String  deimpexp;
    private Long    derepert;
    private Integer danumart;    // Numéro ordre article
    private Integer dtcodtax;   // Code taxe
}
