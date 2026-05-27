package com.solvd.airline.entity.jackson;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.Objects;

/**
 * Jackson binding for a route — same fields as the JAXB sibling.
 */
@JsonPropertyOrder({"id", "originAirportIata", "destinationAirportIata", "distanceKm", "typicalDurationMin"})
public class Route {

    private Long id;
    private String originAirportIata;
    private String destinationAirportIata;
    private int distanceKm;
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
