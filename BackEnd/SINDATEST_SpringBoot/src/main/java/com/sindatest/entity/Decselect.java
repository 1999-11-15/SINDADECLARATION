package com.sindatest.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sindatest.entity.id.DecselectId;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "DECSELECT", schema = "SINDATEST")
@IdClass(DecselectId.class)
public class Decselect {

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
    @Column(name = "DMDATE", nullable = false)
    private LocalDateTime dmdate;

    @Id
    @Column(name = "DMCODE", precision = 2, scale = 0, nullable = false)
    private Integer dmcode;

    @Column(name = "DMLIBELLE", length = 100)
    private String dmlibelle;

    @Column(name = "FLAG_ARCH", precision = 1, scale = 0)
    private Integer flagArch;

    @Column(name = "DMORIGINE", length = 1)
    private String dmorigine;

    @Column(name = "DMINSPECV", length = 10)
    private String dminspecv;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({
            @JoinColumn(name = "DEBUR", referencedColumnName = "DEBUR", insertable = false, updatable = false),
            @JoinColumn(name = "DEIMPEXP", referencedColumnName = "DEIMPEXP", insertable = false, updatable = false),
            @JoinColumn(name = "DEREPERT", referencedColumnName = "DEREPERT", insertable = false, updatable = false)
    })
    @ToString.Exclude
    @JsonIgnore
    private Decent decent;
}
