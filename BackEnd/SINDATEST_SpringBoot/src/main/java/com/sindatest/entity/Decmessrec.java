package com.sindatest.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sindatest.entity.id.DecmessrecId;
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
@Table(name = "DECMESSREC", schema = "SINDATEST")
@IdClass(DecmessrecId.class)
public class Decmessrec {

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

    @Column(name = "DMAGENT", length = 10)
    private String dmagent;

    @Column(name = "FLAG_ARCH", precision = 1, scale = 0)
    private Integer flagArch;

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
