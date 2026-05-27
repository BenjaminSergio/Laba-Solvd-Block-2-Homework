package com.solvd.airline.db;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Connection pool — singleton.
 *
 * The implementation lives in the homework you wrote in Block 01 / Lecture 12
 * (Threads · ConnectionPool · Future · Multithreading). Paste the body of your
 * pool here. The contract is exactly the four methods below; the rest of this
 * project depends only on this surface.
 *
 * Required behaviour:
 *   - Lazy singleton via the Holder idiom (no double-checked locking).
 *   - Bounded — maximum {@code pool.size} concurrent borrowed connections,
 *     read from {@code db.properties}.
 *   - {@link #acquire()} blocks (with timeout) until a connection is free.
 *   - {@link #release(Connection)} returns a connection to the pool — it does
 *     NOT call {@code Connection.close()}; the pooled wrapper intercepts close.
 *   - {@link #shutdown()} closes every underlying connection.
 *
 * Exam Q1 anchor: this is the production wrapper around
 * {@code DriverManager.getConnection(url, user, password)} — the JDBC entry
 * point that, given a URL whose vendor prefix is {@code jdbc:mysql:}, finds
 * the registered MySQL driver via {@link java.util.ServiceLoader} and asks it
 * to open a TCP+TLS+auth handshake.
 */
public final class ConnectionPool {

    private static final String PROPERTIES_RESOURCE = "/db.properties";

    private final String url;
    private final String user;
    private final String password;
    private final int    size;
    private final long   acquireTimeoutMs;

    private final BlockingQueue<Connection> pool;
    private volatile boolean shutDown = false;

    private ConnectionPool() {
        Properties p = loadProperties();
        this.url              = p.getProperty("db.url");
        this.user             = p.getProperty("db.user");
        this.password         = p.getProperty("db.password");
        this.size             = Integer.parseInt(p.getProperty("pool.size", "10"));
        this.acquireTimeoutMs = Long.parseLong(p.getProperty("pool.acquire.timeout.ms", "2000"));
        this.pool             = new ArrayBlockingQueue<>(size);
        try {
            for (int i = 0; i < size; i++) {
                Connection c = DriverManager.getConnection(url, user, password);
                c.setAutoCommit(true);
                c.setTransactionIsolation(isolationLevel);
                pool.offer(c);
            }
            log.info("ConnectionPool initialised — size={} url={}", size, url);
        } catch (SQLException e) {
            throw new IllegalStateException("Cannot pre-fill ConnectionPool", e);
        }
    }

    private static final class Holder {
        static final ConnectionPool INSTANCE = new ConnectionPool();
    }

    public static ConnectionPool getInstance() {
        return Holder.INSTANCE;
    }

    /** Borrow a connection from the pool. Blocks up to {@code pool.acquire.timeout.ms}. */
    public Connection acquire() throws SQLException {
        if (shutDown) {
            throw new SQLException("ConnectionPool is shut down");
        }
        try {
            Connection real = pool.poll(acquireTimeoutMs, TimeUnit.MILLISECONDS);
            if (real == null) {
                throw new SQLException("Pool exhausted — no connection within "
                        + acquireTimeoutMs + " ms");
            }
            return wrap(real);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new SQLException("Interrupted while waiting for a connection", e);
        }
    }

    /** Return a connection to the pool. Resets autoCommit + isolation before returning. */
    public void release(Connection connection) {
        if (connection == null) return;
        if (shutDown) {
            closeQuietly(connection);
            return;
        }
        try {
            if (!connection.getAutoCommit()) {
                connection.setAutoCommit(true);
            }
            connection.setTransactionIsolation(isolationLevel);
            pool.offer(connection);
        } catch (SQLException e) {
            log.warn("Resetting connection state failed — discarding", e);
            closeQuietly(connection);
        }
    }

    /** Close every underlying connection — call on JVM shutdown. */
    public void shutdown() {
        if (shutDown) return;
        shutDown = true;
        List<Connection> drained = new ArrayList<>();
        pool.drainTo(drained);
        for (Connection c : drained) {
            closeQuietly(c);
        }
        log.info("ConnectionPool shut down — closed {} connections", drained.size());

    }

    public int size()             { return size; }
    public String jdbcUrl()       { return url;  }

    private static Properties loadProperties() {
        Properties p = new Properties();
        try (InputStream in = ConnectionPool.class.getResourceAsStream(PROPERTIES_RESOURCE)) {
            if (in == null) {
                throw new IllegalStateException("Missing classpath resource: " + PROPERTIES_RESOURCE);
            }
            p.load(in);
            return p;
        } catch (IOException e) {
            throw new IllegalStateException("Cannot read " + PROPERTIES_RESOURCE, e);
        }
    }
}
