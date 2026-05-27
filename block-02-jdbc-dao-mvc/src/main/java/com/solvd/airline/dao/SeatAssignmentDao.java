package com.solvd.airline.dao;

import com.solvd.airline.entity.SeatAssignment;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/** Stub — homework. Table {@code seat_assignments}. True 1 : 1 with tickets. */
public class SeatAssignmentDao extends AbstractDao<SeatAssignment> {

    @Override protected String tableName() { return "seat_assignments"; }
    @Override protected String idColumn()  { return "assignment_id"; }

    @Override protected String insertSql() {
        return """
               INSERT INTO seat_assignments (ticket_id, seat_id, assigned_at)
               VALUES (?, ?, ?)
               """;
    }
    @Override protected String updateSql() {
        return """
               UPDATE seat_assignments
                  SET ticket_id = ?, seat_id = ?, assigned_at = ?
                WHERE assignment_id = ?
               """;
    }

    @Override protected SeatAssignment mapRow(ResultSet rs) throws SQLException {
        SeatAssignment s = new SeatAssignment();
        s.setId(rs.getLong("assignment_id"));
        s.setTicketId(rs.getLong("ticket_id"));
        s.setSeatId(rs.getLong("seat_id"));
        s.setAssignedAt(rs.getTimestamp("assigned_at").toInstant());
        return s;
    }
    @Override protected void bindForSave(PreparedStatement ps, SeatAssignment s) throws SQLException {
        ps.setLong     (1, s.getTicketId());
        ps.setLong     (2, s.getSeatId());
        ps.setTimestamp(3, Timestamp.from(s.getAssignedAt()));
    }
    @Override protected void bindForUpdate(PreparedStatement ps, SeatAssignment s) throws SQLException {
        ps.setLong     (1, s.getTicketId());
        ps.setLong     (2, s.getSeatId());
        ps.setTimestamp(3, Timestamp.from(s.getAssignedAt()));
        ps.setLong(4, s.getId());
    }
}
