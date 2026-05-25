package com.solvd.airline.dao;

import com.solvd.airline.entity.AircraftModel;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/** Stub — homework. Table {@code aircraft_models}. */
public class AircraftModelDao extends AbstractDao<AircraftModel> {

    @Override protected String tableName() { return "aircraft_models"; }
    @Override protected String idColumn()  { return "model_id"; }

    @Override protected String insertSql() { throw new UnsupportedOperationException("Homework."); }
    @Override protected String updateSql() { throw new UnsupportedOperationException("Homework."); }

    @Override protected AircraftModel mapRow(ResultSet rs) throws SQLException {
        throw new UnsupportedOperationException("Homework.");
    }
    @Override protected void bindForSave(PreparedStatement ps, AircraftModel m) throws SQLException {
        throw new UnsupportedOperationException("Homework.");
    }
    @Override protected void bindForUpdate(PreparedStatement ps, AircraftModel m) throws SQLException {
        throw new UnsupportedOperationException("Homework.");
    }
}
