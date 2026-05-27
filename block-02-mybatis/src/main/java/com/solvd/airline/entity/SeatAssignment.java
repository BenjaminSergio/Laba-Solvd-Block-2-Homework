package com.solvd.airline.entity;

/**
 * Stub — homework. Maps the {@code seat_assignments} table.
 *
 * <pre>
 * assignment_id  BIGINT  PK
 * ticket_id      BIGINT  FK -> tickets
 * seat_id        BIGINT  FK -> seats
 * </pre>
 *
 * TODO: copy from your Lecture 03 entity.
 */
public class SeatAssignment extends BaseEntity {

    private Long ticketId;
    private Long seatId;

    public Long getTicketId()             { return ticketId; }
    public void setTicketId(Long t)       { this.ticketId = t; }

    public Long getSeatId()               { return seatId; }
    public void setSeatId(Long s)         { this.seatId = s; }
}
