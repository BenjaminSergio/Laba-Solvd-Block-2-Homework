package com.solvd.airline.xml;

import com.solvd.airline.entity.jaxb.Aircraft;
import com.solvd.airline.entity.jaxb.AircraftModel;
import com.solvd.airline.entity.jaxb.Airport;
import com.solvd.airline.entity.jaxb.Fleet;
import com.solvd.airline.entity.jaxb.Flight;
import com.solvd.airline.entity.jaxb.Route;
import com.solvd.airline.xml.jaxb.FleetMarshaller;

import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * JAXB integration test — the contract for every annotated entity.
 *
 * <ol>
 *   <li>Build a {@link Fleet} object graph in Java.</li>
 *   <li>Marshal it to an XML string.</li>
 *   <li>Unmarshal the string back to a {@link Fleet}.</li>
 *   <li>Assert source.equals(roundtripped).</li>
 * </ol>
 *
 * <p>Any missing annotation, wrong propOrder, broken adapter, or forgotten
 * @XmlElementWrapper makes this test fail with a precise mismatch.
 */
class JaxbRoundTripIT {

    @Test
    void roundTripFromConstructedGraph() throws Exception {
        Fleet src = sampleFleet();

        String xml = FleetMarshaller.marshalToString(src);
        assertTrue(xml.contains("<fleet>"),     "marshalled XML should open with <fleet>");
        assertTrue(xml.contains("<airports>"),  "@XmlElementWrapper(name=\"airports\") must wrap the list");
        assertTrue(xml.contains("iata=\"WAW\""), "iata must be marshalled as an attribute");

        Fleet back = FleetMarshaller.unmarshal(xml);
        assertEquals(src, back, "round-trip should preserve every field");
    }

    @Test
    void roundTripFromCanonicalFleetXml() throws Exception {
        Fleet onDisk = FleetMarshaller.unmarshal(Path.of("src/main/resources/xml/fleet.xml"));
        assertNotNull(onDisk);
        assertEquals(3, onDisk.getAirports().size());
        assertEquals(2, onDisk.getAircraftModels().size());
        assertEquals(2, onDisk.getAircraft().size());
        assertEquals(2, onDisk.getRoutes().size());
        assertEquals(3, onDisk.getFlights().size());

        // Adapter round-trip — dates and dateTimes must survive
        assertEquals(LocalDate.of(2019, 4, 12),
                onDisk.getAircraft().get(0).getDeliveredOn());
        assertEquals(LocalDateTime.of(2026, 6, 15, 8, 30),
                onDisk.getFlights().get(0).getScheduledDep());
        assertEquals(Flight.Status.SCHEDULED, onDisk.getFlights().get(0).getStatus());

        // Marshal back, unmarshal again, equality holds end-to-end
        String xml = FleetMarshaller.marshalToString(onDisk);
        Fleet back = FleetMarshaller.unmarshal(xml);
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
