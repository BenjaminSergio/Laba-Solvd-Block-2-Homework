package com.solvd.airline.mapper;

import com.solvd.airline.entity.Route;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Optional;

/**
 * Stub — homework. A route is keyed by (origin, destination); add a
 * domain finder {@code findByEndpoints(originId, destinationId)}.
 *
 * Bonus: a {@code <resultMap>} with two {@code <association>} blocks to
 * load Origin and Destination Airport entities in a single JOIN — the
 * Act 4 nested-result pattern in action.
 */
public interface RouteMapper {

    Optional<Route> findById(@Param("id") Long id);

    Optional<Route> findByEndpoints(@Param("originId") Long originId,
                                   @Param("destinationId") Long destinationId);

    List<Route> findAll();

    List<Route> findByOriginId(@Param("originId") Long originId);

    void save(Route route);

    void update(Route route);

    boolean deleteById(@Param("id") Long id);
}
