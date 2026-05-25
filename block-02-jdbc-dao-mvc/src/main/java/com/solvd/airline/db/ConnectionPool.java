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

    // TODO (homework): paste your L12 BlockingQueue<Connection> + initialised flag here.

    private ConnectionPool() {
        Properties p = loadProperties();
        this.url              = p.getProperty("db.url");
        this.user             = p.getProperty("db.user");
        this.password         = p.getProperty("db.password");
        this.size             = Integer.parseInt(p.getProperty("pool.size", "10"));
        this.acquireTimeoutMs = Long.parseLong(p.getProperty("pool.acquire.timeout.ms", "2000"));
        // TODO (homework): pre-fill the queue with `size` open connections.
    }

    private static final class Holder {
        static final ConnectionPool INSTANCE = new ConnectionPool();
    }

    public static ConnectionPool getInstance() {
        return Holder.INSTANCE;
    }

    /** Borrow a connection from the pool. Blocks up to {@code pool.acquire.timeout.ms}. */
    public Connection acquire() throws SQLException {
        // TODO (homework): poll the BlockingQueue with the timeout.
        throw new UnsupportedOperationException("Paste your L12 implementation here.");
    }

    /** Return a connection to the pool. Resets autoCommit + isolation before returning. */
    public void release(Connection connection) {
        // TODO (homework): reset state, offer the connection back to the queue.
        throw new UnsupportedOperationException("Paste your L12 implementation here.");
    }

    /** Close every underlying connection — call on JVM shutdown. */
    public void shutdown() {
        // TODO (homework): drain the queue + close().
        throw new UnsupportedOperationException("Paste your L12 implementation here.");
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
