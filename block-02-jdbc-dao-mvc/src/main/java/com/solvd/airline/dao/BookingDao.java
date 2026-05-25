package com.solvd.airline.dao;

import com.solvd.airline.db.ConnectionPool;
import com.solvd.airline.entity.Booking;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BookingDao extends AbstractDao<Booking> {

    @Override protected String tableName() { return "bookings"; }
    @Override protected String idColumn()  { return "booking_id"; }

    @Override
    protected String insertSql() {
        return """
               INSERT INTO bookings (booking_reference, passenger_id, status)
               VALUES (?, ?, ?)
               """;
    }

    @Override
    protected String updateSql() {
        return """
               UPDATE bookings
                  SET booking_reference = ?, passenger_id = ?, status = ?
                WHERE booking_id = ?
               """;
    }

    @Override
    protected Booking mapRow(ResultSet rs) throws SQLException {
        Booking b = new Booking();
        b.setId(rs.getLong("booking_id"));
        b.setBookingReference(rs.getString("booking_reference"));
        b.setPassengerId(rs.getLong("passenger_id"));
        b.setStatus(Booking.Status.valueOf(rs.getString("status")));
        b.setBookedAt(rs.getTimestamp("booked_at").toInstant());
        return b;
    }

    @Override
    protected void bindForSave(PreparedStatement ps, Booking b) throws SQLException {
        ps.setString(1, b.getBookingReference());
        ps.setLong  (2, b.getPassengerId());
        ps.setString(3, b.getStatus().name());
    }

    @Override
    protected void bindForUpdate(PreparedStatement ps, Booking b) throws SQLException {
        bindForSave(ps, b);
        ps.setLong(4, b.getId());
    }

    /**
     * Transactional variant of {@link #save(Booking)} — the calling service
     * supplies the {@link Connection} so the insert participates in an
     * outer transaction. Demonstrates Exam Q4: the unit of work spans
     * multiple DAOs and the transaction boundary lives ABOVE them.
     */
    public Booking saveInTx(Connection c, Booking b) throws SQLException {
        try (PreparedStatement ps = c.prepareStatement(
                insertSql(), java.sql.Statement.RETURN_GENERATED_KEYS)) {
            bindForSave(ps, b);
            ps.executeUpdate();
            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) b.setId(keys.getLong(1));
            }
            return b;
        }
    }

    /** All bookings made by one passenger, newest first. */
    public List<Booking> findByPassenger(long passengerId) {
        String sql = "SELECT * FROM bookings WHERE passenger_id = ? ORDER BY booked_at DESC";
        List<Booking> out = new ArrayList<>();
        try (Connection c = ConnectionPool.getInstance().acquire();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setLong(1, passengerId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) out.add(mapRow(rs));
            }
            return out;
        } catch (SQLException e) {
            throw new DaoException("findByPassenger failed: passengerId=" + passengerId, e);
        }
    }

    public Optional<Booking> findByReference(String reference) {
        String sql = "SELECT * FROM bookings WHERE booking_reference = ?";
        try (Connection c = ConnectionPool.getInstance().acquire();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, reference);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? Optional.of(mapRow(rs)) : Optional.empty();
            }
        } catch (SQLException e) {
            throw new DaoException("findByReference failed: " + reference, e);
        }
    }
}
