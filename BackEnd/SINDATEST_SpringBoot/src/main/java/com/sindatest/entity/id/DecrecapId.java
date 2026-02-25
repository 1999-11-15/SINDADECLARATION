package com.sindatest.entity.id;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Clé primaire composite pour la table DECRECAP.
 * PK: DEBUR + DEIMPEXP + DEREPERT + DRGTAX
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DecrecapId implements Serializable {
    private Integer debur;
    private String  deimpexp;
    private Long    derepert;
    private Integer drgtax;     // Code groupe taxe
}
