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

    public Long getTicketId() {
        return ticketId;
    }

    public void setTicketId(Long ticketId) {
        this.ticketId = ticketId;
    }

    public Long getSeatId() {
        return seatId;
    }

    public void setSeatId(Long seatId) {
        this.seatId = seatId;
    }

    public Instant getAssignedAt() {
        return assignedAt;
    }

    public void setAssignedAt(Instant assignedAt) {
        this.assignedAt = assignedAt;
    }

    @java.lang.Override
    public java.lang.String toString() {
        return "SeatAssignment{" +
                "ticketId=" + ticketId +
                ", seatId=" + seatId +
                ", assignedAt=" + assignedAt +
                '}';
    }
}
