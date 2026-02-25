package com.sindatest.entity.id;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Clé primaire composite pour la table DECART.
 * PK: DEBUR + DEIMPEXP + DEREPERT + DANUMART
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DecartId implements Serializable {
    private Integer debur;
    private String  deimpexp;
    private Long    derepert;
    private Integer danumart;   // Numéro d'ordre article dans la déclaration
}
