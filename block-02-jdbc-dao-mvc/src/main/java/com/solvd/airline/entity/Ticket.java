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

    public String getTicketNumber() {
        return ticketNumber;
    }

    public void setTicketNumber(String ticketNumber) {
        this.ticketNumber = ticketNumber;
    }

    public Long getBookingId() {
        return bookingId;
    }

    public void setBookingId(Long bookingId) {
        this.bookingId = bookingId;
    }

    public Long getFlightId() {
        return flightId;
    }

    public void setFlightId(Long flightId) {
        this.flightId = flightId;
    }

    public Long getFareClassId() {
        return fareClassId;
    }

    public void setFareClassId(Long fareClassId) {
        this.fareClassId = fareClassId;
    }

    public BigDecimal getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(BigDecimal basePrice) {
        this.basePrice = basePrice;
    }

    public Instant getIssuedAt() {
        return issuedAt;
    }

    public void setIssuedAt(Instant issuedAt) {
        this.issuedAt = issuedAt;
    }

    @java.lang.Override
    public java.lang.String toString() {
        return "Ticket{" +
                "ticketNumber='" + ticketNumber + '\'' +
                ", bookingId=" + bookingId +
                ", flightId=" + flightId +
                ", fareClassId=" + fareClassId +
                ", basePrice=" + basePrice +
                ", issuedAt=" + issuedAt +
                '}';
    }
}
