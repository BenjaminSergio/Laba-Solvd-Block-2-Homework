package com.solvd.airline.entity;

/**
 * Stub — homework. Maps the {@code aircraft} table.
 *
 * <pre>
 * aircraft_id        BIGINT       PK
 * tail_number        VARCHAR(10)  UNIQUE — "SP-LWA"
 * aircraft_model_id  BIGINT       FK -> aircraft_models
 * seat_capacity      INT
 * </pre>
 *
 * TODO: copy the complete fields/getters/setters from your Lecture 03
 * Aircraft entity. MyBatis needs only the no-arg ctor + getters + setters.
 */
public class Aircraft extends BaseEntity {

    private String tailNumber;
    private Long   aircraftModelId;
    private Integer seatCapacity;

    public String getTailNumber()                  { return tailNumber; }
    public void   setTailNumber(String n)          { this.tailNumber = n; }

    public Long   getAircraftModelId()             { return aircraftModelId; }
    public void   setAircraftModelId(Long id)      { this.aircraftModelId = id; }

    public Integer getSeatCapacity()               { return seatCapacity; }
    public void    setSeatCapacity(Integer s)      { this.seatCapacity = s; }
}
