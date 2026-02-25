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

    // 🔑 Identification déclaration
    private Integer debur;
    private String deimpexp;
    private Long derepert;

    // 📄 Déclaration
    private String codeDeclaration; // Maps to denumdec
    // Maps to danumtcce (String in DTO, but Long in DB - need conversion or parse)
    // User requested "numeroTCE": "string".
    private String numeroTCE;
    private Integer daregdecl; // Constant: DAREGDECL
    private String detypdec; // Constant: DETYPDEC
    private LocalDateTime dedatin; // Constant: dedatin
    private String danomencl; // Constant: NDP

    // 📅 Date Range
    private LocalDateTime startDate;
    private LocalDateTime endDate;

    // 🚢 Manifeste (JOIN Decoli)
    private Integer dcrubr; // Rubrique manifeste
    private Integer dcnumesc; // Numéro d'escale
}
