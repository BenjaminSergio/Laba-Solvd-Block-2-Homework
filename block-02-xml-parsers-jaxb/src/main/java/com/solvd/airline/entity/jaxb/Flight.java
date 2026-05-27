package com.solvd.airline.entity.jaxb;

import com.solvd.airline.xml.jaxb.adapters.LocalDateTimeAdapter;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlEnum;
import jakarta.xml.bind.annotation.XmlSchemaType;
import jakarta.xml.bind.annotation.XmlType;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * JAXB binding for {@code <flight>}.
 *
 * <p>Demonstrates:
 * <ul>
 *   <li>{@link XmlJavaTypeAdapter} on {@link LocalDateTime} fields (no native JAXB support).</li>
 *   <li>{@code @XmlEnum} on the {@link Status} enum — UPPERCASE names match the XSD enumeration verbatim,
 *       so {@code @XmlEnumValue} is not needed.</li>
 * </ul>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "FlightType",
         propOrder = {"routeId", "aircraftTail", "scheduledDep", "scheduledArr", "status"})
public class Flight {

    @XmlEnum(String.class)
    public enum Status { SCHEDULED, BOARDING, DEPARTED, ARRIVED, CANCELLED }

    @XmlAttribute(name = "number", required = true)
    private String number;

    @XmlElement(required = true)
    private Long routeId;

    @XmlElement
    private String aircraftTail;

    @XmlElement(required = true)
    @XmlSchemaType(name = "dateTime")
    @XmlJavaTypeAdapter(LocalDateTimeAdapter.class)
    private LocalDateTime scheduledDep;

    @XmlElement(required = true)
    @XmlSchemaType(name = "dateTime")
    @XmlJavaTypeAdapter(LocalDateTimeAdapter.class)
    private LocalDateTime scheduledArr;

    @XmlElement(required = true)
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
