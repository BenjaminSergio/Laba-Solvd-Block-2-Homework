package com.solvd.airline.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Single-row mapper. The same shape Spring's {@code RowMapper<T>} exposes —
 * because the production framework arrived at this exact contract for the
 * same reason: a {@link ResultSet} cursor positioned on a row is the only
 * input a row-to-entity mapper needs.
 *
 * Exam Q6 anchor: a {@link ResultSet} is a database cursor, not a Java
 * collection. The mapper is called once per row, while the cursor is on
 * that row — no rewinding, no random access in the default forward-only
 * mode.
 */
@FunctionalInterface
public interface RowMapper<T> {

    T map(ResultSet rs) throws SQLException;
}
