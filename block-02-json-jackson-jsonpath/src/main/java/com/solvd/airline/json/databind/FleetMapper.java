package com.solvd.airline.json.databind;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.solvd.airline.entity.jackson.Fleet;
import com.solvd.airline.json.core.JacksonMapper;

import java.io.IOException;
import java.nio.file.Path;

/**
 * Databind round-trip — the JSON parallel of {@code FleetMarshaller} (JAXB).
 *
 * <p>{@link ObjectMapper#readValue(java.io.File, Class)} parses JSON into a
 * fully-typed {@link Fleet} POJO; {@link ObjectMapper#writeValue(java.io.File,
 * Object)} serialises a {@link Fleet} back to JSON. Compared to tree-mode
 * traversal you trade some flexibility for type safety, IDE autocomplete,
 * and refactor-friendly field access.
 *
 * <p>The class is stateless and thread-safe — it holds the shared, frozen
 * {@link ObjectMapper} from {@link JacksonMapper}.
 */
public final class FleetMapper {

    private FleetMapper() { }

    private static final ObjectMapper MAPPER = JacksonMapper.instance();

    /** Read a {@code Fleet} from a JSON file on disk. */
    public static Fleet readFleet(Path jsonPath) throws IOException {
        return MAPPER.readValue(jsonPath.toFile(), Fleet.class);
    }

    /** Read a {@code Fleet} from a JSON string. */
    public static Fleet readFleet(String json) throws IOException {
        return MAPPER.readValue(json, Fleet.class);
    }

    /** Write a {@code Fleet} to a JSON file. */
    public static void writeFleet(Fleet fleet, Path outputPath) throws IOException {
        MAPPER.writeValue(outputPath.toFile(), fleet);
    }

    /** Serialise a {@code Fleet} to a JSON string (used by round-trip tests). */
    public static String writeFleetAsString(Fleet fleet) throws IOException {
        return MAPPER.writeValueAsString(fleet);
    }
}
