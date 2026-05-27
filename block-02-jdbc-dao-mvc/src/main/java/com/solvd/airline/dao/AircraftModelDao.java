package com.solvd.airline.dao;

import com.solvd.airline.entity.AircraftModel;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/** Stub — homework. Table {@code aircraft_models}. */
public class AircraftModelDao extends AbstractDao<AircraftModel> {

    @Override protected String tableName() { return "aircraft_models"; }
    @Override protected String idColumn()  { return "model_id"; }

    @Override protected String insertSql() {
        return """
               INSERT INTO aircraft_models (manufacturer, model_name, capacity, range_km)
               VALUES (?, ?, ?, ?)
               """;
    }
    @Override protected String updateSql() {
        return """
               UPDATE aircraft_models
                  SET manufacturer = ?, model_name = ?, capacity = ?, range_km = ?
                WHERE model_id = ?
               """;
    }

    @Override protected AircraftModel mapRow(ResultSet rs) throws SQLException {
        AircraftModel m = new AircraftModel();
        m.setId(rs.getLong("model_id"));
        m.setManufacturer(rs.getString("manufacturer"));
        m.setModelName(rs.getString("model_name"));
        m.setCapacity(rs.getShort("capacity"));
        m.setRangeKm(rs.getInt("range_km"));
        return m;
    }
    @Override protected void bindForSave(PreparedStatement ps, AircraftModel m) throws SQLException {
        ps.setString(1, m.getManufacturer());
        ps.setString(2, m.getModelName());
        ps.setShort (3, m.getCapacity());
        ps.setInt   (4, m.getRangeKm());
    }
    @Override protected void bindForUpdate(PreparedStatement ps, AircraftModel m) throws SQLException {
        ps.setString(1, m.getManufacturer());
        ps.setString(2, m.getModelName());
        ps.setShort (3, m.getCapacity());
        ps.setInt   (4, m.getRangeKm());
        ps.setLong(5, m.getId());
    }
}
