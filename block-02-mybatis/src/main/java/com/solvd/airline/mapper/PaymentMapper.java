package com.solvd.airline.mapper;

import com.solvd.airline.entity.Payment;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Optional;

/**
 * Annotation mapper for {@code payments}. Used by
 * {@link com.solvd.airline.service.MyBatisPaymentService}.
 */
public interface PaymentMapper {

    @Select("""
            SELECT payment_id, booking_id, amount, currency, method, paid_at
              FROM payments
             WHERE payment_id = #{id}
            """)
    Optional<Payment> findById(@Param("id") Long id);

    @Select("""
            SELECT payment_id, booking_id, amount, currency, method, paid_at
              FROM payments
             WHERE booking_id = #{bookingId}
             ORDER BY paid_at
            """)
    List<Payment> findByBookingId(@Param("bookingId") Long bookingId);

    @Insert("""
            INSERT INTO payments (booking_id, amount, currency, method, paid_at)
            VALUES (#{bookingId}, #{amount}, #{currency}, #{method}, #{paidAt})
            """)
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "payment_id")
    void save(Payment payment);

    @Delete("DELETE FROM payments WHERE payment_id = #{id}")
    boolean deleteById(@Param("id") Long id);
}
