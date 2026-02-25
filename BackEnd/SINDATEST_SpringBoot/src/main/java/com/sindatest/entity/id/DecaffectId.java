package com.sindatest.entity.id;

import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DecaffectId implements Serializable {
    private Integer debur;
    private String deimpexp;
    private Long derepert;
    private LocalDateTime dfdate;
    private Integer dfvac;
}
