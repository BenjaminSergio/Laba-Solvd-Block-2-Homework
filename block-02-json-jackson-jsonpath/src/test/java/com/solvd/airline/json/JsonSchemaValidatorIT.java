package com.solvd.airline.json;

import com.networknt.schema.ValidationMessage;
import com.solvd.airline.json.validation.JsonSchemaValidator;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Happy-path + sad-path integration test for {@link JsonSchemaValidator}.
 *
 * <ul>
 *   <li>The reference {@code fleet.json} validates cleanly against
 *       {@code fleet-schema.json}.</li>
 *   <li>A payload with a 4-letter IATA code (violating the
 *       {@code [A-Z]{3}} pattern) is rejected with a precise message.</li>
 * </ul>
 */
class JsonSchemaValidatorIT {

    private static final Path FLEET_JSON   = Path.of("src/main/resources/json/fleet.json");
    private static final Path FLEET_SCHEMA = Path.of("src/main/resources/json/fleet-schema.json");

    @Test
    void canonicalDocumentValidates() throws Exception {
        Set<ValidationMessage> errors = JsonSchemaValidator.validate(FLEET_JSON, FLEET_SCHEMA);
        assertTrue(errors.isEmpty(),
            "fleet.json must validate cleanly · errors=" + errors);
    }

    @Test
    void invalidIataCodeRejected() throws Exception {
        // 4-letter IATA — violates the IataCode pattern ^[A-Z]{3}$
        String bad = """
            {
              "airports": [
                {"iata": "WAWW", "name": "x", "city": "x", "countryCode": "PL", "timezone": "x"}
              ],
              "aircraftModels": [],
              "aircraft": [],
              "routes": [],
              "flights": []
            }
            """;
        Set<ValidationMessage> errors = JsonSchemaValidator.validate(
                new ByteArrayInputStream(bad.getBytes(StandardCharsets.UTF_8)),
                FLEET_SCHEMA);
        assertFalse(errors.isEmpty(),
            "Schema should reject the 4-letter IATA code");
        String allMessages = errors.toString();
        assertTrue(allMessages.contains("iata") || allMessages.toLowerCase().contains("pattern"),
            "rejection should name iata or the pattern rule: " + allMessages);
    }
}
