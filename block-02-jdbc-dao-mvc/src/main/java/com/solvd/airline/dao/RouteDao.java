package com.solvd.airline.dao;

import com.solvd.airline.entity.Route;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/** Stub — homework. Table {@code routes}. Two FKs to {@code airports}. */
public class RouteDao extends AbstractDao<Route> {

    @Override protected String tableName() { return "routes"; }
    @Override protected String idColumn()  { return "route_id"; }

    @Override protected String insertSql() {
        return """
               INSERT INTO routes
                   (origin_airport_id, destination_airport_id, distance_km, typical_duration_min)
               VALUES (?, ?, ?, ?)
               """;
    }
    @Override protected String updateSql() {
        return """
               UPDATE routes
                  SET origin_airport_id = ?, destination_airport_id = ?,
                      distance_km = ?, typical_duration_min = ?
                WHERE route_id = ?
               """;
    }

    @Override protected Route mapRow(ResultSet rs) throws SQLException {
        Route r = new Route();
        r.setId(rs.getLong("route_id"));
        r.setOriginAirportId(rs.getLong("origin_airport_id"));
        r.setDestinationAirportId(rs.getLong("destination_airport_id"));
        r.setDistanceKm(rs.getInt("distance_km"));
        r.setTypicalDurationMin(rs.getInt("typical_duration_min"));
        return r;
    }
    @Override protected void bindForSave(PreparedStatement ps, Route r) throws SQLException {
        ps.setLong(1, r.getOriginAirportId());
        ps.setLong(2, r.getDestinationAirportId());
        ps.setInt (3, r.getDistanceKm());
        ps.setInt (4, r.getTypicalDurationMin());
    }
    @Override protected void bindForUpdate(PreparedStatement ps, Route r) throws SQLException {
        ps.setLong(1, r.getOriginAirportId());
        ps.setLong(2, r.getDestinationAirportId());
        ps.setInt (3, r.getDistanceKm());
        ps.setInt (4, r.getTypicalDurationMin());
        ps.setLong(5, r.getId());
    }
}
