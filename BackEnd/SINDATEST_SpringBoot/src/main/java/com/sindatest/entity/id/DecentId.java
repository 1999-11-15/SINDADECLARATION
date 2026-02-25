package com.sindatest.entity.id;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Clé primaire composite pour la table DECENT.
 * PK: DEBUR + DEIMPEXP + DEREPERT
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DecentId implements Serializable {
    private Integer debur;      // Bureau de dédouanement
    private String  deimpexp;   // Code en douane de l'opérateur
    private Long    derepert;   // Numéro répertoire du déclarant
}
