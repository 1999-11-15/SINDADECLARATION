package com.sindatest.entity.id;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Clé primaire composite pour la table DECOLI.
 * PK: DEBUR + DEIMPEXP + DEREPERT + DCNUMLM
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DecoliId implements Serializable {
    private Integer debur;
    private String  deimpexp;
    private Long    derepert;
    private Integer dcnumlm;   // Marque et numéro du colis (manifeste)
}
