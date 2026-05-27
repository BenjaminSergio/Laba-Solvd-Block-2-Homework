package com.solvd.airline.dao;

import com.solvd.airline.entity.Ticket;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Stub — homework. Table {@code tickets}.
 *
 * Implement the standard CRUD methods AND a {@code saveInTx(Connection, Ticket)}
 * that participates in the outer transaction owned by {@code JdbcBookingService}
 * (see {@link BookingDao#saveInTx} for the pattern).
 */
public class TicketDao extends AbstractDao<Ticket> {

    @Override protected String tableName() { return "tickets"; }
    @Override protected String idColumn()  { return "ticket_id"; }

    @Override protected String insertSql() {
        return """
               INSERT INTO tickets
                   (ticket_number, booking_id, flight_id, fare_class_id, base_price, issued_at)
               VALUES (?, ?, ?, ?, ?, ?)
               """;
    }
    @Override protected String updateSql() {
        return """
               UPDATE tickets
                  SET ticket_number = ?, booking_id = ?, flight_id = ?,
                      fare_class_id = ?, base_price = ?, issued_at = ?
                WHERE ticket_id = ?
               """;
    }

    @Override protected Ticket mapRow(ResultSet rs) throws SQLException {
        Ticket t = new Ticket();
        t.setId(rs.getLong("ticket_id"));
        t.setTicketNumber(rs.getString("ticket_number"));
        t.setBookingId(rs.getLong("booking_id"));
        t.setFlightId(rs.getLong("flight_id"));
        t.setFareClassId(rs.getLong("fare_class_id"));
        t.setBasePrice(rs.getBigDecimal("base_price"));
        t.setIssuedAt(rs.getTimestamp("issued_at").toInstant());
        return t;
    }
    @Override protected void bindForSave(PreparedStatement ps, Ticket t) throws SQLException {
        ps.setString    (1, t.getTicketNumber());
        ps.setLong      (2, t.getBookingId());
        ps.setLong      (3, t.getFlightId());
        ps.setLong      (4, t.getFareClassId());
        ps.setBigDecimal(5, t.getBasePrice());
        ps.setTimestamp (6, Timestamp.from(t.getIssuedAt()));
    }
    @Override protected void bindForUpdate(PreparedStatement ps, Ticket t) throws SQLException {
        ps.setString    (1, t.getTicketNumber());
        ps.setLong      (2, t.getBookingId());
        ps.setLong      (3, t.getFlightId());
        ps.setLong      (4, t.getFareClassId());
        ps.setBigDecimal(5, t.getBasePrice());
        ps.setTimestamp (6, Timestamp.from(t.getIssuedAt()));
        ps.setLong(7, t.getId());
    }

    /** TODO: transactional save — see BookingDao.saveInTx. */
    public Ticket saveInTx(Connection c, Ticket t) throws SQLException {
        try (PreparedStatement ps = c.prepareStatement(
                insertSql(), Statement.RETURN_GENERATED_KEYS)) {
            bindForSave(ps, t);
            ps.executeUpdate();
            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) t.setId(keys.getLong(1));
            }
            return t;
        }
    }
}
