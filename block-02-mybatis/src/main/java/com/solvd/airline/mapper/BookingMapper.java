package com.solvd.airline.mapper;

import com.solvd.airline.entity.Booking;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;
import java.util.Optional;

/**
 * Annotation mapper for {@code bookings}.
 *
 * Used by {@link com.solvd.airline.service.MyBatisBookingService} —
 * {@link #save(Booking)} populates the generated booking_id back onto the
 * entity, which the service then reads to set the FK on the child Ticket.
 */
public interface BookingMapper {

    @Select("""
            SELECT booking_id, booking_reference, passenger_id, status, booked_at
              FROM bookings
             WHERE booking_id = #{id}
            """)
    Optional<Booking> findById(@Param("id") Long id);

    @Select("""
            SELECT booking_id, booking_reference, passenger_id, status, booked_at
              FROM bookings
             WHERE booking_reference = #{ref}
            """)
    Optional<Booking> findByReference(@Param("ref") String reference);

    @Select("""
            SELECT booking_id, booking_reference, passenger_id, status, booked_at
              FROM bookings
             WHERE passenger_id = #{passengerId}
             ORDER BY booked_at DESC
            """)
    List<Booking> findByPassengerId(@Param("passengerId") Long passengerId);

    @Insert("""
            INSERT INTO bookings (booking_reference, passenger_id, status)
            VALUES (#{bookingReference}, #{passengerId}, #{status})
            """)
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "booking_id")
    void save(Booking booking);

    @Update("""
            UPDATE bookings
               SET booking_reference = #{bookingReference},
                   passenger_id      = #{passengerId},
                   status            = #{status}
             WHERE booking_id = #{id}
            """)
    void update(Booking booking);

    @Update("""
            UPDATE bookings SET status = #{status} WHERE booking_id = #{id}
            """)
    int updateStatus(@Param("id") Long id, @Param("status") Booking.Status status);

    @Delete("DELETE FROM bookings WHERE booking_id = #{id}")
    boolean deleteById(@Param("id") Long id);
}
