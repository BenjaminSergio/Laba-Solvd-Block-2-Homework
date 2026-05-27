package com.solvd.airline.entity.jackson;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.time.LocalDate;
import java.util.Objects;

/**
 * Jackson binding for an aircraft unit.
 *
 * <p>Demonstrates {@code @JsonFormat} on a {@link LocalDate} — the JSON parallel
 * of JAXB's {@code XmlJavaTypeAdapter<String, LocalDate>}. With
 * {@code jackson-datatype-jsr310} registered on the {@link
 * com.fasterxml.jackson.databind.ObjectMapper} and
 * {@code SerializationFeature.WRITE_DATES_AS_TIMESTAMPS} disabled, dates
 * already serialise as ISO-8601 strings. {@code @JsonFormat} is the per-field
 * override knob — useful when the wire format demands a non-ISO pattern.
 *
 * <p>Here we leave it at ISO-8601 ({@code yyyy-MM-dd}) explicitly so the
 * pattern is visible in the source — exam Q1 ("how to parse a Date with
 * Jackson") points at this exact annotation.
 */
@JsonPropertyOrder({"tailNumber", "modelId", "inService", "deliveredOn"})
public class Aircraft {

    private String tailNumber;
    private Long modelId;
    private boolean inService;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
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
