package com.solvd.airline.mapper;

import com.solvd.airline.entity.Ticket;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Optional;

/**
 * Annotation mapper for {@code tickets}.
 *
 * Used by {@link com.solvd.airline.service.MyBatisBookingService} —
 * within the same SqlSession as the booking insert, so both INSERTs
 * commit or roll back together.
 */
public interface TicketMapper {

    @Select("""
            SELECT ticket_id, ticket_number, booking_id, flight_id,
                   fare_class_id, base_price, issued_at
              FROM tickets
             WHERE ticket_id = #{id}
            """)
    Optional<Ticket> findById(@Param("id") Long id);

    @Select("""
            SELECT ticket_id, ticket_number, booking_id, flight_id,
                   fare_class_id, base_price, issued_at
              FROM tickets
             WHERE booking_id = #{bookingId}
             ORDER BY issued_at
            """)
    List<Ticket> findByBookingId(@Param("bookingId") Long bookingId);

    @Insert("""
            INSERT INTO tickets
                (ticket_number, booking_id, flight_id, fare_class_id,
                 base_price, issued_at)
            VALUES (#{ticketNumber}, #{bookingId}, #{flightId},
                    #{fareClassId}, #{basePrice}, #{issuedAt})
            """)
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "ticket_id")
    void save(Ticket ticket);

    @Delete("DELETE FROM tickets WHERE ticket_id = #{id}")
    boolean deleteById(@Param("id") Long id);
}
