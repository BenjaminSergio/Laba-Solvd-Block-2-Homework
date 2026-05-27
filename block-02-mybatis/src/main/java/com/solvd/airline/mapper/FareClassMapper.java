package com.solvd.airline.mapper;

import com.solvd.airline.entity.FareClass;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Optional;

/**
 * Stub — homework. FareClass is reference data — three rows in
 * production, never updated outside of a release. **Strong L2-cache
 * candidate.** Enable {@code @CacheNamespace} on the interface or
 * declare {@code <cache/>} in the XML.
 */
public interface FareClassMapper {

    Optional<FareClass> findById(@Param("id") Long id);

    Optional<FareClass> findByCode(@Param("code") String code);

    List<FareClass> findAll();

    void save(FareClass fareClass);

    void update(FareClass fareClass);

    boolean deleteById(@Param("id") Long id);
}
