package com.solvd.airline.json.stream;

import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.solvd.airline.json.core.JacksonMapper;

import java.io.IOException;
import java.io.StringWriter;
import java.nio.file.Path;

/**
 * Streaming writer — the JSON parallel of XML's {@code XMLStreamWriter}.
 *
 * <p>{@link JsonGenerator} is the symmetric counterpart of {@link
 * com.fasterxml.jackson.core.JsonParser}: you tell the generator when to
 * write a {@code start_object}, when to write a field name, when to write
 * a value. Use it when:
 * <ul>
 *   <li>you are emitting very large JSON and cannot hold the full POJO in RAM;</li>
 *   <li>you are forwarding a stream from one source to one sink and want to
 *       transform key names or filter fields without allocating.</li>
 * </ul>
 *
 * <p>For ordinary cases, prefer {@code ObjectMapper.writeValue(...)}.
 */
public final class JsonStreamWriter {

    private final JsonFactory factory;

    public JsonStreamWriter() {
        ObjectMapper mapper = JacksonMapper.instance();
        this.factory = mapper.getFactory();
    }

    /**
     * Emit a hand-written tiny fleet document to a file. Used by the
     * CLI demo to prove the writer round-trips with the reader.
     */
    public void writeMinimalFleet(Path outputPath) throws IOException {
        try (JsonGenerator gen = factory.createGenerator(outputPath.toFile(), JsonEncoding.UTF8)) {
            gen.useDefaultPrettyPrinter();
            gen.writeStartObject();              // {
            gen.writeArrayFieldStart("airports");  //   "airports": [
            gen.writeStartObject();              //     {
            gen.writeStringField("iata", "WAW");
            gen.writeStringField("name", "Warsaw Chopin");
            gen.writeStringField("city", "Warsaw");
            gen.writeStringField("countryCode", "PL");
            gen.writeStringField("timezone", "Europe/Warsaw");
            gen.writeEndObject();                //     }
            gen.writeEndArray();                 //   ]
            gen.writeEndObject();                // }
        }
    }

    /** Emit the same minimal fleet to a string — used by smoke tests. */
    public String writeMinimalFleetToString() throws IOException {
        StringWriter buf = new StringWriter();
        try (JsonGenerator gen = factory.createGenerator(buf)) {
            gen.useDefaultPrettyPrinter();
            gen.writeStartObject();
            gen.writeArrayFieldStart("airports");
            gen.writeStartObject();
            gen.writeStringField("iata", "WAW");
            gen.writeStringField("name", "Warsaw Chopin");
            gen.writeStringField("city", "Warsaw");
            gen.writeStringField("countryCode", "PL");
            gen.writeStringField("timezone", "Europe/Warsaw");
            gen.writeEndObject();
            gen.writeEndArray();
            gen.writeEndObject();
        }
        return buf.toString();
    }
}
