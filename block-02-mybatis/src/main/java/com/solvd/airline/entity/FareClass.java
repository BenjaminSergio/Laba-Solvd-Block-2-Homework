package com.solvd.airline.entity;

import java.math.BigDecimal;

/**
 * Stub — homework. Maps the {@code fare_classes} table.
 *
 * <pre>
 * fare_class_id  BIGINT        PK
 * code           VARCHAR(8)    UNIQUE — "Y", "J", "F"
 * name           VARCHAR(40)   "Economy", "Business", "First"
 * multiplier     DECIMAL(4,2)  pricing factor
 * </pre>
 *
 * TODO: copy from your Lecture 03 entity.
 */
public class FareClass extends BaseEntity {

    private String     code;
    private String     name;
    private BigDecimal multiplier;

    public String getCode()                       { return code; }
    public void   setCode(String code)            { this.code = code; }

    public String getName()                       { return name; }
    public void   setName(String name)            { this.name = name; }

    public BigDecimal getMultiplier()             { return multiplier; }
    public void       setMultiplier(BigDecimal m) { this.multiplier = m; }
}
