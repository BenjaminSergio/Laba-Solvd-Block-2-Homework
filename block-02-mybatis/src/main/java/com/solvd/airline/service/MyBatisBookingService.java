package com.solvd.airline.service;

import com.solvd.airline.entity.Booking;
import com.solvd.airline.entity.Passenger;
import com.solvd.airline.entity.Ticket;
import com.solvd.airline.mapper.BookingMapper;
import com.solvd.airline.mapper.PassengerMapper;
import com.solvd.airline.mapper.TicketMapper;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

/**
 * MyBatis implementation of the {@link BookingService} contract — the
 * drop-in replacement for Lecture 03's {@code JdbcBookingService}.
 *
 * Same logical operation (passenger lookup → booking insert → ticket insert),
 * same transactional guarantee (all three or none), drastically less code.
 * Compare the two side-by-side on Act 3 slide 27 of the lecture: the JDBC
 * version is ~55 lines (with manual Connection / setAutoCommit / rollback /
 * finally / resetAutoCommit dance); the MyBatis version is ~30 lines and
 * is structurally harder to write a transaction bug into.
 *
 * Exam Q3 anchor — {@code try (SqlSession session = factory.openSession())}
 * replaces the entire Connection lifecycle ceremony. Implicit rollback on
 * any exception (close-without-commit rolls back); explicit
 * {@code session.commit()} on the success path.
 */
public class MyBatisBookingService implements BookingService {

    private static final Logger log = LoggerFactory.getLogger(MyBatisBookingService.class);

    private final SqlSessionFactory factory;

    public MyBatisBookingService(SqlSessionFactory factory) {
        this.factory = factory;
    }

    @Override
    public Booking bookFlight(long passengerId, long flightId, long fareClassId,
                              BigDecimal basePrice) {

        try (SqlSession session = factory.openSession()) {                 // begin tx (manual commit)
            PassengerMapper passengers = session.getMapper(PassengerMapper.class);
            BookingMapper   bookings   = session.getMapper(BookingMapper.class);
            TicketMapper    tickets    = session.getMapper(TicketMapper.class);

            Passenger p = passengers.findById(passengerId)
                    .orElseThrow(() -> new IllegalArgumentException(
                            "Unknown passenger id=" + passengerId));

            Booking b = new Booking();
            b.setBookingReference(generateReference());
            b.setPassengerId(p.getId());
            b.setStatus(Booking.Status.CONFIRMED);
            bookings.save(b);                                              // INSERT #1 → b.id populated

            Ticket t = new Ticket();
            t.setTicketNumber(generateTicketNumber());
            t.setBookingId(b.getId());                                     // FK to booking just inserted
            t.setFlightId(flightId);
            t.setFareClassId(fareClassId);
            t.setBasePrice(basePrice);
            t.setIssuedAt(Instant.now());
            tickets.save(t);                                               // INSERT #2

            session.commit();                                              // commit both
            log.info("Booked {} for passenger <{}>", b.getBookingReference(), p.getEmail());
            return b;
        }                                                                  // close: any uncommitted → rollback
    }

    @Override
    public void cancelBooking(long bookingId) {
        try (SqlSession session = factory.openSession()) {
            BookingMapper bookings = session.getMapper(BookingMapper.class);
            Booking b = bookings.findById(bookingId)
                    .orElseThrow(() -> new IllegalArgumentException(
                            "Unknown booking id=" + bookingId));
            if (b.getStatus() == Booking.Status.CANCELLED) {
                log.info("Booking {} already cancelled — no-op", b.getBookingReference());
                return;
            }
            bookings.updateStatus(b.getId(), Booking.Status.CANCELLED);
            session.commit();
        }
    }

    // ----- helpers ------------------------------------------------------

    private static String generateReference() {
        return UUID.randomUUID().toString().substring(0, 6).toUpperCase();
    }

    private static String generateTicketNumber() {
        long n = (long)(Math.random() * 9_999_999_999_999L);
        return String.format("%013d", n);
    }
}
