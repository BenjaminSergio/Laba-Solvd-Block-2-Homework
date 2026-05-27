package com.solvd.airline.service;

import com.solvd.airline.dao.BookingDao;
import com.solvd.airline.dao.DaoException;
import com.solvd.airline.dao.PaymentDao;
import com.solvd.airline.db.ConnectionPool;
import com.solvd.airline.entity.Booking;
import com.solvd.airline.entity.Payment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;

/**
 * Implementação JDBC de PaymentService.
 *
 * recordPayment é uma unidade de trabalho:
 *   1. INSERT em payments
 *   2. UPDATE bookings.status PENDING -> CONFIRMED (se aplicável)
 * Ou ambos commitam, ou ambos sofrem rollback.
 */
public class JdbcPaymentService implements PaymentService {

    private static final Logger log = LoggerFactory.getLogger(JdbcPaymentService.class);

    private final BookingDao bookings;
    private final PaymentDao payments;

    public JdbcPaymentService(BookingDao bookings, PaymentDao payments) {
        this.bookings = bookings;
        this.payments = payments;
    }

    @Override
    public Payment recordPayment(long bookingId, BigDecimal amount,
                                 String currency, Payment.Method method) {
        if (amount == null || amount.signum() <= 0) {
            throw new IllegalArgumentException("amount must be > 0: " + amount);
        }
        if (currency == null || currency.length() != 3) {
            throw new IllegalArgumentException("currency must be ISO-4217 (3 chars): " + currency);
        }

        Booking b = bookings.findById(bookingId)
                .orElseThrow(() -> new DaoException("Unknown booking id=" + bookingId));

        Connection c = null;
        try {
            c = ConnectionPool.getInstance().acquire();
            c.setAutoCommit(false);                                          // begin tx

            Payment p = new Payment();
            p.setBookingId(b.getId());
            p.setAmount(amount);
            p.setCurrency(currency.toUpperCase());
            p.setMethod(method);
            p.setPaidAt(LocalDateTime.now());
            payments.saveInTx(c, p);

            // PENDING -> CONFIRMED no primeiro pagamento bem-sucedido
            if (b.getStatus() == Booking.Status.PENDING && method != Payment.Method.REFUND) {
                String sql = "UPDATE bookings SET status = ? WHERE booking_id = ?";
                try (PreparedStatement ps = c.prepareStatement(sql)) {
                    ps.setString(1, Booking.Status.CONFIRMED.name());
                    ps.setLong  (2, b.getId());
                    ps.executeUpdate();
                }
            }

            c.commit();                                                      // commit
            log.info("Recorded {} {} {} for booking {}",
                    method, amount, currency, b.getBookingReference());
            return p;
        } catch (SQLException e) {
            rollbackQuietly(c);
            throw new DaoException("recordPayment failed for bookingId=" + bookingId, e);
        } finally {
            resetAutoCommitAndRelease(c);
        }
    }

    private static void rollbackQuietly(Connection c) {
        if (c == null) return;
        try { c.rollback(); }
        catch (SQLException ex) { log.warn("Rollback failed", ex); }
    }

    private static void resetAutoCommitAndRelease(Connection c) {
        if (c == null) return;
        try { c.setAutoCommit(true); }
        catch (SQLException ex) { log.warn("Resetting autoCommit failed", ex); }
        ConnectionPool.getInstance().release(c);
    }
}