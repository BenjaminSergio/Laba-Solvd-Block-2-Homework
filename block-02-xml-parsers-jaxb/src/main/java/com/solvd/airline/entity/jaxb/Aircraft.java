package com.solvd.airline.entity.jaxb;

import com.solvd.airline.xml.jaxb.adapters.LocalDateAdapter;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlSchemaType;
import jakarta.xml.bind.annotation.XmlType;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import java.time.LocalDate;
import java.util.Objects;

/**
 * JAXB binding for {@code <aircraftUnit>}.
 *
 * <p>Demonstrates {@link XmlJavaTypeAdapter} on a {@link LocalDate} field —
 * JAXB does not natively map {@code java.time.*} types, so the adapter
 * converts to the {@code xs:date} string form.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AircraftUnitType", propOrder = {"inService", "deliveredOn"})
public class Aircraft {

    @XmlAttribute(name = "tailNumber", required = true)
    private String tailNumber;

    @XmlAttribute(name = "modelId", required = true)
    private Long modelId;

    @XmlElement(required = true)
    private boolean inService;

    @XmlElement
    @XmlSchemaType(name = "date")
    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    private LocalDate deliveredOn;

    public Aircraft() { }

    public Aircraft(String tailNumber, Long modelId, boolean inService, LocalDate deliveredOn) {
        this.tailNumber = tailNumber;
        this.modelId = modelId;
        this.inService = inService;
        this.deliveredOn = deliveredOn;
    }

    public String    getTailNumber()  { return tailNumber; }
    public void      setTailNumber(String t) { this.tailNumber = t; }
    public Long      getModelId()     { return modelId; }
    public void      setModelId(Long m) { this.modelId = m; }
    public boolean   isInService()    { return inService; }
    public void      setInService(boolean s) { this.inService = s; }
    public LocalDate getDeliveredOn() { return deliveredOn; }
    public void      setDeliveredOn(LocalDate d) { this.deliveredOn = d; }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (!(other instanceof Aircraft a)) return false;
        return inService == a.inService
            && Objects.equals(tailNumber, a.tailNumber)
            && Objects.equals(modelId, a.modelId)
            && Objects.equals(deliveredOn, a.deliveredOn);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tailNumber, modelId, inService, deliveredOn);
    }

    @Override
    public String toString() {
        return "Aircraft{" + tailNumber + " · model=" + modelId
             + (inService ? " · in-service" : " · grounded") + "}";
    }
}
