package com.solvd.airline.json.tree;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.solvd.airline.json.core.JacksonMapper;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * Tree-mode reader — the JSON parallel of XML's DOM parser.
 *
 * <p>{@link ObjectMapper#readTree(java.io.File)} parses the entire document
 * into an in-memory {@link JsonNode} tree, then offers <strong>random access</strong>
 * by path (the JSON analogue of {@code Document.getElementsByTagName}).
 *
 * <p>When to use tree mode: small-to-medium documents, ad-hoc traversal,
 * documents where the schema is unknown or the keys are dynamic.
 *
 * <p>When NOT to use tree mode: documents too large to hold in RAM (use the
 * streaming parser in {@link com.solvd.airline.json.stream.JsonStreamReader}),
 * or documents with a known schema where typed POJOs are cleaner (use the
 * databind path in {@link com.solvd.airline.json.databind.FleetMapper}).
 */
public final class JsonTreeReader {

    private final ObjectMapper mapper = JacksonMapper.instance();

    /** Read the entire JSON file into a mutable {@link JsonNode} tree. */
    public JsonNode readTree(Path jsonPath) throws IOException {
        return mapper.readTree(jsonPath.toFile());
    }

    /**
     * Count airports in the document. Demonstrates random access into the tree.
     */
    public int countAirports(JsonNode root) {
        return root.path("airports").size();
    }

    /** Count flights in the document. */
    public int countFlights(JsonNode root) {
        return root.path("flights").size();
    }

    /** Count routes in the document. */
    public int countRoutes(JsonNode root) {
        return root.path("routes").size();
    }

    /**
     * Walk every airport and collect its IATA code. Demonstrates iterating
     * an array node — {@code for (JsonNode n : arrayNode)}.
     */
    public List<String> allIataCodes(JsonNode root) {
        List<String> out = new ArrayList<>();
        for (JsonNode airport : root.path("airports")) {
            out.add(airport.path("iata").asText());
        }
        return out;
    }

    /**
     * Demonstrates JSON Pointer (RFC 6901) — the related-but-smaller cousin
     * of JSONPath. {@code JsonNode.at("/airports/0/name")} traverses the
     * single path with no filters, no recursion, no functions. Use Pointer
     * when you know exactly where the value is; use JSONPath when you need
     * filters and recursive descent.
     */
    public String firstAirportNameViaPointer(JsonNode root) {
        return root.at("/airports/0/name").asText();
    }
}
