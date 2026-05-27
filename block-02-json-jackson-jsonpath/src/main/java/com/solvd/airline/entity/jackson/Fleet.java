package com.solvd.airline.entity.jackson;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonRootName;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Root POJO of the fleet document. Maps to the JSON root object in fleet.json.
 *
 * <p>Demonstrates {@code @JsonRootName} — only activated when the mapper is
 * configured with {@code SerializationFeature.WRAP_ROOT_VALUE} / {@code
 * DeserializationFeature.UNWRAP_ROOT_VALUE}. The default Jackson behaviour is
 * to <strong>not</strong> wrap the root, so {@code @JsonRootName} is a hint
 * for the wrapped variant. We keep the annotation here as a teaching artefact —
 * the JAXB analogue is {@code @XmlRootElement(name = "fleet")}.
 *
 * <p>{@code @JsonPropertyOrder} mirrors JAXB's {@code @XmlType(propOrder=...)} —
 * it controls the order of keys when serialising, important for human-readable
 * diffs against the XML lecture's {@code fleet.xml}.
 *
 * <p>No {@code @JsonElementWrapper} equivalent is needed: JSON arrays
 * <em>are</em> the wrapper.
 */
@JsonRootName("fleet")
@JsonPropertyOrder({"airports", "aircraftModels", "aircraft", "routes", "flights"})
public class Fleet {

    private List<Airport> airports = new ArrayList<>();
    private List<AircraftModel> aircraftModels = new ArrayList<>();
    private List<Aircraft> aircraft = new ArrayList<>();
    private List<Route> routes = new ArrayList<>();
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
