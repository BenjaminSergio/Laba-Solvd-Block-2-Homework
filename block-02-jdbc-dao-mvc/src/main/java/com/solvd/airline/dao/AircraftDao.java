package com.solvd.airline.dao;

import com.solvd.airline.entity.Aircraft;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Stub — homework. See {@link AirportDao} for the reference implementation.
 *
 * Table {@code aircraft}: aircraft_id (PK), tail_number (UNIQUE), model_id (FK),
 * in_service (BOOLEAN), delivered_on (DATE, nullable).
 */
public class AircraftDao extends AbstractDao<Aircraft> {

    @Override protected String tableName() { return "aircraft"; }
    @Override protected String idColumn()  { return "aircraft_id"; }

    @Override protected String insertSql() {
        return """
               INSERT INTO aircraft (tail_number, model_id, in_service, delivered_on)
               VALUES (?, ?, ?, ?)
               """;
    }

    @Override protected String updateSql() {
        return """
               UPDATE aircraft
                  SET tail_number = ?, model_id = ?, in_service = ?, delivered_on = ?
                WHERE aircraft_id = ?
               """;
    }

    @Override
    protected Aircraft mapRow(ResultSet rs) throws SQLException {
        Aircraft a = new Aircraft();
        a.setId(rs.getLong("aircraft_id"));
        a.setTailNumber(rs.getString("tail_number"));
        a.setModelId(rs.getLong("model_id"));
        a.setInService(rs.getBoolean("in_service"));
        Date d = rs.getDate("delivered_on");
        a.setDeliveredOn(d == null ? null : d.toLocalDate());
        return a;
    }

    @Override
    protected void bindForSave(PreparedStatement ps, Aircraft a) throws SQLException {
        ps.setString (1, a.getTailNumber());
        ps.setLong   (2, a.getModelId());
        ps.setBoolean(3, a.isInService());
        if (a.getDeliveredOn() == null) ps.setNull(4, Types.DATE);
        else                            ps.setDate(4, Date.valueOf(a.getDeliveredOn()));
    }

    @Override
    protected void bindForUpdate(PreparedStatement ps, Aircraft a) throws SQLException {
        ps.setString (1, a.getTailNumber());
        ps.setLong   (2, a.getModelId());
        ps.setBoolean(3, a.isInService());
        if (a.getDeliveredOn() == null) ps.setNull(4, Types.DATE);
        else                            ps.setDate(4, Date.valueOf(a.getDeliveredOn()));
        ps.setLong(5, a.getId());
    }
}
