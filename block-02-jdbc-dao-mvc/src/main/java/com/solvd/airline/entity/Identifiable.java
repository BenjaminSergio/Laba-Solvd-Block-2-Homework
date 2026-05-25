package com.solvd.airline.entity;

/**
 * Anything the DAO layer can persist must be identifiable by a primary key
 * of type {@code K}. Most entities in this schema use {@link Long} (BIGINT
 * surrogate keys), but the type parameter is exposed so a future entity
 * keyed by {@code String} or a composite class still fits the contract.
 */
public interface Identifiable<K> {

    K getId();

    void setId(K id);
}
