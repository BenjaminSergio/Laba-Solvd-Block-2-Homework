package com.solvd.airline.dao;

import com.solvd.airline.entity.Payment;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Stub — homework. Table {@code payments}.
 *
 * Implement {@code saveInTx(Connection, Payment)} so a service can record a
 * payment as part of the same transaction that confirms a booking.
 */
public class PaymentDao extends AbstractDao<Payment> {

    @Override protected String tableName() { return "payments"; }
    @Override protected String idColumn()  { return "payment_id"; }

    @Override protected String insertSql() { throw new UnsupportedOperationException("Homework."); }
    @Override protected String updateSql() { throw new UnsupportedOperationException("Homework."); }

    @Override protected Payment mapRow(ResultSet rs) throws SQLException {
        throw new UnsupportedOperationException("Homework.");
    }
    @Override protected void bindForSave(PreparedStatement ps, Payment p) throws SQLException {
        throw new UnsupportedOperationException("Homework.");
    }
    @Override protected void bindForUpdate(PreparedStatement ps, Payment p) throws SQLException {
        throw new UnsupportedOperationException("Homework.");
    }

    public Payment saveInTx(Connection c, Payment p) throws SQLException {
        throw new UnsupportedOperationException("Homework.");
    }
}
