package com.sindatest.entity;

import com.sindatest.entity.id.DecentId;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Entity pour la table DECENT — En-tête de la déclaration douanière.
 * Schema Oracle: SINDATEST.DECENT
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "DECENT", schema = "SINDATEST")
@IdClass(DecentId.class)
public class Decent {

    // ─── Clé primaire composite ──────────────────────────────
    @Id
    @Column(name = "DEBUR", precision = 2, scale = 0, nullable = false)
    private Integer debur; // Bureau de dédouanement

    @Id
    @Column(name = "DEIMPEXP", length = 8, nullable = false)
    private String deimpexp; // Code en douane de l'opérateur

    @Id
    @Column(name = "DEREPERT", precision = 8, scale = 0, nullable = false)
    private Long derepert; // Numéro répertoire du déclarant

    // ─── Informations déclaration ────────────────────────────
    @Column(name = "DETYPDEC", length = 2)
    private String detypdec; // Type de déclaration

    @Column(name = "DETOTART", precision = 3, scale = 0)
    private Integer detotart; // Nombre total d'articles

    @Column(name = "DENUMDAE", precision = 6, scale = 0)
    private Long denumdae; // Numéro DAE (demande autorisation enlevage)

    @Column(name = "DEDATDAE")
    private LocalDateTime dedatdae; // Date enregistrement DAE

    // ─── Parties prenantes ───────────────────────────────────
    @Column(name = "DEFOURNI", length = 73)
    private String defourni; // Nom/prénom/adresse du fournisseur

    @Column(name = "DEAGR", precision = 9, scale = 0)
    private Long deagr; // Numéro d'agrément commissionnaire

    @Column(name = "DEREPAGR", precision = 5, scale = 0)
    private Integer derepagr; // Répertoire déclaration commissionnaire

    @Column(name = "DENOMDEC", length = 70)
    private String denomdec; // Nom et adresse déclarant

    // ─── Engagement & paiement ───────────────────────────────
    @Column(name = "DETYPENG", length = 5)
    private String detypeng; // Type d'engagement (A...., ABCDE, etc.)

    @Column(name = "DEMODPAI", precision = 1, scale = 0)
    private Integer demodpai; // Code mode de paiement (1-5)

    @Column(name = "DECRED", precision = 9, scale = 0)
    private Long decred; // Numéro du crédit d'enlevage

    // ─── Bureaux & pays ──────────────────────────────────────
    @Column(name = "DEBURFRO", precision = 2, scale = 0)
    private Integer deburfro; // Bureau frontière

    @Column(name = "DEBURDES", precision = 2, scale = 0)
    private Integer deburdes; // Bureau destination

    @Column(name = "DEPYPROV", precision = 3, scale = 0)
    private Integer depyprov; // Pays de provenance

    @Column(name = "DEPYACHA", precision = 3, scale = 0)
    private Integer depyacha; // Pays d'achat

    // ─── Transport ───────────────────────────────────────────
    @Column(name = "DENATRAN1", precision = 3, scale = 0)
    private Integer denatran1; // Nationalité 1er moyen transport

    @Column(name = "DEMODTRAN1", precision = 1, scale = 0)
    private Integer demodtran1; // Mode transport 1er transporteur

    @Column(name = "DEIDTRAN1", length = 25)
    private String deidtran1; // Identification moyen transport

    @Column(name = "DENATRAN2", precision = 3, scale = 0)
    private Integer denatran2;

    @Column(name = "DEMODTRAN2", precision = 1, scale = 0)
    private Integer demodtran2;

    @Column(name = "DEIDTRAN2", length = 25)
    private String deidtran2;

    @Column(name = "DENATRAN3", precision = 3, scale = 0)
    private Integer denatran3;

    @Column(name = "DEMODTRAN3", precision = 1, scale = 0)
    private Integer demodtran3;

    @Column(name = "DEIDTRAN3", length = 25)
    private String deidtran3;

    @Column(name = "DENATRTR", precision = 3, scale = 0)
    private Integer denatrtr; // Transport de retour

    @Column(name = "DEMOTRTR", precision = 1, scale = 0)
    private Integer demotrtr;

    @Column(name = "DEIDTRTR", length = 25)
    private String deidtrtr;

    // ─── Valeurs financières ─────────────────────────────────
    @Column(name = "DEDEVPTFN", precision = 3, scale = 0)
    private Integer dedevptfn; // Devise facture nette

    @Column(name = "DEPTFN", precision = 14, scale = 3)
    private BigDecimal deptfn; // Prix total facture net

    @Column(name = "DESIGPTFN", length = 1)
    private String desigptfn; // Signe algébrique du solde (+/-)

    @Column(name = "DESOLDPTFN", precision = 13, scale = 3)
    private BigDecimal desoldptfn; // Montant solde PTFN

