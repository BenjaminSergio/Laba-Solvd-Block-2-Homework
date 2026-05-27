package com.solvd.airline.mapper;

import com.solvd.airline.entity.Flight;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Annotation-backed mapper — the reference for the interface-with-annotations
 * style. No XML file. All SQL lives inline in @Select / @Insert / @Update /
 * @Delete annotations.
 *
 * mapUnderscoreToCamelCase=true in mybatis-config.xml handles the
 * scheduled_dep -> scheduledDep mapping automatically — no <resultMap>
 * needed for entities whose columns follow the snake_case ↔ camelCase
 * convention.
 *
 * Use this style for any mapper whose SQL is static and short. The moment
 * a method needs dynamic SQL — conditional WHERE clauses, foreach IN
 * lists — escalate that method to XML (or fall back to @SelectProvider,
 * which is uglier than the XML it replaces).
 */
public interface FlightMapper {

    @Select("""
            SELECT flight_id, flight_number, route_id, aircraft_id,
                   scheduled_dep, scheduled_arr, status
              FROM flights
             WHERE flight_id = #{id}
            """)
    Optional<Flight> findById(@Param("id") Long id);

    @Select("""
            SELECT flight_id, flight_number, route_id, aircraft_id,
                   scheduled_dep, scheduled_arr, status
              FROM flights
             ORDER BY scheduled_dep
            """)
    List<Flight> findAll();

    @Select("""
            SELECT flight_id, flight_number, route_id, aircraft_id,
                   scheduled_dep, scheduled_arr, status
              FROM flights
             WHERE flight_number = #{flightNumber}
             ORDER BY scheduled_dep
            """)
    List<Flight> findByFlightNumber(@Param("flightNumber") String flightNumber);

    @Select("""
            SELECT flight_id, flight_number, route_id, aircraft_id,
                   scheduled_dep, scheduled_arr, status
              FROM flights
             ORDER BY scheduled_dep DESC
             LIMIT #{limit}
            """)
    List<Flight> findUpcoming(@Param("limit") int limit);

    @Insert("""
            INSERT INTO flights
                (flight_number, route_id, aircraft_id,
                 scheduled_dep, scheduled_arr, status)
            VALUES (#{flightNumber}, #{routeId}, #{aircraftId},
                    #{scheduledDep}, #{scheduledArr}, #{status})
            """)
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "flight_id")
    void save(Flight flight);

    @Update("""
            UPDATE flights
               SET flight_number = #{flightNumber},
                   route_id      = #{routeId},
                   aircraft_id   = #{aircraftId},
                   scheduled_dep = #{scheduledDep},
                   scheduled_arr = #{scheduledArr},
                   status        = #{status}
             WHERE flight_id = #{id}
            """)
    void update(Flight flight);

    @Update("""
            UPDATE flights
               SET status = #{status}
             WHERE flight_id = #{id}
            """)
    int updateStatus(@Param("id") Long id,
                     @Param("status") Flight.Status status);

    @Update("""
            UPDATE flights
               SET scheduled_dep = #{newDep},
                   scheduled_arr = #{newArr}
             WHERE flight_id = #{id}
            """)
    int reschedule(@Param("id") Long id,
                   @Param("newDep") LocalDateTime newDep,
                   @Param("newArr") LocalDateTime newArr);

    @Delete("DELETE FROM flights WHERE flight_id = #{id}")
    boolean deleteById(@Param("id") Long id);
}
