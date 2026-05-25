package com.solvd.airline.dao;

import com.solvd.airline.db.ConnectionPool;
import com.solvd.airline.entity.Identifiable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Template-method base for every concrete DAO.
 *
 * Owns the parts that NEVER vary between tables:
 *   - acquire / release of a {@link Connection} from the pool
 *   - try-with-resources around {@link PreparedStatement} and {@link ResultSet}
 *   - standardised CRUD SQL skeletons that delegate the table-specific
 *     pieces to the concrete subclass
 *   - translation of {@link SQLException} into {@link DaoException}
 *
 * Concrete DAOs (e.g. {@code AirportDao}) override only six small hooks —
 * the parts that DO vary per table:
 *   - {@link #tableName()}             "airports"
 *   - {@link #idColumn()}              "airport_id"
 *   - {@link #insertSql()}             column list + placeholders
 *   - {@link #updateSql()}             SET clause for non-id columns
 *   - {@link #mapRow(ResultSet)}       row -> entity
 *   - {@link #bindForSave(PreparedStatement, T)} entity -> placeholders
 *   - {@link #bindForUpdate(PreparedStatement, T)} entity -> placeholders + id
 *
 * Exam Q5 anchor: every query routes through {@link PreparedStatement}.
 * No string concatenation, no {@link Statement#execute(String)} on user
 * input. Every value sent to the server arrives in a {@code set*} call
 * and is bound by index — SQL injection is structurally impossible at
 * this layer.
 */
public abstract class AbstractDao<T extends Identifiable<Long>> implements Dao<T, Long> {

    protected final Logger log = LoggerFactory.getLogger(getClass());

    protected abstract String tableName();
    protected abstract String idColumn();
    protected abstract String insertSql();
    protected abstract String updateSql();

    protected abstract T   mapRow(ResultSet rs) throws SQLException;
    protected abstract void bindForSave(PreparedStatement ps, T entity)   throws SQLException;
    protected abstract void bindForUpdate(PreparedStatement ps, T entity) throws SQLException;

    @Override
    public Optional<T> findById(Long id) {
        String sql = "SELECT * FROM " + tableName() + " WHERE " + idColumn() + " = ?";
        try (Connection c = acquire();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? Optional.of(mapRow(rs)) : Optional.empty();
            }
        } catch (SQLException e) {
            throw new DaoException("findById failed for " + tableName() + " id=" + id, e);
        }
    }

    @Override
    public List<T> findAll() {
        String sql = "SELECT * FROM " + tableName() + " ORDER BY " + idColumn();
        List<T> out = new ArrayList<>();
        try (Connection c = acquire();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                out.add(mapRow(rs));
            }
            return out;
        } catch (SQLException e) {
            throw new DaoException("findAll failed for " + tableName(), e);
        }
    }

    @Override
    public T save(T entity) {
        try (Connection c = acquire();
             PreparedStatement ps = c.prepareStatement(insertSql(), Statement.RETURN_GENERATED_KEYS)) {
            bindForSave(ps, entity);
            ps.executeUpdate();
            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) {
                    entity.setId(keys.getLong(1));
                }
            }
            return entity;
        } catch (SQLException e) {
            throw new DaoException("save failed for " + tableName(), e);
        }
    }

    @Override
    public void update(T entity) {
        try (Connection c = acquire();
             PreparedStatement ps = c.prepareStatement(updateSql())) {
            bindForUpdate(ps, entity);
            int rows = ps.executeUpdate();
            if (rows == 0) {
                throw new DaoException("update affected 0 rows in " + tableName()
                        + " id=" + entity.getId());
            }
        } catch (SQLException e) {
            throw new DaoException("update failed for " + tableName() + " id=" + entity.getId(), e);
        }
    }

    @Override
    public boolean deleteById(Long id) {
        String sql = "DELETE FROM " + tableName() + " WHERE " + idColumn() + " = ?";
        try (Connection c = acquire();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setLong(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException("deleteById failed for " + tableName() + " id=" + id, e);
        }
    }

    /**
     * Borrow a connection from the pool. Wrapped so callers can use
     * try-with-resources — the returned connection's {@code close()} is
     * intercepted by the pool wrapper and rerouted to {@code release()}.
     */
    protected Connection acquire() throws SQLException {
        return ConnectionPool.getInstance().acquire();
    }
}
