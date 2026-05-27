package com.solvd.airline.json.core;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;

/**
 * Centralised, preconfigured {@link ObjectMapper} factory.
 *
 * <p>An {@code ObjectMapper} is expensive to build and <strong>thread-safe
 * after configuration</strong> — but mutating it after first use is not.
 * The standard pattern is to build one per application, freeze it, and share.
 *
 * <p>Configuration choices, each with a stated reason:
 * <ul>
 *   <li>{@link JavaTimeModule} — registers serialisers for {@code Instant},
 *       {@code LocalDate}, {@code LocalDateTime}, {@code ZonedDateTime},
 *       {@code OffsetDateTime}. Without this, Jackson refuses to serialise
 *       {@code java.time.*} types.</li>
 *   <li>{@link Jdk8Module} — adds support for {@code Optional<T>},
 *       {@code OptionalLong}, {@code OptionalInt}. A present {@code Optional}
 *       serialises as its value; an empty one serialises as {@code null}
 *       (and is then omitted when {@code Include.NON_NULL} is set).</li>
 *   <li>{@link ParameterNamesModule} — lets Jackson find constructor parameter
 *       names without an explicit {@code @JsonProperty} on every arg. Requires
 *       the compiler flag {@code -parameters} (set in {@code pom.xml}).</li>
 *   <li>{@code WRITE_DATES_AS_TIMESTAMPS = false} — dates as ISO-8601 strings
 *       (the human-readable default), not epoch-millis numbers.</li>
 *   <li>{@code FAIL_ON_UNKNOWN_PROPERTIES = true} — strict reads catch typos.
 *       Production code often flips this to {@code false} for forward
 *       compatibility; the lecture default is strict so students see errors.</li>
 *   <li>{@code Include.NON_NULL} — null fields are omitted from output,
 *       producing cleaner JSON. Matches the implicit JAXB behaviour.</li>
 *   <li>{@code INDENT_OUTPUT = true} — pretty-printed JSON for human reading
 *       in tests and demos. Production may flip this off for wire economy.</li>
 * </ul>
 */
public final class JacksonMapper {

    private JacksonMapper() { }

    private static final ObjectMapper INSTANCE = build();

    /** Returns the singleton, frozen mapper. Safe to call from any thread. */
    public static ObjectMapper instance() {
        return INSTANCE;
    }

    /** Build a fresh mapper. Test code may use this to obtain a mutable copy. */
    public static ObjectMapper build() {
        return JsonMapper.builder()
                .addModule(new JavaTimeModule())
                .addModule(new Jdk8Module())
                .addModule(new ParameterNamesModule())
                .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
                .enable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
                .enable(SerializationFeature.INDENT_OUTPUT)
                .enable(MapperFeature.PROPAGATE_TRANSIENT_MARKER)
                .serializationInclusion(JsonInclude.Include.NON_NULL)
                .build();
    }
}
