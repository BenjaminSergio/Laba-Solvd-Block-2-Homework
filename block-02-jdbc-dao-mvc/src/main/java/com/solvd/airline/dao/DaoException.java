package com.solvd.airline.dao;

/**
 * Vendor-neutral wrapper around {@link java.sql.SQLException}.
 *
 * The DAO layer translates checked, vendor-specific {@code SQLException}s
 * into this unchecked exception so service classes never need to declare
 * {@code throws SQLException}. That is the small but important boundary
 * that lets you swap MySQL for PostgreSQL — service code does not see the
 * vendor name in its checked-exception list.
 */
public class DaoException extends RuntimeException {

    public DaoException(String message, Throwable cause) {
        super(message, cause);
    }

    public DaoException(String message) {
        super(message);
    }
}
