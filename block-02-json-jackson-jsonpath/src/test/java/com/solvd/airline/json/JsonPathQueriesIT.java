package com.solvd.airline.json;

import com.solvd.airline.json.jsonpath.JsonPathQueries;

import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * JSONPath integration test — the five reference queries against fleet.json.
 */
class JsonPathQueriesIT {

    private static final Path FLEET_JSON = Path.of("src/main/resources/json/fleet.json");

    @Test
    void childPath_airportName() throws Exception {
        JsonPathQueries q = new JsonPathQueries(FLEET_JSON);
        assertEquals("Warsaw Chopin", q.airportName("WAW"));
        assertEquals("John F. Kennedy International", q.airportName("JFK"));
        assertNull(q.airportName("XXX"));
    }

    @Test
    void filterExpression_flightsWithStatus() throws Exception {
        JsonPathQueries q = new JsonPathQueries(FLEET_JSON);
        assertEquals(1, q.flightsWithStatus("SCHEDULED").size());
        assertEquals(1, q.flightsWithStatus("BOARDING").size());
        assertEquals(1, q.flightsWithStatus("ARRIVED").size());
        assertEquals(0, q.flightsWithStatus("CANCELLED").size());
    }

    @Test
    void functionCall_countFlights() throws Exception {
        JsonPathQueries q = new JsonPathQueries(FLEET_JSON);
        assertEquals(3, q.countFlights());
    }

    @Test
    void recursiveDescent_allAircraftTails() throws Exception {
        JsonPathQueries q = new JsonPathQueries(FLEET_JSON);
        List<String> tails = q.allAircraftTails();
        assertEquals(3, tails.size(),
            "recursive-descent $..aircraftTail walks every flight in fleet.json");
        assertTrue(tails.contains("SP-LRG"));
        assertTrue(tails.contains("SP-LIB"));
    }

    @Test
    void arraySlice_firstTwoAirportNames() throws Exception {
        JsonPathQueries q = new JsonPathQueries(FLEET_JSON);
        assertEquals(List.of("Warsaw Chopin", "Kraków John Paul II"),
                q.firstTwoAirportNames());
    }
}
