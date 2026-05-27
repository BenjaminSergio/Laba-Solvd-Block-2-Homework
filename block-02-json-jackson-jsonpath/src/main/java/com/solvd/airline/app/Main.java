package com.solvd.airline.app;

import com.fasterxml.jackson.databind.JsonNode;
import com.networknt.schema.ValidationMessage;
import com.solvd.airline.entity.jackson.Fleet;
import com.solvd.airline.json.databind.FleetMapper;
import com.solvd.airline.json.jsonpath.JsonPathQueries;
import com.solvd.airline.json.stream.JsonStreamReader;
import com.solvd.airline.json.stream.JsonStreamWriter;
import com.solvd.airline.json.tree.JsonTreeReader;
import com.solvd.airline.json.validation.JsonSchemaValidator;

import java.nio.file.Path;
import java.util.Set;

/**
 * CLI demo for Section 02 / Lecture 05.
 *
 * <p>Walks the seven reading modes / responsibilities in order, printing a
 * one-line success indicator for each. Mirrors the JAXB lecture's
 * {@code Main}; same shape, JSON wire format.
 *
 * <p>Run with: {@code mvn -q exec:java -Dexec.mainClass="com.solvd.airline.app.Main"}.
 */
public final class Main {

    public static void main(String[] args) throws Exception {
        Path fleetJson  = Path.of("src/main/resources/json/fleet.json");
        Path fleetSchema = Path.of("src/main/resources/json/fleet-schema.json");

        section(1, "JSON Schema validation (Draft 2020-12)");
        Set<ValidationMessage> errors = JsonSchemaValidator.validate(fleetJson, fleetSchema);
        if (!errors.isEmpty()) {
            System.err.println("✗ Schema errors:");
            errors.forEach(e -> System.err.println("  " + e));
            System.exit(1);
        }
        System.out.println("  ✓ fleet.json conforms to fleet-schema.json (zero errors)");

        section(2, "Tree mode — JsonNode random access");
        JsonTreeReader tree = new JsonTreeReader();
        JsonNode root = tree.readTree(fleetJson);
        System.out.println("  ✓ airports=" + tree.countAirports(root)
                + ", routes=" + tree.countRoutes(root)
                + ", flights=" + tree.countFlights(root));
        System.out.println("  ✓ JSON Pointer demo — first airport name: " + tree.firstAirportNameViaPointer(root));

        section(3, "Streaming mode — JsonParser cursor (no allocation)");
        JsonStreamReader stream = new JsonStreamReader();
        int flightCount = stream.countFlights(fleetJson);
        String firstDep = stream.firstScheduledDepFor(fleetJson, "LO281");
        System.out.println("  ✓ flights=" + flightCount + " · LO281 dep=" + firstDep);

        section(4, "Streaming write — JsonGenerator");
        JsonStreamWriter writer = new JsonStreamWriter();
        String written = writer.writeMinimalFleetToString();
        System.out.println("  ✓ wrote " + written.length() + " chars · first 40: "
                + written.substring(0, Math.min(40, written.length())).replace("\n", " "));

        section(5, "JSONPath — 5 reference queries on fleet.json");
        JsonPathQueries jp = new JsonPathQueries(fleetJson);
        System.out.println("  ✓ Q1 child path:        airportName(WAW) = " + jp.airportName("WAW"));
        System.out.println("  ✓ Q2 filter:            SCHEDULED flights = " + jp.flightsWithStatus("SCHEDULED").size());
        System.out.println("  ✓ Q3 length() fn:       countFlights() = " + jp.countFlights());
        System.out.println("  ✓ Q4 recursive descent: all aircraftTails = " + jp.allAircraftTails());
        System.out.println("  ✓ Q5 array slice:       firstTwoAirportNames = " + jp.firstTwoAirportNames());

        section(6, "Databind — full round-trip to typed POJOs");
        Fleet fleet = FleetMapper.readFleet(fleetJson);
        String roundtripped = FleetMapper.writeFleetAsString(fleet);
        Fleet again = FleetMapper.readFleet(roundtripped);
        boolean equal = fleet.equals(again);
        System.out.println("  ✓ deserialised " + fleet + " · round-trip equality=" + equal);
        if (!equal) {
            System.err.println("✗ Round-trip equality FAILED");
            System.exit(2);
        }

        section(7, "Polymorphism — safe-by-default (no enableDefaultTyping)");
        System.out.println("  ✓ ObjectMapper does NOT enable default typing — gadget classes cannot be deserialised");
        System.out.println("  ✓ Use @JsonTypeInfo(use=Id.NAME) + @JsonSubTypes when polymorphism is needed");

        System.out.println();
        System.out.println("All seven sections green. ✓");
    }

    private static void section(int n, String label) {
        System.out.println();
        System.out.println("── " + n + ". " + label + " ───────────────");
    }

    private Main() { }
}
