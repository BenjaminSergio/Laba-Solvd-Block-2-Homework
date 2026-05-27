package com.solvd.airline.entity.jackson.stubs;

import java.time.Instant;
import java.util.List;

/**
 * STUB — homework task 4 (Jackson annotations) + task 5 (parse with Jackson).
 *
 * <p>Annotate this class so it round-trips against your {@code booking.json}
 * + {@code booking-schema.json}. Coverage to demonstrate — pick at least one
 * annotation from each of the five groups taught in Act 4:
 *
 * <ul>
 *   <li><strong>Group A · Naming</strong>: {@code @JsonPropertyOrder} on the
 *       class to set field order (mirrors JAXB {@code @XmlType(propOrder=...)}).
 *       Consider {@code @JsonRootName("booking")} if you enable
 *       {@code WRAP_ROOT_VALUE}.</li>
 *   <li><strong>Group B · Date</strong>: {@code @JsonFormat(shape=STRING,
 *       pattern="yyyy-MM-dd'T'HH:mm:ssX")} on {@code bookedAt} ({@link Instant}).
 *       Note: {@code Instant} natively serialises as ISO-8601 once
 *       {@code JavaTimeModule} is registered — the {@code @JsonFormat} only
 *       overrides the default pattern.</li>
 *   <li><strong>Group C · Skip / control</strong>: {@code @JsonIgnore} on
 *       {@code internalAuditNote} — this is the exam-Q3 anchor. The field
 *       must NEVER appear in JSON, either direction. Bonus: try
 *       {@code @JsonProperty(access = Access.READ_ONLY)} on a field you want
 *       to <em>read from</em> JSON but never <em>emit</em> back.</li>
 *   <li><strong>Group D · Polymorphism</strong>: see {@link Payment} — that is
 *       where {@code @JsonTypeInfo} + {@code @JsonSubTypes} belong, not here.</li>
 *   <li><strong>Group E · Construction</strong>: optional — convert to an
 *       immutable POJO and add a {@code @JsonCreator}-annotated constructor.</li>
 * </ul>
 *
 * <p>Use {@link com.solvd.airline.entity.jackson.Fleet} as the reference for
 * collections (in JSON, a {@code List<T>} field naturally serialises as a
 * JSON array — there is no wrapper-element step like JAXB needs). Use
 * {@link com.solvd.airline.entity.jackson.Aircraft} as the reference for
 * date formatting via {@code @JsonFormat}.
 */
public class Booking {

    public enum Status { PENDING, CONFIRMED, CANCELLED, COMPLETED }

    private String  bookingReference;
    private Long    passengerId;
    private Status  status;
    private Instant bookedAt;
    private List<Ticket>  tickets;
    private List<Payment> payments;

    /** Internal — must NEVER appear in JSON. Mark @JsonIgnore. */
    private String internalAuditNote;

    // TODO: getters / setters / Jackson annotations
}
