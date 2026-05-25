package com.solvd.airline.dao;

import com.solvd.airline.entity.FareClass;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/** Stub — homework. Table {@code fare_classes}. */
public class FareClassDao extends AbstractDao<FareClass> {

    @Override protected String tableName() { return "fare_classes"; }
    @Override protected String idColumn()  { return "fare_class_id"; }

    @Override protected String insertSql() { throw new UnsupportedOperationException("Homework."); }
    @Override protected String updateSql() { throw new UnsupportedOperationException("Homework."); }

    @Override protected FareClass mapRow(ResultSet rs) throws SQLException {
        throw new UnsupportedOperationException("Homework.");
    }
    @Override protected void bindForSave(PreparedStatement ps, FareClass f) throws SQLException {
        throw new UnsupportedOperationException("Homework.");
    }
    @Override protected void bindForUpdate(PreparedStatement ps, FareClass f) throws SQLException {
        throw new UnsupportedOperationException("Homework.");
    }
}
