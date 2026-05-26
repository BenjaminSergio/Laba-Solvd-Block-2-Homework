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

    public Long getOriginAirportId() {
        return originAirportId;
    }

    public void setOriginAirportId(Long originAirportId) {
        this.originAirportId = originAirportId;
    }

    public Long getDestinationAirportId() {
        return destinationAirportId;
    }

    public void setDestinationAirportId(Long destinationAirportId) {
        this.destinationAirportId = destinationAirportId;
    }

    public int getDistanceKm() {
        return distanceKm;
    }

    public void setDistanceKm(int distanceKm) {
        this.distanceKm = distanceKm;
    }

    public int getTypicalDurationMin() {
        return typicalDurationMin;
    }

    public void setTypicalDurationMin(int typicalDurationMin) {
        this.typicalDurationMin = typicalDurationMin;
    }

    @java.lang.Override
    public java.lang.String toString() {
        return "Route{" +
                "originAirportId=" + originAirportId +
                ", destinationAirportId=" + destinationAirportId +
                ", distanceKm=" + distanceKm +
                ", typicalDurationMin=" + typicalDurationMin +
                '}';
    }
}
