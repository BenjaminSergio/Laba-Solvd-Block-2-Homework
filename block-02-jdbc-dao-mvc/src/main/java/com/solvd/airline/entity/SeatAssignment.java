package com.solvd.airline.entity;

import java.time.Instant;

/**
 * Stub — homework. Maps the {@code seat_assignments} table.
 *
 * <pre>
 * assignment_id  BIGINT     PK
 * ticket_id      BIGINT     FK -> tickets,  UNIQUE (one seat per ticket)
 * seat_id        BIGINT     FK -> seats,    UNIQUE (one ticket per seat)
 * assigned_at    TIMESTAMP
 * </pre>
 *
 * The two UNIQUE constraints together implement a true 1 : 1 between
 * tickets and seats. TODO: fields, getters, setters.
 */
public class SeatAssignment extends BaseEntity {

    private Long    ticketId;
    private Long    seatId;
    private Instant assignedAt;

    // TODO: getters / setters
}
