package com.solvd.airline.entity.jackson.stubs;

import java.math.BigDecimal;
import java.time.Instant;

/**
 * STUB — homework task 4.
 *
 * <p>Annotate so {@code {"number": "T...", "flightNumber": "...", ...}}
 * round-trips. Coverage:
 *
 * <ul>
 *   <li>{@code ticketNumber} → {@code @JsonProperty("number")} — rename so the
 *       JSON key is shorter than the Java field name. (In JSON there is no
 *       attribute-vs-element distinction; the JAXB equivalent was
 *       {@code @XmlAttribute(name="number")}.)</li>
 *   <li>{@code flightNumber} → no annotation needed; field name = JSON key.</li>
 *   <li>{@code fareClass} → nested {@link FareClass} object. Jackson handles
 *       this recursively with no extra annotation — this is exam Q1's "complex
 *       object" answer at the simplest level.</li>
 *   <li>{@code basePrice} → no annotation. Jackson serialises
 *       {@link BigDecimal} as a JSON number (configurable via
 *       {@code DeserializationFeature.USE_BIG_DECIMAL_FOR_FLOATS} on the read
 *       side — already enabled in {@code JacksonMapper}).</li>
 *   <li>{@code issuedAt} → {@code @JsonFormat} for an explicit pattern, or
 *       leave plain to use the default ISO-8601 emitted by
 *       {@code JavaTimeModule}.</li>
 * </ul>
 */
public class Ticket {

    private String     ticketNumber;
    private String     flightNumber;
    private FareClass  fareClass;
    private BigDecimal basePrice;
    private Instant    issuedAt;

    // TODO: getters / setters / Jackson annotations
}
