package com.solvd.airline.dao;

import com.solvd.airline.db.ConnectionPool;
import com.solvd.airline.entity.Passenger;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class PassengerDao extends AbstractDao<Passenger> {

    @Override protected String tableName() { return "passengers"; }
    @Override protected String idColumn()  { return "passenger_id"; }

    @Override
    protected String insertSql() {
        return """
               INSERT INTO passengers (first_name, last_name, email, phone, date_of_birth)
               VALUES (?, ?, ?, ?, ?)
               """;
    }

    @Override
    protected String updateSql() {
        return """
               UPDATE passengers
                  SET first_name = ?, last_name = ?, email = ?, phone = ?, date_of_birth = ?
                WHERE passenger_id = ?
               """;
    }

    @Override
    protected Passenger mapRow(ResultSet rs) throws SQLException {
        Passenger p = new Passenger();
        p.setId(rs.getLong("passenger_id"));
        p.setFirstName(rs.getString("first_name"));
        p.setLastName(rs.getString("last_name"));
        p.setEmail(rs.getString("email"));
        p.setPhone(rs.getString("phone"));                 // null-safe in JDBC
        p.setDateOfBirth(rs.getDate("date_of_birth").toLocalDate());
        p.setCreatedAt(rs.getTimestamp("created_at").toInstant());
        return p;
    }

    @Override
    protected void bindForSave(PreparedStatement ps, Passenger p) throws SQLException {
        ps.setString(1, p.getFirstName());
        ps.setString(2, p.getLastName());
        ps.setString(3, p.getEmail());
        ps.setString(4, p.getPhone());                     // setString(null) is the right thing
        ps.setDate  (5, Date.valueOf(p.getDateOfBirth()));
    }

    @Override
    protected void bindForUpdate(PreparedStatement ps, Passenger p) throws SQLException {
        bindForSave(ps, p);
        ps.setLong(6, p.getId());
    }

    /** Lookup by email — UNIQUE in the schema. */
    public Optional<Passenger> findByEmail(String email) {
        String sql = "SELECT * FROM passengers WHERE email = ?";
        try (Connection c = ConnectionPool.getInstance().acquire();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? Optional.of(mapRow(rs)) : Optional.empty();
            }
        } catch (SQLException e) {
            throw new DaoException("findByEmail failed for email=" + email, e);
        }
    }
}
