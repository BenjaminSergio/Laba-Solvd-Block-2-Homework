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

    @Override protected String insertSql() { throw new UnsupportedOperationException("Homework."); }
    @Override protected String updateSql() { throw new UnsupportedOperationException("Homework."); }

    @Override protected Ticket mapRow(ResultSet rs) throws SQLException {
        throw new UnsupportedOperationException("Homework.");
    }
    @Override protected void bindForSave(PreparedStatement ps, Ticket t) throws SQLException {
        throw new UnsupportedOperationException("Homework.");
    }
    @Override protected void bindForUpdate(PreparedStatement ps, Ticket t) throws SQLException {
        throw new UnsupportedOperationException("Homework.");
    }

    /** TODO: transactional save — see BookingDao.saveInTx. */
    public Ticket saveInTx(Connection c, Ticket t) throws SQLException {
        throw new UnsupportedOperationException("Homework.");
    }
}
