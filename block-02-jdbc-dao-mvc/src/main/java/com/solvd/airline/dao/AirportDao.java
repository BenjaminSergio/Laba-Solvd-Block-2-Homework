package com.solvd.airline.dao;

import com.solvd.airline.db.ConnectionPool;
import com.solvd.airline.entity.Airport;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

/**
 * The reference DAO walked through in the lecture (slide 04.4).
 *
 * The base class {@link AbstractDao} handles the five generic CRUD methods.
 * This class supplies only the parts that vary per table:
 *   - the table + id column names
 *   - the INSERT and UPDATE SQL skeletons
 *   - row -> entity ({@link #mapRow})
 *   - entity -> placeholders ({@link #bindForSave}, {@link #bindForUpdate})
 *
 * Plus one query that has no analogue in the generic interface — find by
 * the natural key {@code iata}. Domain-specific finders always live on the
 * concrete DAO; the generic interface stays minimal.
 */
public class AirportDao extends AbstractDao<Airport> {

    @Override protected String tableName() { return "airports"; }
    @Override protected String idColumn()  { return "airport_id"; }

    @Override
    protected String insertSql() {
        return """
               INSERT INTO airports (iata, name, city, country_code, timezone)
               VALUES (?, ?, ?, ?, ?)
               """;
    }

    @Override
    protected String updateSql() {
        return """
               UPDATE airports
                  SET iata = ?, name = ?, city = ?, country_code = ?, timezone = ?
                WHERE airport_id = ?
               """;
    }

    @Override
    protected Airport mapRow(ResultSet rs) throws SQLException {
        Airport a = new Airport();
        a.setId(rs.getLong("airport_id"));
        a.setIata(rs.getString("iata"));
        a.setName(rs.getString("name"));
        a.setCity(rs.getString("city"));
        a.setCountryCode(rs.getString("country_code"));
        a.setTimezone(rs.getString("timezone"));
        return a;
    }

    @Override
    protected void bindForSave(PreparedStatement ps, Airport a) throws SQLException {
        ps.setString(1, a.getIata());
        ps.setString(2, a.getName());
        ps.setString(3, a.getCity());
        ps.setString(4, a.getCountryCode());
        ps.setString(5, a.getTimezone());
    }

    @Override
    protected void bindForUpdate(PreparedStatement ps, Airport a) throws SQLException {
        ps.setString(1, a.getIata());
        ps.setString(2, a.getName());
        ps.setString(3, a.getCity());
        ps.setString(4, a.getCountryCode());
        ps.setString(5, a.getTimezone());
        ps.setLong  (6, a.getId());
    }

    /**
     * Lookup by the natural unique key. Demonstrates a domain-specific finder
     * that does not belong on the generic {@link Dao} interface.
     */
    public Optional<Airport> findByIata(String iata) {
        String sql = "SELECT * FROM airports WHERE iata = ?";
        try (Connection c = ConnectionPool.getInstance().acquire();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, iata);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? Optional.of(mapRow(rs)) : Optional.empty();
            }
        } catch (SQLException e) {
            throw new DaoException("findByIata failed for iata=" + iata, e);
        }
    }
}
