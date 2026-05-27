package com.solvd.airline.mapper;

import com.solvd.airline.entity.SeatAssignment;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Optional;

/**
 * Stub — homework. SeatAssignment is the join row between Ticket and
 * Seat. Add a domain finder {@code findByTicketId} that returns the
 * assignment for a given ticket (one-to-one in practice for most fares,
 * one-to-many for premium fares with both seat + bassinet allocation).
 */
public interface SeatAssignmentMapper {

    Optional<SeatAssignment> findById(@Param("id") Long id);

    List<SeatAssignment> findByTicketId(@Param("ticketId") Long ticketId);

    void save(SeatAssignment assignment);

    boolean deleteById(@Param("id") Long id);
}
