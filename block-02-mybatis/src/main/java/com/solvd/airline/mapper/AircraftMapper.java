package com.solvd.airline.mapper;

import com.solvd.airline.entity.Aircraft;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Optional;

/**
 * Stub — homework.
 *
 * Decide whether to use annotations (model after {@link FlightMapper})
 * or an XML mapper (model after {@link AirportMapper} + AirportMapper.xml).
 * Either is acceptable; commit to one.
 *
 * Required methods: the five-CRUD baseline.
 * Suggested domain finder: {@code findByTailNumber}.
 *
 * Remember to register the mapper in {@code mybatis-config.xml}'s
 * {@code <mappers>} block (one line — class or resource).
 */
public interface AircraftMapper {

    Optional<Aircraft> findById(@Param("id") Long id);

    Optional<Aircraft> findByTailNumber(@Param("tailNumber") String tailNumber);

    List<Aircraft> findAll();

    void save(Aircraft aircraft);

    void update(Aircraft aircraft);

    boolean deleteById(@Param("id") Long id);
}
