package com.solvd.airline.entity;

import java.math.BigDecimal;
import java.time.Instant;

/**
 * Stub — homework. Maps the {@code tickets} table.
 *
 * <pre>
 * ticket_id      BIGINT        PK
 * ticket_number  CHAR(13)      UNIQUE — IATA 13-digit
 * booking_id     BIGINT        FK -> bookings (ON DELETE CASCADE)
 * flight_id      BIGINT        FK -> flights
 * fare_class_id  BIGINT        FK -> fare_classes
 * base_price     DECIMAL(10,2)
 * issued_at      TIMESTAMP
 * UNIQUE (booking_id, flight_id)
 * </pre>
 *
 * TODO: fields, getters, setters.
 */
public class Ticket extends BaseEntity {

    private String     ticketNumber;
    private Long       bookingId;
    private Long       flightId;
    private Long       fareClassId;
    private BigDecimal basePrice;
    private Instant    issuedAt;

    // TODO: getters / setters
}
