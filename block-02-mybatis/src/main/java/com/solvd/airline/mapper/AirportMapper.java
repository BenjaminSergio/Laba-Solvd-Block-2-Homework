package com.solvd.airline.mapper;

import com.solvd.airline.entity.Airport;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Optional;

/**
 * XML-backed mapper — the reference for the XML mapping style.
 *
 * The implementation lives in {@code src/main/resources/mappers/AirportMapper.xml}.
 * MyBatis generates a JDK dynamic proxy that implements this interface at
 * runtime; each method invocation looks up the statement by namespace + id,
 * binds the arguments to the SQL's {@code #{...}} placeholders, runs the
 * statement against the current SqlSession's JDBC Connection, and maps the
 * result back into the declared return type.
 *
 * Use this style for any mapper that may grow dynamic SQL (the search()
 * method is the seed example — see the XML for the <where> + <if> tags).
 */
public interface AirportMapper {

    Optional<Airport> findById(@Param("id") Long id);

    Optional<Airport> findByIata(@Param("iata") String iata);

    List<Airport> findAll();

    /** Dynamic-SQL example: every parameter is optional. */
    List<Airport> search(@Param("countryCode") String countryCode,
                         @Param("city")        String city,
                         @Param("namePart")    String namePart);

    void save(Airport airport);

    void update(Airport airport);

    boolean deleteById(@Param("id") Long id);
}
