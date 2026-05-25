package com.solvd.airline.entity;

/**
 * Stub — homework. Maps the {@code aircraft_models} table.
 *
 * <pre>
 * model_id      BIGINT       PK
 * manufacturer  VARCHAR(60)
 * model_name    VARCHAR(60)
 * capacity      SMALLINT
 * range_km      INT
 * UNIQUE (manufacturer, model_name)
 * </pre>
 *
 * TODO: fields, getters, setters.
 */
public class AircraftModel extends BaseEntity {

    private String manufacturer;
    private String modelName;
    private short  capacity;
    private int    rangeKm;

    // TODO: getters / setters
}
