package com.solvd.airline.dao;

import com.solvd.airline.entity.Seat;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/** Stub — homework. Table {@code seats}. */
public class SeatDao extends AbstractDao<Seat> {

    @Override protected String tableName() { return "seats"; }
    @Override protected String idColumn()  { return "seat_id"; }

    @Override protected String insertSql() {
        return """
               INSERT INTO seats (aircraft_id, fare_class_id, seat_label, is_window, is_exit_row)
               VALUES (?, ?, ?, ?, ?)
               """;
    }
    @Override protected String updateSql() {
        return """
               UPDATE seats
                  SET aircraft_id = ?, fare_class_id = ?, seat_label = ?,
                      is_window = ?, is_exit_row = ?
                WHERE seat_id = ?
               """;
    }

    @Override protected Seat mapRow(ResultSet rs) throws SQLException {
        Seat s = new Seat();
        s.setId(rs.getLong("seat_id"));
        s.setAircraftId(rs.getLong("aircraft_id"));
        s.setFareClassId(rs.getLong("fare_class_id"));
        s.setSeatLabel(rs.getString("seat_label"));
        s.setWindow(rs.getBoolean("is_window"));
        s.setExitRow(rs.getBoolean("is_exit_row"));
        return s;
    }
    @Override protected void bindForSave(PreparedStatement ps, Seat s) throws SQLException {
        ps.setLong   (1, s.getAircraftId());
        ps.setLong   (2, s.getFareClassId());
        ps.setString (3, s.getSeatLabel());
        ps.setBoolean(4, s.isWindow());
        ps.setBoolean(5, s.isExitRow());
    }
    @Override protected void bindForUpdate(PreparedStatement ps, Seat s) throws SQLException {
        ps.setLong   (1, s.getAircraftId());
        ps.setLong   (2, s.getFareClassId());
        ps.setString (3, s.getSeatLabel());
        ps.setBoolean(4, s.isWindow());
        ps.setBoolean(5, s.isExitRow());
        ps.setLong(6, s.getId());
    }
}