    @Column(name = "DEDEVFRET", precision = 3, scale = 0)
    private Integer dedevfret; // Devise montant fret

    @Column(name = "DEMONFRET", precision = 10, scale = 3)
    private BigDecimal demonfret; // Montant fret en devise

    @Column(name = "DEDEVASS", precision = 3, scale = 0)
    private Integer dedevass; // Devise assurance

    @Column(name = "DEMONASS", precision = 10, scale = 3)
    private BigDecimal demonass; // Montant assurance en devise

    @Column(name = "DECOURDEV", precision = 8, scale = 7)
    private BigDecimal decourdev; // Cours de conversion devise

    @Column(name = "DETTVD", precision = 15, scale = 3)
    private BigDecimal dettvd; // Total TVD

    @Column(name = "DETTLIQ", precision = 14, scale = 3)
    private BigDecimal dettliq; // Total liquidé

    // ─── Colis & livraison ───────────────────────────────────
    @Column(name = "DETOTCOLI", precision = 6, scale = 0)
    private Long detotcoli; // Nombre total colis

    @Column(name = "DEMODLIV", precision = 2, scale = 0)
    private Integer demodliv; // Mode de livraison

    @Column(name = "DELOCALIS", length = 11)
    private String delocalis; // Code localisation marchandise

    // ─── Dates & contrôle ────────────────────────────────────
    @Column(name = "DEDATEXP")
    private LocalDateTime dedatexp; // Date expédition

    @Column(name = "DEDATDEC")
    private LocalDateTime dedatdec; // Date dépôt déclaration

    @Column(name = "DEDATIN")
    private LocalDateTime dedatin; // Date insertion (default sysdate)

    @Column(name = "DEDATVALI")
    private LocalDateTime dedatvali; // Date de validation

    @Column(name = "DEDATIMPOSI")
    private LocalDateTime dedatimposi; // Date imposition

    @Column(name = "DEDATRECEV")
    private LocalDateTime dedatrecev; // Date réception

    @Column(name = "DEDATBAE")
    private LocalDateTime dedatbae; // Date BAE

    @Column(name = "DEDATARRIV1")
    private LocalDateTime dedatarriv1;

    @Column(name = "DEDATARRIV2")
    private LocalDateTime dedatarriv2;

    @Column(name = "DEDATARRIV3")
    private LocalDateTime dedatarriv3;

    // ─── Flags & statuts ─────────────────────────────────────
    @Column(name = "DEVALID", precision = 1, scale = 0)
    private Integer devalid; // Flag validation

    @Column(name = "DEANNUL", precision = 1, scale = 0)
    private Integer deannul; // Flag annulation (default 0)

    @Column(name = "DERECEVAB", precision = 1, scale = 0)
    private Integer derecevab; // Recevabilité (0, 1, 9)

    @Column(name = "DECANZ", precision = 1, scale = 0)
    private Integer decanz;

    @Column(name = "DENCALVD", precision = 1, scale = 0)
    private Integer dencalvd;

    @Column(name = "DECDJDE", precision = 1, scale = 0)
    private Integer decdjde;

    @Column(name = "DESUITE", precision = 1, scale = 0)
    private Integer desuite; // Flag suite (default 0)

    @Column(name = "DEVFET", precision = 1, scale = 0)
    private Integer devfet;

    @Column(name = "DEREVIS", precision = 1, scale = 0)
    private Integer derevis;

    @Column(name = "DELITIJ", precision = 1, scale = 0)
    private Integer delitij;

    @Column(name = "DEENLEV", precision = 1, scale = 0)
    private Integer deenlev;

    @Column(name = "DEAFFECT", precision = 1, scale = 0)
    private Integer deaffect;

    @Column(name = "DERELAV", precision = 1, scale = 0)
    private Integer derelav; // Relation acheteur-vendeur

    // ─── Nature & numéros ────────────────────────────────────
    @Column(name = "DENATURE", length = 4)
    private String denature; // Nature (ex: SAFA)

    @Column(name = "DENUIM", length = 15)
    private String denuim; // Numéro immatriculation

    @Column(name = "DENUOR", precision = 7, scale = 0)
    private Long denuor;

    @Column(name = "DENUMDEC", length = 7)
    private String denumdec; // Numéro serial déclaration

    @Column(name = "DEITENER", length = 18)
    private String deitener; // Itinéraire transit

    // ─── Agents & inspection ─────────────────────────────────
    @Column(name = "DEAGENR", length = 10)
    private String deagenr; // Code agent réception

    @Column(name = "DEAGEVF", length = 10)
    private String deagevf; // Code agent validation

    @Column(name = "DEINSPEC", length = 10)
    private String deinspec; // Code inspecteur

    // ─── Garanties & engagements ─────────────────────────────
    @Column(name = "DEGARAND", precision = 2, scale = 0)
    private Integer degarand;

