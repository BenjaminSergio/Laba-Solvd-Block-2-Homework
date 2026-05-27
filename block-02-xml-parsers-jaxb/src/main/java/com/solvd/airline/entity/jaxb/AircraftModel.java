package com.solvd.airline.entity.jaxb;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;

import java.util.Objects;

/**
 * JAXB binding for {@code <aircraftModel>}.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AircraftModelType", propOrder = {"manufacturer", "modelName", "capacity", "rangeKm"})
public class AircraftModel {

    @XmlAttribute(name = "id", required = true)
    private Long id;

    @XmlElement(required = true)
    private String manufacturer;

    @XmlElement(required = true)
    private String modelName;

    @XmlElement(required = true)
    private int capacity;

    @XmlElement(required = true)
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
