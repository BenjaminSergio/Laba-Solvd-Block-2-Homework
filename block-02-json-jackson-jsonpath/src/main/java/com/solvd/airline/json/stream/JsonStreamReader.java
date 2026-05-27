package com.solvd.airline.json.stream;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.solvd.airline.json.core.JacksonMapper;

import java.io.IOException;
import java.nio.file.Path;

/**
 * Streaming reader — the JSON parallel of XML's StAX parser.
 *
 * <p>{@link JsonParser} is a <strong>pull-based cursor</strong>: you call
 * {@link JsonParser#nextToken()} to advance; you decide when to stop. The
 * parser holds at most one token's worth of state in memory, so the whole
 * file never lands in RAM. This is the right reading mode when:
 * <ul>
 *   <li>the document is too large to load as a tree;</li>
 *   <li>you only care about a subset of the data (early exit);</li>
 *   <li>throughput matters and you want zero allocation per object.</li>
 * </ul>
 *
 * <p>The trade-off: streaming code is <em>lower-level</em>. You drive the
 * loop, you maintain the state. Misread one token and the cursor desyncs.
 */
public final class JsonStreamReader {

    private final JsonFactory factory;

    public JsonStreamReader() {
        // Borrow the factory from the configured mapper so both reading
        // modes share the same character-encoding / parser-feature settings.
        ObjectMapper mapper = JacksonMapper.instance();
        this.factory = mapper.getFactory();
    }

    /**
     * Count flights using the streaming parser — without ever building a
     * {@code JsonNode} or {@code Flight} POJO in memory. This is the
     * minimum-allocation count.
     */
    public int countFlights(Path jsonPath) throws IOException {
        int count = 0;
        try (JsonParser parser = factory.createParser(jsonPath.toFile())) {
            // Walk to the "flights" key, then count the START_OBJECT tokens
            // immediately inside its surrounding START_ARRAY.
            while (parser.nextToken() != null) {
                if ("flights".equals(parser.currentName())
                        && parser.nextToken() == JsonToken.START_ARRAY) {
                    while (parser.nextToken() != JsonToken.END_ARRAY) {
                        if (parser.currentToken() == JsonToken.START_OBJECT) {
                            count++;
                            parser.skipChildren();   // skip the whole flight object
                        }
                    }
                    break;
                }
            }
        }
        return count;
    }

    /**
     * Find the scheduled-departure timestamp of the named flight, returning
     * as soon as it is found. Demonstrates early-exit streaming — the rest
     * of the file is never read.
     */
    public String firstScheduledDepFor(Path jsonPath, String flightNumber) throws IOException {
        try (JsonParser parser = factory.createParser(jsonPath.toFile())) {
            String currentNumber = null;
            while (parser.nextToken() != null) {
                if (parser.currentToken() == JsonToken.FIELD_NAME) {
                    String field = parser.currentName();
                    parser.nextToken();
                    if ("number".equals(field) && parser.currentToken() == JsonToken.VALUE_STRING) {
                        currentNumber = parser.getValueAsString();
                    } else if ("scheduledDep".equals(field)
                            && flightNumber.equals(currentNumber)
                            && parser.currentToken() == JsonToken.VALUE_STRING) {
                        return parser.getValueAsString();
                    }
                }
            }
        }
        return null;
    }
}
