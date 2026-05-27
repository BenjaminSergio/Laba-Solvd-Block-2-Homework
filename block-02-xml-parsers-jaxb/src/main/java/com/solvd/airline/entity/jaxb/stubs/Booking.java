package com.solvd.airline.entity.jaxb.stubs;

import java.time.Instant;
import java.util.List;

/**
 * STUB — homework task 5.
 *
 * <p>Annotate this class with JAXB so it round-trips against your
 * {@code booking.xml} + {@code booking.xsd}. Coverage to demonstrate:
 *
 * <ul>
 *   <li>{@code @XmlRootElement} on the class.</li>
 *   <li>{@code @XmlAccessorType(XmlAccessType.FIELD)} at class level.</li>
 *   <li>{@code @XmlAttribute} on {@code bookingReference} (6-char metadata key)
 *       and {@code status} (enum) — meets the Act-1 attribute rule.</li>
 *   <li>{@code @XmlElement} on {@code passengerId}.</li>
 *   <li>{@code @XmlElementWrapper(name="tickets") @XmlElement(name="ticket")}
 *       on {@code tickets} — wrap the list.</li>
 *   <li>{@code @XmlElementWrapper(name="payments") @XmlElement(name="payment")}
 *       on {@code payments} — wrap the list.</li>
 *   <li>{@code @XmlJavaTypeAdapter(InstantAdapter.class)} +
 *       {@code @XmlSchemaType(name="dateTime")} on {@code bookedAt}.</li>
 *   <li>{@code @XmlEnum(String.class)} on the {@link Status} enum.</li>
 *   <li>{@code @XmlTransient} on {@code internalAuditNote} — exam Q7.</li>
 * </ul>
 *
 * <p>Use {@link com.solvd.airline.entity.jaxb.Flight} as the reference for
 * adapter + enum + propOrder. Use {@link com.solvd.airline.entity.jaxb.Fleet}
 * as the reference for wrapped collections.
 */
public class Booking {

    public enum Status { PENDING, CONFIRMED, CANCELLED, COMPLETED }

    private String  bookingReference;
    private Long    passengerId;
    private Status  status;
    private Instant bookedAt;
    private List<Ticket>  tickets;
    private List<Payment> payments;

    /** Internal — must NEVER appear in the XML. Mark @XmlTransient. */
    private String internalAuditNote;

    // TODO: getters / setters / annotations
}
