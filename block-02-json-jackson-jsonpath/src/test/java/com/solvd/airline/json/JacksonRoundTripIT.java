package com.solvd.airline.json;

import com.solvd.airline.entity.jackson.Aircraft;
import com.solvd.airline.entity.jackson.AircraftModel;
import com.solvd.airline.entity.jackson.Airport;
import com.solvd.airline.entity.jackson.Fleet;
import com.solvd.airline.entity.jackson.Flight;
import com.solvd.airline.entity.jackson.Route;
import com.solvd.airline.json.databind.FleetMapper;

import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Databind integration test — the contract for every annotated Jackson entity.
 *
 * <p>Mirrors {@code JaxbRoundTripIT} from Lecture 04. Same Java graph, JSON
 * wire format. The anchor test for exam Q1 (parse arrays / dates / complex
 * objects with Jackson).
 *
 * <ol>
 *   <li>Build a {@link Fleet} object graph in Java.</li>
 *   <li>Serialise it to a JSON string.</li>
 *   <li>Deserialise the string back to a {@link Fleet}.</li>
 *   <li>Assert source.equals(roundtripped).</li>
 * </ol>
 */
class JacksonRoundTripIT {

    @Test
    void roundTripFromConstructedGraph() throws Exception {
        Fleet src = sampleFleet();

        String json = FleetMapper.writeFleetAsString(src);
        assertTrue(json.contains("\"airports\""),
                "serialised JSON must include the airports array");
        assertTrue(json.contains("\"iata\" : \"WAW\""),
                "iata is a plain JSON key, not an XML-style attribute");

        Fleet back = FleetMapper.readFleet(json);
        assertEquals(src, back, "round-trip must preserve every field");
    }

    @Test
    void roundTripFromCanonicalFleetJson() throws Exception {
        Fleet onDisk = FleetMapper.readFleet(Path.of("src/main/resources/json/fleet.json"));
        assertNotNull(onDisk);
        assertEquals(3, onDisk.getAirports().size());
        assertEquals(2, onDisk.getAircraftModels().size());
        assertEquals(2, onDisk.getAircraft().size());
        assertEquals(2, onDisk.getRoutes().size());
        assertEquals(3, onDisk.getFlights().size());

        // Date round-trip — @JsonFormat + JavaTimeModule
        assertEquals(LocalDate.of(2019, 4, 12),
                onDisk.getAircraft().get(0).getDeliveredOn());
        assertEquals(LocalDateTime.of(2026, 6, 15, 8, 30),
                onDisk.getFlights().get(0).getScheduledDep());

        // Enum round-trip — Jackson serialises enums by name() by default
        assertEquals(Flight.Status.SCHEDULED, onDisk.getFlights().get(0).getStatus());

        // Marshal back, unmarshal again, equality holds end-to-end
        String json = FleetMapper.writeFleetAsString(onDisk);
        Fleet back = FleetMapper.readFleet(json);
        assertEquals(onDisk, back);
    }

    private static Fleet sampleFleet() {
        Fleet f = new Fleet();
        f.setAirports(List.of(
            new Airport("WAW", "Warsaw Chopin",       "Warsaw", "PL", "Europe/Warsaw"),
            new Airport("KRK", "Kraków John Paul II", "Kraków", "PL", "Europe/Warsaw")
        ));
        f.setAircraftModels(List.of(
            new AircraftModel(1L, "Boeing", "737-800", 189, 5765)
        ));
        f.setAircraft(List.of(
            new Aircraft("SP-LRG", 1L, true, LocalDate.of(2019, 4, 12))
        ));
        f.setRoutes(List.of(
            new Route(100L, "WAW", "JFK", 6855, 545)
        ));
        f.setFlights(List.of(
            new Flight("LO281", 100L, "SP-LRG",
                LocalDateTime.of(2026, 6, 15,  8, 30),
                LocalDateTime.of(2026, 6, 15, 11, 45),
                Flight.Status.SCHEDULED)
        ));
        return f;
    }
}
