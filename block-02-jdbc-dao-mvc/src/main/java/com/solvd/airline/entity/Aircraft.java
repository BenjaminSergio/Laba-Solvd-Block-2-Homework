package com.solvd.airline.entity;

import java.time.LocalDate;

/**
 * Stub — homework. Maps the {@code aircraft} table.
 *
 * <pre>
 * aircraft_id   BIGINT       PK
 * tail_number   CHAR(7)      UNIQUE
 * model_id      BIGINT       FK -> aircraft_models  (ON DELETE RESTRICT)
 * in_service    BOOLEAN      default TRUE
 * delivered_on  DATE         nullable
 * </pre>
 *
 * TODO: declare fields, getters, setters following the {@link Airport}
 *       template. The id field is inherited from {@link BaseEntity}.
 */
public class Aircraft extends BaseEntity {

    private String    tailNumber;
    private Long      modelId;
    private boolean   inService;
    private LocalDate deliveredOn;

    // TODO: getters / setters — see Airport.java
}
