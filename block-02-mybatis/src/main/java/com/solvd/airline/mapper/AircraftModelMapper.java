package com.solvd.airline.mapper;

import com.solvd.airline.entity.AircraftModel;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Optional;

/**
 * Stub — homework. Aircraft model lookup is read-heavy and rarely
 * written; a strong candidate to enable the L2 cache on (see Act 6
 * of the lecture, the {@code <cache/>} tag in XML or
 * {@code @CacheNamespace} on the interface).
 */
public interface AircraftModelMapper {

    Optional<AircraftModel> findById(@Param("id") Long id);

    List<AircraftModel> findAll();

    List<AircraftModel> findByManufacturer(@Param("manufacturer") String manufacturer);

    void save(AircraftModel model);

    void update(AircraftModel model);

    boolean deleteById(@Param("id") Long id);
}
