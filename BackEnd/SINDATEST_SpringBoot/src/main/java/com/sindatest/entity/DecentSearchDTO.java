package com.sindatest.entity;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DecentSearchDTO {

    // ─── Mode 1 : Recherche par numéro de déclaration ────────
    private Integer debur; // Bureau de dédouanement
    private String denumdec; // Numéro de déclaration (DENUMDEC)
    private Long derepert; // Numéro répertoire du déclarant

    // ─── Mode 2 : Recherche IMP/EXP ──────────────────────────
    private String deimpexp; // Code en douane de l'opérateur
    private LocalDateTime dedatin; // Date de déclaration (insertion)

    // ─── Recherche avancée (inchangée) ───────────────────────
    private String numeroTCE; // → danumtcce (Décart)
    private Integer daregdecl; // Numéro TCE (registre)
    private String detypdec; // Type déclaration
    private String danomencl; // NDP — Nomenclature

    // 📅 Plage de dates
    private LocalDateTime startDate;
    private LocalDateTime endDate;

    // 🚢 Manifeste (JOIN Decoli)
    private Integer dcrubr; // Rubrique manifeste
    private Integer dcnumesc; // Numéro d'escale
}
