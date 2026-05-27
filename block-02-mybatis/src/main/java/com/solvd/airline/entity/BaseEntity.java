package com.solvd.airline.entity;

import java.util.Objects;

/**
 * Same contract as the Lecture 03 project — MyBatis works with the
 * identical plain-Java entity classes. The framework requires only:
 *   - a no-arg constructor (so reflection can instantiate the entity per row),
 *   - getters and setters matching the bean naming convention.
 * No annotations, no superclass — that is the SQL-mapper niche.
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
