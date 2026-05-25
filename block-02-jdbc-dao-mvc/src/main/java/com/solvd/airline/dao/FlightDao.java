package com.solvd.airline.dao;

import com.solvd.airline.db.ConnectionPool;
import com.solvd.airline.entity.Flight;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class FlightDao extends AbstractDao<Flight> {

    @Override protected String tableName() { return "flights"; }
    @Override protected String idColumn()  { return "flight_id"; }

    @Override
    protected String insertSql() {
        return """
               INSERT INTO flights
                   (flight_number, route_id, aircraft_id, scheduled_dep, scheduled_arr, status)
               VALUES (?, ?, ?, ?, ?, ?)
               """;
    }

    @Override
    protected String updateSql() {
        return """
               UPDATE flights
                  SET flight_number = ?, route_id = ?, aircraft_id = ?,
                      scheduled_dep = ?, scheduled_arr = ?, status = ?
                WHERE flight_id = ?
               """;
    }

    @Override
    protected Flight mapRow(ResultSet rs) throws SQLException {
        Flight f = new Flight();
        f.setId(rs.getLong("flight_id"));
        f.setFlightNumber(rs.getString("flight_number"));
        f.setRouteId(rs.getLong("route_id"));
        long aircraftId = rs.getLong("aircraft_id");
        f.setAircraftId(rs.wasNull() ? null : aircraftId);     // nullable FK
        f.setScheduledDep(rs.getTimestamp("scheduled_dep").toLocalDateTime());
        f.setScheduledArr(rs.getTimestamp("scheduled_arr").toLocalDateTime());
        f.setStatus(Flight.Status.valueOf(rs.getString("status")));
        return f;
    }

    @Override
    protected void bindForSave(PreparedStatement ps, Flight f) throws SQLException {
        ps.setString(1, f.getFlightNumber());
        ps.setLong  (2, f.getRouteId());
        if (f.getAircraftId() == null) ps.setNull(3, Types.BIGINT);
        else                            ps.setLong(3, f.getAircraftId());
        ps.setTimestamp(4, Timestamp.valueOf(f.getScheduledDep()));
        ps.setTimestamp(5, Timestamp.valueOf(f.getScheduledArr()));
        ps.setString   (6, f.getStatus().name());
    }

    @Override
    protected void bindForUpdate(PreparedStatement ps, Flight f) throws SQLException {
        bindForSave(ps, f);
        ps.setLong(7, f.getId());
    }

    /** Domain finder: every flight on a given route on a given calendar date. */
    public List<Flight> findByRouteOnDate(long routeId, LocalDate date) {
        String sql = """
                     SELECT * FROM flights
                      WHERE route_id = ?
                        AND scheduled_dep >= ?
                        AND scheduled_dep <  ?
                      ORDER BY scheduled_dep
                     """;
        List<Flight> out = new ArrayList<>();
        try (Connection c = ConnectionPool.getInstance().acquire();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setLong     (1, routeId);
            ps.setTimestamp(2, Timestamp.valueOf(date.atStartOfDay()));
            ps.setTimestamp(3, Timestamp.valueOf(date.plusDays(1).atStartOfDay().minusNanos(1)
                                                    .withSecond(0).truncatedTo(java.time.temporal.ChronoUnit.MINUTES)
                                                    .with(LocalTime.MAX)));
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) out.add(mapRow(rs));
            }
            return out;
        } catch (SQLException e) {
            throw new DaoException("findByRouteOnDate failed: route=" + routeId + " date=" + date, e);
        }
    }
}
