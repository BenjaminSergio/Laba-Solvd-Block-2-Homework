package com.solvd.airline.entity;

import java.time.Instant;

/**
 * Maps the {@code bookings} table.
 *
 * <pre>
 * booking_id        BIGINT      PK
 * booking_reference CHAR(6)     UNIQUE — "ABC123"
 * passenger_id      BIGINT      FK -> passengers
 * status            ENUM('PENDING','CONFIRMED','CANCELLED','COMPLETED')
 * booked_at         TIMESTAMP   default CURRENT_TIMESTAMP
 * </pre>
 */
public class Booking extends BaseEntity {

    public enum Status { PENDING, CONFIRMED, CANCELLED, COMPLETED }

    private String  bookingReference;
    private Long    passengerId;
    private Status  status;
    private Instant bookedAt;

    public Booking() { }

    public String getBookingReference()              { return bookingReference; }
    public void   setBookingReference(String ref)    { this.bookingReference = ref; }

    public Long   getPassengerId()                   { return passengerId; }
    public void   setPassengerId(Long passengerId)   { this.passengerId = passengerId; }

    public Status getStatus()                        { return status; }
    public void   setStatus(Status status)           { this.status = status; }

    public Instant getBookedAt()                     { return bookedAt; }
    public void    setBookedAt(Instant bookedAt)     { this.bookedAt = bookedAt; }

    @Override
    public String toString() {
        return "Booking{" + bookingReference + " · passenger=" + passengerId + " · " + status + "}";
    }
}
