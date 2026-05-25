package com.solvd.airline.entity;

import java.math.BigDecimal;

/**
 * Stub — homework. Maps the {@code fare_classes} table.
 *
 * <pre>
 * fare_class_id     BIGINT       PK
 * code              CHAR(1)      'Y','W','C','F'
 * name              VARCHAR(40)
 * price_multiplier  DECIMAL(4,2)
 * refundable        BOOLEAN
 * </pre>
 *
 * TODO: fields, getters, setters. Use {@link BigDecimal} for {@code price_multiplier} —
 * never {@code float} or {@code double} for money-adjacent values.
 */
public class FareClass extends BaseEntity {

    private String     code;
    private String     name;
    private BigDecimal priceMultiplier;
    private boolean    refundable;

    // TODO: getters / setters
}
