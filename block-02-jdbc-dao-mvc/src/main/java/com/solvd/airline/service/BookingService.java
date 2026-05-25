package com.solvd.airline.service;

import com.solvd.airline.entity.Booking;

import java.math.BigDecimal;

/**
 * Service contract — framework-agnostic.
 *
 * Two implementations are envisaged:
 *   - {@link JdbcBookingService} — plain JDBC, the homework reference.
 *   - {@code JpaBookingService}  — same contract via JPA / Hibernate, shipped
 *                                  later in the course (Lecture 06+).
 *
 * Exam Q3 anchor: the service layer is what makes the DAO pattern useful in
 * a real application. A booking touches passengers + flights + bookings +
 * tickets — four tables, four DAOs. The transaction boundary belongs HERE,
 * not inside any single DAO.
 */
public interface BookingService {

    Booking bookFlight(long passengerId, long flightId, long fareClassId, BigDecimal basePrice);

    void cancelBooking(long bookingId);
}
