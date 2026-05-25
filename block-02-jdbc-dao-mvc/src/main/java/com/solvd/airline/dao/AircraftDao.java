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
        // TODO: column list + placeholders
        throw new UnsupportedOperationException("Homework — see AirportDao for reference.");
    }

    @Override protected String updateSql() {
        // TODO
        throw new UnsupportedOperationException("Homework — see AirportDao for reference.");
    }

    @Override
    protected Aircraft mapRow(ResultSet rs) throws SQLException {
        // TODO: build Aircraft from rs columns. Use rs.getDate() + toLocalDate()
        //       and remember to handle a nullable DATE with rs.wasNull().
        throw new UnsupportedOperationException("Homework.");
    }

    @Override
    protected void bindForSave(PreparedStatement ps, Aircraft a) throws SQLException {
        // TODO
        throw new UnsupportedOperationException("Homework.");
    }

    @Override
    protected void bindForUpdate(PreparedStatement ps, Aircraft a) throws SQLException {
        // TODO
        throw new UnsupportedOperationException("Homework.");
    }
}
