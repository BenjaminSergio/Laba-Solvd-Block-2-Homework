package com.solvd.airline.dao;

import com.solvd.airline.entity.Route;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/** Stub — homework. Table {@code routes}. Two FKs to {@code airports}. */
public class RouteDao extends AbstractDao<Route> {

    @Override protected String tableName() { return "routes"; }
    @Override protected String idColumn()  { return "route_id"; }

    @Override protected String insertSql() { throw new UnsupportedOperationException("Homework."); }
    @Override protected String updateSql() { throw new UnsupportedOperationException("Homework."); }

    @Override protected Route mapRow(ResultSet rs) throws SQLException {
        throw new UnsupportedOperationException("Homework.");
    }
    @Override protected void bindForSave(PreparedStatement ps, Route r) throws SQLException {
        throw new UnsupportedOperationException("Homework.");
    }
    @Override protected void bindForUpdate(PreparedStatement ps, Route r) throws SQLException {
        throw new UnsupportedOperationException("Homework.");
    }
}