    @Column(name = "DEGARNUD", precision = 4, scale = 0)
    private Integer degarnud;

    @Column(name = "DENGAGDL", length = 50)
    private String dengagdl;

    @Column(name = "DENGAGDM", precision = 10, scale = 3)
    private BigDecimal dengagdm;

    @Column(name = "DEGARANE", precision = 2, scale = 0)
    private Integer degarane;

    @Column(name = "DEGARNUE", precision = 4, scale = 0)
    private Integer degarnue;

    @Column(name = "DENGAGEL", length = 50)
    private String dengagel;

    @Column(name = "DENGAGEM", precision = 10, scale = 3)
    private BigDecimal dengagem;

    @Column(name = "DENGAGEC1", length = 3)
    private String dengagec1;

    @Column(name = "DENGAGEC2", length = 3)
    private String dengagec2;

    @Column(name = "DENGAGEC3", length = 3)
    private String dengagec3;

    // ─── Divers ──────────────────────────────────────────────
    @Column(name = "DEADRSUS", length = 72)
    private String deadrsus; // Adresse suspense

    @Column(name = "DEANOMALI", length = 40)
    private String deanomali; // Anomalies détectées

    @Column(name = "DEJIMPOSI", precision = 2, scale = 0)
    private Integer dejimposi;

    @Column(name = "DEJRECEV", precision = 2, scale = 0)
    private Integer dejrecev;

    @Column(name = "DENUMPRI", precision = 5, scale = 0)
    private Integer denumpri;

    @Column(name = "DEDATPRI")
    private LocalDateTime dedatpri;

    @Column(name = "DENBFPRI", precision = 2, scale = 0)
    private Integer denbfpri;

    @Column(name = "DENBFAPU", precision = 2, scale = 0)
    private Integer denbfapu;

    @Column(name = "DENBLM", precision = 1, scale = 0)
    private Integer denblm;

    @Column(name = "DEEDITBAE", precision = 1, scale = 0)
    private Integer deeditbae;

    @Column(name = "DENBEDIT", precision = 1, scale = 0)
    private Integer denbedit;

    @Column(name = "DENBERRG", precision = 3, scale = 0)
    private Integer denberrg; // Nbr erreurs graves

    @Column(name = "DENBERRNG", precision = 2, scale = 0)
    private Integer denberrng; // Nbr erreurs non graves

    @Column(name = "DENBERR", precision = 2, scale = 0)
    private Integer denberr;

    @Column(name = "DE2FORM", precision = 1, scale = 0)
    private Integer de2form;

    @Column(name = "DE2NBERRG", precision = 2, scale = 0)
    private Integer de2nberrg;

    @Column(name = "DE2NBERRNG", precision = 2, scale = 0)
    private Integer de2nberrng;

    @Column(name = "DE2ANNUL", precision = 1, scale = 0)
    private Integer de2annul;

    @Column(name = "DE2ANOMAL", length = 40)
    private String de2anomal;

    @Column(name = "DEMODPASS", length = 4)
    private String demodpass;

    @Column(name = "DENBARTIN", precision = 3, scale = 0)
    private Integer denbartin;

    @Column(name = "DEBURD", length = 2)
    private String deburd;

    @Column(name = "DECHRS")
    private LocalDateTime dechrs;

    @Column(name = "DECHENGB")
    private LocalDateTime dechengb;

    @Column(name = "DECHENGC")
    private LocalDateTime dechengc;

    @Column(name = "DERDZV")
    private LocalDateTime derdzv;

    @Column(name = "DENUQFIL", precision = 5, scale = 0)
    private Integer denuqfil;

    @Column(name = "DENURFIL", precision = 5, scale = 0)
    private Integer denurfil;

    @Column(name = "DENUCFIL", precision = 6, scale = 0)
    private Long denucfil;

    @Column(name = "DECAIFIL", precision = 7, scale = 0)
    private Long decaifil;

    @Column(name = "DEFILLER", length = 3)
    private String defiller;

    @Column(name = "DEFILLER3", length = 1)
    private String defiller3; // V, C, A, S, R

    @Column(name = "DEFILLER4", length = 3)
    private String defiller4;

    @Column(name = "FLAG_ARCH", precision = 1, scale = 0)
    private Integer flagArch; // Flag archivage (default 0)

    // ═══════════════════════════════════════════════════════════
    // Relations vers les tables enfants (1:N)
    // ═══════════════════════════════════════════════════════════

    @OneToMany(mappedBy = "decent", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @ToString.Exclude
    private List<Decart> articles;

    @OneToMany(mappedBy = "decent", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @ToString.Exclude
    private List<Decoli> colis;

    @OneToMany(mappedBy = "decent", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @ToString.Exclude
    private List<Decrecap> recapitulatifs;

    @OneToMany(mappedBy = "decent", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @ToString.Exclude
    private List<Decpaie> paiements;

}
