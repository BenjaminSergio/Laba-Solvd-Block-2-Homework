package com.solvd.airline.mapper;

import com.solvd.airline.entity.Passenger;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;
import java.util.Optional;

/**
 * Annotation mapper for {@code passengers}. Used by
 * {@link com.solvd.airline.service.MyBatisBookingService} to look up
 * a passenger before inserting a booking.
 */
public interface PassengerMapper {

    @Select("""
            SELECT passenger_id, first_name, last_name, email,
                   phone, date_of_birth, created_at
              FROM passengers
             WHERE passenger_id = #{id}
            """)
    Optional<Passenger> findById(@Param("id") Long id);

    @Select("""
            SELECT passenger_id, first_name, last_name, email,
                   phone, date_of_birth, created_at
              FROM passengers
             WHERE email = #{email}
            """)
    Optional<Passenger> findByEmail(@Param("email") String email);

    @Select("""
            SELECT passenger_id, first_name, last_name, email,
                   phone, date_of_birth, created_at
              FROM passengers
             ORDER BY last_name, first_name
            """)
    List<Passenger> findAll();

    @Insert("""
            INSERT INTO passengers
                (first_name, last_name, email, phone, date_of_birth)
            VALUES (#{firstName}, #{lastName}, #{email}, #{phone}, #{dateOfBirth})
            """)
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "passenger_id")
    void save(Passenger passenger);

    @Update("""
            UPDATE passengers
               SET first_name    = #{firstName},
                   last_name     = #{lastName},
                   email         = #{email},
                   phone         = #{phone},
                   date_of_birth = #{dateOfBirth}
             WHERE passenger_id = #{id}
            """)
    void update(Passenger passenger);

    @Delete("DELETE FROM passengers WHERE passenger_id = #{id}")
    boolean deleteById(@Param("id") Long id);
}
