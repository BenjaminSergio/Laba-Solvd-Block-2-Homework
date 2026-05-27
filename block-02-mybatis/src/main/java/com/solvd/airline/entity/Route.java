package com.solvd.airline.entity;

/**
 * Stub — homework. Maps the {@code routes} table.
 *
 * <pre>
 * route_id       BIGINT       PK
 * origin_id      BIGINT       FK -> airports
 * destination_id BIGINT       FK -> airports
 * distance_km    INT
 * UNIQUE (origin_id, destination_id)
 * </pre>
 *
 * TODO: copy from your Lecture 03 entity.
 */
public class Route extends BaseEntity {

    private Long    originId;
    private Long    destinationId;
    private Integer distanceKm;

    public Long getOriginId()                      { return originId; }
    public void setOriginId(Long originId)         { this.originId = originId; }

    public Long getDestinationId()                 { return destinationId; }
    public void setDestinationId(Long id)          { this.destinationId = id; }

    public Integer getDistanceKm()                 { return distanceKm; }
    public void    setDistanceKm(Integer d)        { this.distanceKm = d; }
}
