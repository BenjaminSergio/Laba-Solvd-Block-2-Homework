package com.solvd.airline.service;

import com.solvd.airline.dao.BookingDao;
import com.solvd.airline.dao.DaoException;
import com.solvd.airline.dao.PassengerDao;
import com.solvd.airline.dao.TicketDao;
import com.solvd.airline.db.ConnectionPool;
import com.solvd.airline.entity.Booking;
import com.solvd.airline.entity.Flight;
import com.solvd.airline.entity.Passenger;
import com.solvd.airline.entity.Ticket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.Instant;
import java.util.UUID;

/**
 * Reference {@link BookingService} implementation using plain JDBC + the DAOs.
 *
 * The single transaction-owned method is {@link #bookFlight}: it
 *   1. checks the passenger exists,
 *   2. inserts the booking,
 *   3. inserts the ticket,
 * and either commits all three writes or rolls back the lot. That is the
 * unit-of-work boundary the lecture demonstrates on slide 05.1.
 *
 * Exam Q4 anchor: {@code setAutoCommit(false)} -> work -> {@code commit()}
 * (or {@code rollback()} on any exception). The {@code finally} block
 * resets autoCommit before the connection is returned to the pool —
 * pooled connections inherit prior state, so we ALWAYS reset.
 */
public class JdbcBookingService implements BookingService {

    private static final Logger log = LoggerFactory.getLogger(JdbcBookingService.class);

    private final PassengerDao passengers;
    private final BookingDao   bookings;
    private final TicketDao    tickets;

    public JdbcBookingService(PassengerDao passengers, BookingDao bookings, TicketDao tickets) {
        this.passengers = passengers;
        this.bookings   = bookings;
        this.tickets    = tickets;
    }

    @Override
    public Booking bookFlight(long passengerId, long flightId, long fareClassId, BigDecimal basePrice) {
        Passenger p = passengers.findById(passengerId)
                .orElseThrow(() -> new DaoException("Unknown passenger id=" + passengerId));

        Connection c = null;
        try {
            c = ConnectionPool.getInstance().acquire();
            c.setAutoCommit(false);                                                  // begin tx

            Booking b = new Booking();
            b.setBookingReference(generateReference());
            b.setPassengerId(p.getId());
            b.setStatus(Booking.Status.CONFIRMED);
            bookings.saveInTx(c, b);

            Ticket t = new Ticket();
            t.setTicketNumber(generateTicketNumber());
            t.setBookingId(b.getId());
            t.setFlightId(flightId);
            t.setFareClassId(fareClassId);
            t.setBasePrice(basePrice);
            t.setIssuedAt(Instant.now());
            tickets.saveInTx(c, t);

            c.commit();                                                              // commit
            log.info("Booked {} for passenger {}", b.getBookingReference(), p.getEmail());
            return b;
        } catch (SQLException e) {
            rollbackQuietly(c);
            throw new DaoException("bookFlight failed for passengerId=" + passengerId
                    + " flightId=" + flightId, e);
        } finally {
            resetAutoCommitAndRelease(c);
        }
    }

    @Override
    public void cancelBooking(long bookingId) {
        Booking b = bookings.findById(bookingId)
                .orElseThrow(() -> new DaoException("Unknown booking id=" + bookingId));
        if (b.getStatus() == Booking.Status.CANCELLED) {
            log.info("Booking {} already cancelled — no-op", b.getBookingReference());
            return;
        }
        b.setStatus(Booking.Status.CANCELLED);
        bookings.update(b);
    }

    // ----- helpers ------------------------------------------------------

    private static String generateReference() {
        return UUID.randomUUID().toString().substring(0, 6).toUpperCase();
    }

    private static String generateTicketNumber() {
        long n = (long)(Math.random() * 9_999_999_999_999L);
        return String.format("%013d", n);
    }

    private static void rollbackQuietly(Connection c) {
        if (c == null) return;
        try {
            c.rollback();
        } catch (SQLException ex) {
            log.warn("Rollback failed", ex);
        }
    }

    private static void resetAutoCommitAndRelease(Connection c) {
        if (c == null) return;
        try {
            c.setAutoCommit(true);                                                   // restore default
        } catch (SQLException ex) {
            log.warn("Resetting autoCommit failed", ex);
        }
        ConnectionPool.getInstance().release(c);
    }
}
