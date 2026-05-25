package com.solvd.airline.entity;

import java.util.Objects;

/**
 * Default base for every entity in the {@code airline_booking} schema whose
 * primary key is a {@code BIGINT} surrogate ({@link Long}). Owns the {@code id}
 * field and an identity-based {@code equals}/{@code hashCode} pair: two
 * entities are equal iff they share the same non-null id and same concrete
 * class. Entities with a {@code null} id (newly constructed, not yet saved)
 * are equal only by reference.
 */
public abstract class BaseEntity implements Identifiable<Long> {

    protected Long id;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (other == null || getClass() != other.getClass()) return false;
        BaseEntity that = (BaseEntity) other;
        return id != null && id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getClass(), id);
    }
}
