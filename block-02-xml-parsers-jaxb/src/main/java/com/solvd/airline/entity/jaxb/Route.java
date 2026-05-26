package com.solvd.airline.entity.jaxb;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;

import java.util.Objects;

/**
 * JAXB binding for {@code <route>}.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RouteType",
         propOrder = {"originAirportIata", "destinationAirportIata", "distanceKm", "typicalDurationMin"})
public class Route {

    @XmlAttribute(name = "id", required = true)
    private Long id;

    @XmlElement(required = true)
    private String originAirportIata;

    @XmlElement(required = true)
    private String destinationAirportIata;

    @XmlElement(required = true)
    private int distanceKm;

    @XmlElement(required = true)
    private int typicalDurationMin;

    public Route() { }

    public Route(Long id, String originIata, String destIata, int distanceKm, int durationMin) {
        this.id = id;
        this.originAirportIata = originIata;
        this.destinationAirportIata = destIata;
        this.distanceKm = distanceKm;
        this.typicalDurationMin = durationMin;
    }

    public Long   getId()   { return id; }
    public void   setId(Long id) { this.id = id; }
    public String getOriginAirportIata()      { return originAirportIata; }
    public void   setOriginAirportIata(String i) { this.originAirportIata = i; }
    public String getDestinationAirportIata() { return destinationAirportIata; }
    public void   setDestinationAirportIata(String i) { this.destinationAirportIata = i; }
    public int    getDistanceKm()             { return distanceKm; }
    public void   setDistanceKm(int d)        { this.distanceKm = d; }
    public int    getTypicalDurationMin()     { return typicalDurationMin; }
    public void   setTypicalDurationMin(int d){ this.typicalDurationMin = d; }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (!(other instanceof Route r)) return false;
        return distanceKm == r.distanceKm
            && typicalDurationMin == r.typicalDurationMin
            && Objects.equals(id, r.id)
            && Objects.equals(originAirportIata, r.originAirportIata)
            && Objects.equals(destinationAirportIata, r.destinationAirportIata);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, originAirportIata, destinationAirportIata, distanceKm, typicalDurationMin);
    }

    @Override
    public String toString() {
        return "Route{" + originAirportIata + " → " + destinationAirportIata + " · " + distanceKm + " km}";
    }
}
