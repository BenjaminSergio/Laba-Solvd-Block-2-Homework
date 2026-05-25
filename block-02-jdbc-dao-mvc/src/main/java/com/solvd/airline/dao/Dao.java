package com.solvd.airline.dao;

import com.solvd.airline.entity.Identifiable;

import java.util.List;
import java.util.Optional;

/**
 * Generic DAO contract — five CRUD methods, one signature.
 *
 * Type parameters:
 *   T — the entity type. Must be {@link Identifiable} so {@link #save} can
 *       return its generated key.
 *   K — the primary-key type. {@code Long} for every entity in the
 *       airline_booking schema, but the parameter is exposed so a future
 *       string-keyed table still fits.
 *
 * Exam Q2 anchor: this interface is the seam that makes the DAO pattern
 * work. Business code depends on {@code Dao<Booking, Long>}, not on
 * {@code BookingDao}. Swap the implementation (raw JDBC -> Spring
 * JdbcTemplate -> JPA repository) without touching a service class.
 */
public interface Dao<T extends Identifiable<K>, K> {

    Optional<T> findById(K id);

    List<T> findAll();

    /** Inserts and returns the entity with its generated key populated. */
    T save(T entity);

    void update(T entity);

    boolean deleteById(K id);
}
