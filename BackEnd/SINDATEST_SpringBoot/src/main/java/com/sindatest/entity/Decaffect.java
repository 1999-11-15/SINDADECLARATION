package com.sindatest.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sindatest.entity.id.DecaffectId;
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
@Table(name = "DECAFFECT", schema = "SINDATEST")
@IdClass(DecaffectId.class)
public class Decaffect {

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
    @Column(name = "DFDATE", nullable = false)
    private LocalDateTime dfdate;

    @Id
    @Column(name = "DFVAC", precision = 2, scale = 0, nullable = false)
    private Integer dfvac;

    @Column(name = "DFINSPEC", length = 10)
    private String dfinspec;

    @Column(name = "DFREVISEUR", length = 10)
    private String dfreviseur;

    @Column(name = "DFAGENT", length = 10)
    private String dfagent;

    @Column(name = "FLAG_ARCH", precision = 1, scale = 0)
    private Integer flagArch;

    @Column(name = "DFSELECT", precision = 2, scale = 0)
    private Integer dfselect;

    @Column(name = "DFINSPECV", length = 10)
    private String dfinspecv;

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
