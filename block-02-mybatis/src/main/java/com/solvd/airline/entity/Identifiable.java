package com.solvd.airline.entity;

/**
 * Same contract as the Lecture 03 project. Anything the Mapper layer can
 * persist must be identifiable by a primary key of type {@code K}. Most
 * entities in this schema use {@link Long} (BIGINT surrogate keys).
 */
public interface Identifiable<K> {

    K getId();

    void setId(K id);
}
