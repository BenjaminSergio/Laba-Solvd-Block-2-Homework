package com.solvd.airline.entity.jackson;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Jackson binding for a flight.
 *
 * <p>Demonstrates:
 * <ul>
 *   <li>{@code @JsonFormat} on {@link LocalDateTime} fields — ISO-8601 without
 *       offset, the parallel of JAXB's {@code XmlAdapter<String, LocalDateTime>}
 *       + {@code @XmlSchemaType("dateTime")}.</li>
 *   <li>A {@code Status} enum that Jackson serialises by name out of the box —
 *       UPPERCASE Java enum constants match the JSON Schema {@code enum} values
 *       byte-for-byte, so no {@code @JsonValue} or {@code @JsonProperty} override
 *       is needed (the JSON parallel of JAXB's {@code @XmlEnum}).</li>
 * </ul>
 */
@JsonPropertyOrder({"number", "routeId", "aircraftTail", "scheduledDep", "scheduledArr", "status"})
public class Flight {

    public enum Status { SCHEDULED, BOARDING, DEPARTED, ARRIVED, CANCELLED }

    private String number;
    private Long routeId;
    private String aircraftTail;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime scheduledDep;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime scheduledArr;

    private Status status;

    public Flight() { }

    public Flight(String number, Long routeId, String aircraftTail,
                  LocalDateTime scheduledDep, LocalDateTime scheduledArr, Status status) {
        this.number = number;
        this.routeId = routeId;
        this.aircraftTail = aircraftTail;
        this.scheduledDep = scheduledDep;
        this.scheduledArr = scheduledArr;
        this.status = status;
    }

    public String        getNumber()        { return number; }
    public void          setNumber(String n) { this.number = n; }
    public Long          getRouteId()       { return routeId; }
    public void          setRouteId(Long r) { this.routeId = r; }
    public String        getAircraftTail() { return aircraftTail; }
    public void          setAircraftTail(String t) { this.aircraftTail = t; }
    public LocalDateTime getScheduledDep() { return scheduledDep; }
    public void          setScheduledDep(LocalDateTime t) { this.scheduledDep = t; }
    public LocalDateTime getScheduledArr() { return scheduledArr; }
    public void          setScheduledArr(LocalDateTime t) { this.scheduledArr = t; }
    public Status        getStatus()        { return status; }
    public void          setStatus(Status s) { this.status = s; }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (!(other instanceof Flight f)) return false;
        return Objects.equals(number, f.number)
            && Objects.equals(routeId, f.routeId)
            && Objects.equals(aircraftTail, f.aircraftTail)
            && Objects.equals(scheduledDep, f.scheduledDep)
            && Objects.equals(scheduledArr, f.scheduledArr)
            && status == f.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(number, routeId, aircraftTail, scheduledDep, scheduledArr, status);
    }

    @Override
    public String toString() {
        return "Flight{" + number + " · " + scheduledDep + " · " + status + "}";
    }
}
