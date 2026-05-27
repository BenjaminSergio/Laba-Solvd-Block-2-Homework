package com.solvd.airline.json;

import com.fasterxml.jackson.databind.JsonNode;
import com.solvd.airline.json.tree.JsonTreeReader;

import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tree-mode integration test — JsonNode random access against fleet.json.
 */
class JsonTreeReaderIT {

    private static final Path FLEET_JSON = Path.of("src/main/resources/json/fleet.json");

    @Test
    void countsAirportsRoutesFlights() throws Exception {
        JsonTreeReader r = new JsonTreeReader();
        JsonNode root = r.readTree(FLEET_JSON);
        assertEquals(3, r.countAirports(root));
        assertEquals(2, r.countRoutes(root));
        assertEquals(3, r.countFlights(root));
    }

    @Test
    void collectsAllIataCodes() throws Exception {
        JsonTreeReader r = new JsonTreeReader();
        JsonNode root = r.readTree(FLEET_JSON);
        assertEquals(List.of("WAW", "KRK", "JFK"), r.allIataCodes(root));
    }

    @Test
    void firstAirportNameViaJsonPointer() throws Exception {
        JsonTreeReader r = new JsonTreeReader();
        JsonNode root = r.readTree(FLEET_JSON);
        assertEquals("Warsaw Chopin", r.firstAirportNameViaPointer(root));
    }
}
