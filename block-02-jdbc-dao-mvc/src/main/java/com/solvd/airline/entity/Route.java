package com.solvd.airline.entity;

/**
 * Stub — homework. Maps the {@code routes} table — directional pair of airports.
 *
 * <pre>
 * route_id                BIGINT  PK
 * origin_airport_id       BIGINT  FK -> airports
 * destination_airport_id  BIGINT  FK -> airports
 * distance_km             INT
 * typical_duration_min    INT
 * UNIQUE (origin, destination)
 * </pre>
 *
 * TODO: fields, getters, setters.
 */
public class Route extends BaseEntity {

    private Long originAirportId;
    private Long destinationAirportId;
    private int  distanceKm;
    private int  typicalDurationMin;

    // TODO: getters / setters
}
