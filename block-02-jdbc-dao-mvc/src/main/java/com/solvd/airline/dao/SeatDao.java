package com.solvd.airline.dao;

import com.solvd.airline.entity.Seat;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/** Stub — homework. Table {@code seats}. */
public class SeatDao extends AbstractDao<Seat> {

    @Override protected String tableName() { return "seats"; }
    @Override protected String idColumn()  { return "seat_id"; }

    @Override protected String insertSql() { throw new UnsupportedOperationException("Homework."); }
    @Override protected String updateSql() { throw new UnsupportedOperationException("Homework."); }

    @Override protected Seat mapRow(ResultSet rs) throws SQLException {
        throw new UnsupportedOperationException("Homework.");
    }
    @Override protected void bindForSave(PreparedStatement ps, Seat s) throws SQLException {
        throw new UnsupportedOperationException("Homework.");
    }
    @Override protected void bindForUpdate(PreparedStatement ps, Seat s) throws SQLException {
        throw new UnsupportedOperationException("Homework.");
    }
}
