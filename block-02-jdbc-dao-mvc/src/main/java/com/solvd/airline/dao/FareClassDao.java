package com.solvd.airline.dao;

import com.solvd.airline.entity.FareClass;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/** Stub — homework. Table {@code fare_classes}. */
public class FareClassDao extends AbstractDao<FareClass> {

    @Override protected String tableName() { return "fare_classes"; }
    @Override protected String idColumn()  { return "fare_class_id"; }

    @Override protected String insertSql() {
        return """
               INSERT INTO fare_classes (code, name, price_multiplier, refundable)
               VALUES (?, ?, ?, ?)
               """;
    }
    @Override protected String updateSql() {
        return """
               UPDATE fare_classes
                  SET code = ?, name = ?, price_multiplier = ?, refundable = ?
                WHERE fare_class_id = ?
               """;
    }

    @Override protected FareClass mapRow(ResultSet rs) throws SQLException {
        FareClass f = new FareClass();
        f.setId(rs.getLong("fare_class_id"));
        f.setCode(rs.getString("code"));
        f.setName(rs.getString("name"));
        f.setPriceMultiplier(rs.getBigDecimal("price_multiplier"));
        f.setRefundable(rs.getBoolean("refundable"));
        return f;
    }
    @Override protected void bindForSave(PreparedStatement ps, FareClass f) throws SQLException {
        ps.setString    (1, f.getCode());
        ps.setString    (2, f.getName());
        ps.setBigDecimal(3, f.getPriceMultiplier());
        ps.setBoolean   (4, f.isRefundable());
    }
    @Override protected void bindForUpdate(PreparedStatement ps, FareClass f) throws SQLException {
        ps.setString    (1, f.getCode());
        ps.setString    (2, f.getName());
        ps.setBigDecimal(3, f.getPriceMultiplier());
        ps.setBoolean   (4, f.isRefundable());
        ps.setLong(5, f.getId());
    }
}
