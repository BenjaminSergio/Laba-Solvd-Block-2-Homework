package com.solvd.airline.xml;

import com.solvd.airline.xml.dom.DomFleetParser;

import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Happy-path integration test for the reference DOM parser.
 * Asserts the airport count, the first IATA code, and the flight count
 * against the canonical {@code fleet.xml} resource.
 */
class DomFleetParserIT {

    private static final Path FLEET = Path.of("src/main/resources/xml/fleet.xml");

    @Test
    void parsesThreeAirports() throws Exception {
        List<String> iata = DomFleetParser.parseAirportIataCodes(FLEET);
        assertEquals(3, iata.size(), "expected 3 airports in fleet.xml");
        assertEquals("WAW", iata.get(0));
        assertEquals("KRK", iata.get(1));
        assertEquals("JFK", iata.get(2));
    }

    @Test
    void parsesThreeFlights() throws Exception {
        List<String> flightNums = DomFleetParser.parseFlightNumbers(FLEET);
        assertEquals(3, flightNums.size(), "expected 3 flights in fleet.xml");
        assertTrue(flightNums.contains("LO281"));
        assertTrue(flightNums.contains("LO282"));
        assertTrue(flightNums.contains("LO302"));
    }
}
