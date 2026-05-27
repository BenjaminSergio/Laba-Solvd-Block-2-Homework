package com.solvd.airline.entity.jaxb;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElementWrapper;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Root element of the fleet document. Maps to {@code <fleet>} in fleet.xml.
 *
 * <p>Demonstrates {@code @XmlElementWrapper} for five different collections —
 * the wrapper element groups repeated children under a named parent, matching
 * the XSD {@code <xs:sequence>} structure.
 */
@XmlRootElement(name = "fleet")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = {"airports", "aircraftModels", "aircraft", "routes", "flights"})
public class Fleet {

    @XmlElementWrapper(name = "airports")
    @XmlElement(name = "airport")
    private List<Airport> airports = new ArrayList<>();

    @XmlElementWrapper(name = "aircraftModels")
    @XmlElement(name = "aircraftModel")
    private List<AircraftModel> aircraftModels = new ArrayList<>();

    @XmlElementWrapper(name = "aircraft")
    @XmlElement(name = "aircraftUnit")
    private List<Aircraft> aircraft = new ArrayList<>();

    @XmlElementWrapper(name = "routes")
    @XmlElement(name = "route")
    private List<Route> routes = new ArrayList<>();

    @XmlElementWrapper(name = "flights")
    @XmlElement(name = "flight")
    private List<Flight> flights = new ArrayList<>();

    public Fleet() { }

    public List<Airport>        getAirports()        { return airports; }
    public void                 setAirports(List<Airport> a)        { this.airports = a; }
    public List<AircraftModel>  getAircraftModels()  { return aircraftModels; }
    public void                 setAircraftModels(List<AircraftModel> a) { this.aircraftModels = a; }
    public List<Aircraft>       getAircraft()        { return aircraft; }
    public void                 setAircraft(List<Aircraft> a)       { this.aircraft = a; }
    public List<Route>          getRoutes()          { return routes; }
    public void                 setRoutes(List<Route> r)            { this.routes = r; }
    public List<Flight>         getFlights()         { return flights; }
    public void                 setFlights(List<Flight> f)          { this.flights = f; }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (!(other instanceof Fleet f)) return false;
        return Objects.equals(airports, f.airports)
            && Objects.equals(aircraftModels, f.aircraftModels)
            && Objects.equals(aircraft, f.aircraft)
            && Objects.equals(routes, f.routes)
            && Objects.equals(flights, f.flights);
    }

    @Override
    public int hashCode() {
        return Objects.hash(airports, aircraftModels, aircraft, routes, flights);
    }

    @Override
    public String toString() {
        return "Fleet{airports=" + airports.size()
             + ", aircraftModels=" + aircraftModels.size()
             + ", aircraft=" + aircraft.size()
             + ", routes=" + routes.size()
             + ", flights=" + flights.size() + "}";
    }
}
