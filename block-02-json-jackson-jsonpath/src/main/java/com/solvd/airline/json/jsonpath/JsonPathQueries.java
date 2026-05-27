package com.solvd.airline.json.jsonpath;

import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.Option;
import com.jayway.jsonpath.spi.json.JacksonJsonProvider;
import com.jayway.jsonpath.spi.mapper.JacksonMappingProvider;
import com.solvd.airline.json.core.JacksonMapper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;

/**
 * REFERENCE — five JSONPath queries against {@code fleet.json}, mirroring
 * the five XPath queries in {@link com.solvd.airline.xml.xpath.XPathQueries}
 * from Lecture 04. Each returns a different typed shape so students see the
 * full range of Jayway's API.
 *
 * <p>The JSONPath syntax is Stefan Goessner's 2007 specification — formalised
 * as <strong>RFC 9535</strong> in 2024. Quick syntax table:
 * <pre>
 *   $              the root document
 *   .name          child field
 *   ..name         recursive descent — every {@code name} key, any depth
 *   [n]            array index (0-based)
 *   [a:b]          array slice [a, b)
 *   [?(EXPR)]      filter expression on each array element
 *   [*]            wildcard — every element
 *   length()       function — array length / string length
 * </pre>
 *
 * <p>JSONPath is the JSON cousin of XPath; it is <strong>not</strong> the
 * same as Jackson's {@code JsonNode.at(...)}, which is JSON Pointer (RFC 6901).
 * Pointer is single-path-only; JSONPath has filters and recursion.
 */
public final class JsonPathQueries {

    private final DocumentContext ctx;

    public JsonPathQueries(Path jsonPath) throws IOException {
        // Use Jackson as Jayway's underlying provider so the same date /
        // number conventions apply across both libraries.
        Configuration config = Configuration.builder()
                .jsonProvider(new JacksonJsonProvider(JacksonMapper.instance()))
                .mappingProvider(new JacksonMappingProvider(JacksonMapper.instance()))
                .options(Option.SUPPRESS_EXCEPTIONS)   // missing paths → null, not throw
                .build();
        String json = Files.readString(jsonPath);
        this.ctx = JsonPath.using(config).parse(json);
    }

    /**
     * Query 1 · <strong>child-path</strong> · {@code $.airports[?(@.iata == 'WAW')].name}.
     * Returns the name of the airport with the given IATA code.
     */
    public String airportName(String iata) {
        List<String> result = ctx.read(
                "$.airports[?(@.iata == '" + iata + "')].name", List.class);
        return result.isEmpty() ? null : result.get(0);
    }

    /**
     * Query 2 · <strong>filter expression</strong> · {@code $.flights[?(@.status == 'SCHEDULED')]}.
     * Returns every flight with the given status.
     */
    public List<Map<String, Object>> flightsWithStatus(String status) {
        return ctx.read("$.flights[?(@.status == '" + status + "')]", List.class);
    }

    /**
     * Query 3 · <strong>function call</strong> · {@code $.flights.length()}.
     * Counts every flight in the document. Demonstrates the {@code length()}
     * function — Jayway's parallel of XPath's {@code count(...)}.
     */
    public int countFlights() {
        Integer n = ctx.read("$.flights.length()", Integer.class);
        return n == null ? 0 : n;
    }

    /**
     * Query 4 · <strong>recursive descent</strong> · {@code $..aircraftTail}.
     * Walks every depth of the document and collects every {@code aircraftTail}
     * value it finds — useful when the schema is unknown or fields can be
     * nested at multiple depths. Returns one entry per flight in fleet.json.
     */
    public List<String> allAircraftTails() {
        return ctx.read("$..aircraftTail", List.class);
    }

    /**
     * Query 5 · <strong>array slice</strong> · {@code $.airports[0:2].name}.
     * Returns the names of the first two airports. Slices use {@code [a:b]}
     * half-open semantics; {@code [0:]} means "from index 0 to the end".
     */
    public List<String> firstTwoAirportNames() {
        return ctx.read("$.airports[0:2].name", List.class);
    }
}
