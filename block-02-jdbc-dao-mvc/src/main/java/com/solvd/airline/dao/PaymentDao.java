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

    @Override protected String insertSql() {
        return """
               INSERT INTO payments (booking_id, amount, currency, method, paid_at)
               VALUES (?, ?, ?, ?, ?)
               """;}
    @Override protected String updateSql() {
        return """
               UPDATE payments
                  SET booking_id = ?, amount = ?, currency = ?, method = ?, paid_at = ?
                WHERE payment_id = ?
               """;
    }

    @Override protected Payment mapRow(ResultSet rs) throws SQLException {
        Payment p = new Payment();
        p.setId(rs.getLong("payment_id"));
        p.setBookingId(rs.getLong("booking_id"));
        p.setAmount(rs.getBigDecimal("amount"));
        p.setCurrency(rs.getString("currency"));
        p.setMethod(Payment.Method.valueOf(rs.getString("method")));
        p.setPaidAt(rs.getTimestamp("paid_at").toLocalDateTime());
        return p;
    }
    @Override protected void bindForSave(PreparedStatement ps, Payment p) throws SQLException {
        ps.setLong      (1, p.getBookingId());
        ps.setBigDecimal(2, p.getAmount());
        ps.setString    (3, p.getCurrency());
        ps.setString    (4, p.getMethod().name());
        ps.setTimestamp (5, Timestamp.valueOf(p.getPaidAt()));
    }
    @Override protected void bindForUpdate(PreparedStatement ps, Payment p) throws SQLException {
        ps.setLong      (1, p.getBookingId());
        ps.setBigDecimal(2, p.getAmount());
        ps.setString    (3, p.getCurrency());
        ps.setString    (4, p.getMethod().name());
        ps.setTimestamp (5, Timestamp.valueOf(p.getPaidAt()));
        ps.setLong(6, p.getId());
    }

    public Payment saveInTx(Connection c, Payment p) throws SQLException {
        try (PreparedStatement ps = c.prepareStatement(
                insertSql(), Statement.RETURN_GENERATED_KEYS)) {
            bindForSave(ps, p);
            ps.executeUpdate();
            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) p.setId(keys.getLong(1));
            }
            return p;
        }
    }
}
