package com.solvd.airline.json.validation;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.networknt.schema.JsonSchema;
import com.networknt.schema.JsonSchemaFactory;
import com.networknt.schema.SpecVersion;
import com.networknt.schema.ValidationMessage;
import com.solvd.airline.json.core.JacksonMapper;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Set;

/**
 * JSON Schema validator — the JSON parallel of {@code XsdValidator}.
 *
 * <p>Built against <strong>JSON Schema Draft 2020-12</strong>, the latest
 * IETF spec (RFC-track, not yet finalised). networknt's
 * {@link JsonSchemaFactory} also supports Draft-07 and 2019-09 — the spec
 * version is passed explicitly at factory construction time.
 *
 * <p>The class is stateless and thread-safe after construction.
 */
public final class JsonSchemaValidator {

    private JsonSchemaValidator() { }

    private static final JsonSchemaFactory FACTORY =
            JsonSchemaFactory.getInstance(SpecVersion.VersionFlag.V202012);

    private static final ObjectMapper MAPPER = JacksonMapper.instance();

    /**
     * Validate a JSON file against a JSON Schema file. Returns the set of
     * validation messages — <strong>empty</strong> means the JSON is valid.
     *
     * @throws IOException if either file is unreadable or malformed
     */
    public static Set<ValidationMessage> validate(Path jsonPath, Path schemaPath) throws IOException {
        try (InputStream schemaIn = Files.newInputStream(schemaPath)) {
            JsonSchema schema = FACTORY.getSchema(schemaIn);
            JsonNode node = MAPPER.readTree(jsonPath.toFile());
            return schema.validate(node);
        }
    }

    /**
     * Validate JSON bytes (from a stream) against a JSON Schema file —
     * used by the security IT to feed a hand-crafted payload.
     */
    public static Set<ValidationMessage> validate(InputStream jsonStream, Path schemaPath) throws IOException {
        try (InputStream schemaIn = Files.newInputStream(schemaPath)) {
            JsonSchema schema = FACTORY.getSchema(schemaIn);
            JsonNode node = MAPPER.readTree(jsonStream);
            return schema.validate(node);
        }
    }

    /**
     * Convenience: validate a JSON file against a schema shipped on the
     * classpath (under {@code src/main/resources/}).
     */
    public static Set<ValidationMessage> validateClasspath(Path jsonPath, String schemaResource) throws IOException {
        try (InputStream schemaIn =
                     JsonSchemaValidator.class.getClassLoader().getResourceAsStream(schemaResource)) {
            if (schemaIn == null) throw new IOException("Schema resource not found: " + schemaResource);
            JsonSchema schema = FACTORY.getSchema(schemaIn);
            JsonNode node = MAPPER.readTree(jsonPath.toFile());
            return schema.validate(node);
        }
    }
}
