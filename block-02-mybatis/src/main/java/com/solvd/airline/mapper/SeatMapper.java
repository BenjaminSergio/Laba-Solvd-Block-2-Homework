package com.solvd.airline.mapper;

import com.solvd.airline.entity.Seat;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Optional;

/**
 * Stub — homework. Add a finder {@code findByAircraftId} that returns
 * all seats for a given aircraft, ordered by seat_label. The
 * (aircraft, label) pair is the natural unique key — a domain finder
 * {@code findByAircraftAndLabel} is also useful.
 */
public interface SeatMapper {

    Optional<Seat> findById(@Param("id") Long id);

    List<Seat> findByAircraftId(@Param("aircraftId") Long aircraftId);

    Optional<Seat> findByAircraftAndLabel(@Param("aircraftId") Long aircraftId,
                                          @Param("seatLabel") String seatLabel);

    void save(Seat seat);

    boolean deleteById(@Param("id") Long id);
}
