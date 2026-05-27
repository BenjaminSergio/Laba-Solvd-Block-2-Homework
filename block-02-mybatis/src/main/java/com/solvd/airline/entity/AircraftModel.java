package com.solvd.airline.entity;

/**
 * Stub — homework. Maps the {@code aircraft_models} table.
 *
 * <pre>
 * aircraft_model_id  BIGINT       PK
 * manufacturer       VARCHAR(40)  "Boeing", "Airbus"
 * model              VARCHAR(40)  "737-800", "A320"
 * default_capacity   INT
 * </pre>
 *
 * TODO: copy from your Lecture 03 entity.
 */
public class AircraftModel extends BaseEntity {

    private String  manufacturer;
    private String  model;
    private Integer defaultCapacity;

    public String getManufacturer()                { return manufacturer; }
    public void   setManufacturer(String m)        { this.manufacturer = m; }

    public String getModel()                       { return model; }
    public void   setModel(String model)           { this.model = model; }

    public Integer getDefaultCapacity()            { return defaultCapacity; }
    public void    setDefaultCapacity(Integer c)   { this.defaultCapacity = c; }
}
