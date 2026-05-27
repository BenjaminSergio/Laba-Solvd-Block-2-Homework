package com.solvd.airline.entity.jackson;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.Objects;

/**
 * Jackson binding for an aircraft model — same fields as the JAXB sibling.
 */
@JsonPropertyOrder({"id", "manufacturer", "modelName", "capacity", "rangeKm"})
public class AircraftModel {

    private Long id;
    private String manufacturer;
    private String modelName;
    private int capacity;
    private int rangeKm;

    public AircraftModel() { }

    public AircraftModel(Long id, String manufacturer, String modelName, int capacity, int rangeKm) {
        this.id = id;
        this.manufacturer = manufacturer;
        this.modelName = modelName;
        this.capacity = capacity;
        this.rangeKm = rangeKm;
    }

    public Long   getId()           { return id; }
    public void   setId(Long id)    { this.id = id; }
    public String getManufacturer() { return manufacturer; }
    public void   setManufacturer(String m) { this.manufacturer = m; }
    public String getModelName()    { return modelName; }
    public void   setModelName(String n) { this.modelName = n; }
    public int    getCapacity()     { return capacity; }
    public void   setCapacity(int c){ this.capacity = c; }
    public int    getRangeKm()      { return rangeKm; }
    public void   setRangeKm(int r) { this.rangeKm = r; }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (!(other instanceof AircraftModel a)) return false;
        return capacity == a.capacity
            && rangeKm == a.rangeKm
            && Objects.equals(id, a.id)
            && Objects.equals(manufacturer, a.manufacturer)
            && Objects.equals(modelName, a.modelName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, manufacturer, modelName, capacity, rangeKm);
    }

    @Override
    public String toString() {
        return "AircraftModel{" + manufacturer + " " + modelName + " · cap=" + capacity + "}";
    }
}
