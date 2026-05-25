package com.solvd.airline.dao;

import com.solvd.airline.entity.SeatAssignment;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/** Stub — homework. Table {@code seat_assignments}. True 1 : 1 with tickets. */
public class SeatAssignmentDao extends AbstractDao<SeatAssignment> {

    @Override protected String tableName() { return "seat_assignments"; }
    @Override protected String idColumn()  { return "assignment_id"; }

    @Override protected String insertSql() { throw new UnsupportedOperationException("Homework."); }
    @Override protected String updateSql() { throw new UnsupportedOperationException("Homework."); }

    @Override protected SeatAssignment mapRow(ResultSet rs) throws SQLException {
        throw new UnsupportedOperationException("Homework.");
    }
    @Override protected void bindForSave(PreparedStatement ps, SeatAssignment s) throws SQLException {
        throw new UnsupportedOperationException("Homework.");
    }
    @Override protected void bindForUpdate(PreparedStatement ps, SeatAssignment s) throws SQLException {
        throw new UnsupportedOperationException("Homework.");
    }
}
