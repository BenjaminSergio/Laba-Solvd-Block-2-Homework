package com.solvd.airline.entity.jaxb.stubs;

import java.math.BigDecimal;
import java.time.Instant;

/**
 * STUB — homework task 5.
 *
 * <p>Annotate so {@code <ticket number="..."> ... </ticket>} round-trips.
 *
 * <ul>
 *   <li>{@code ticketNumber} → {@code @XmlAttribute(name="number")}.</li>
 *   <li>{@code flightNumber} → {@code @XmlElement} (string FK to a Flight).</li>
 *   <li>{@code fareClass} → {@code @XmlElement} (nested {@link FareClass}).</li>
 *   <li>{@code basePrice} → {@code @XmlElement} ({@code BigDecimal} maps natively to {@code xs:decimal}).</li>
 *   <li>{@code issuedAt} → {@code @XmlElement} + {@code XmlAdapter} for {@link Instant}.</li>
 * </ul>
 */
public class Ticket {

    private String     ticketNumber;
    private String     flightNumber;
    private FareClass  fareClass;
    private BigDecimal basePrice;
    private Instant    issuedAt;

    // TODO: getters / setters / annotations
}
