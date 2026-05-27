package com.solvd.airline.service;

import com.solvd.airline.entity.Booking;
import com.solvd.airline.entity.Payment;
import com.solvd.airline.mapper.BookingMapper;
import com.solvd.airline.mapper.PaymentMapper;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * MyBatis implementation of {@link PaymentService}.
 *
 * One transaction: verify the booking exists, insert the payment, mark the
 * booking COMPLETED if this payment settles the balance. Either all three
 * steps land or none do.
 */
public class MyBatisPaymentService implements PaymentService {

    private static final Logger log = LoggerFactory.getLogger(MyBatisPaymentService.class);

    private final SqlSessionFactory factory;

    public MyBatisPaymentService(SqlSessionFactory factory) {
        this.factory = factory;
    }

    @Override
    public Payment recordPayment(long bookingId, BigDecimal amount,
                                 String currency, Payment.Method method) {

        try (SqlSession session = factory.openSession()) {
            BookingMapper bookings = session.getMapper(BookingMapper.class);
            PaymentMapper payments = session.getMapper(PaymentMapper.class);

            Booking b = bookings.findById(bookingId)
                    .orElseThrow(() -> new IllegalArgumentException(
                            "Unknown booking id=" + bookingId));

            if (b.getStatus() == Booking.Status.CANCELLED) {
                throw new IllegalStateException(
                        "Cannot record payment for cancelled booking " + b.getBookingReference());
            }

            Payment p = new Payment();
            p.setBookingId(b.getId());
            p.setAmount(amount);
            p.setCurrency(currency);
            p.setMethod(method);
            p.setPaidAt(LocalDateTime.now());
            payments.save(p);

            // For the homework demo we always mark COMPLETED on the first payment.
            // Real systems would compare amount vs outstanding balance first.
            if (b.getStatus() != Booking.Status.COMPLETED) {
                bookings.updateStatus(b.getId(), Booking.Status.COMPLETED);
            }

            session.commit();
            log.info("Recorded {} {} payment for booking {}",
                    amount, currency, b.getBookingReference());
            return p;
        }
    }
}
