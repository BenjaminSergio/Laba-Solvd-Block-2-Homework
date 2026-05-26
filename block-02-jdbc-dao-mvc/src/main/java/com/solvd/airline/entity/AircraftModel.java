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

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public short getCapacity() {
        return capacity;
    }

    public void setCapacity(short capacity) {
        this.capacity = capacity;
    }

    public int getRangeKm() {
        return rangeKm;
    }

    public void setRangeKm(int rangeKm) {
        this.rangeKm = rangeKm;
    }

    @java.lang.Override
    public java.lang.String toString() {
        return "AircraftModel{" +
                "manufacturer='" + manufacturer + '\'' +
                ", modelName='" + modelName + '\'' +
                ", capacity=" + capacity +
                ", rangeKm=" + rangeKm +
                '}';
    }
}
