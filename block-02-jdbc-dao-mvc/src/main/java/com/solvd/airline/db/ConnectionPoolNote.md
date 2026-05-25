# ConnectionPool — paste your Block 01 / Lecture 12 implementation here

The pool is the homework you already wrote in Block 01 / Lecture 12
(Threads · ConnectionPool · Future · Multithreading). The skeleton in
`ConnectionPool.java` defines the same contract — Holder-idiom singleton,
bounded `BlockingQueue<Connection>`, lazy initialisation, blocking
`acquire()` with timeout, `release()` that does not close the underlying
connection.

## What to bring over

1. The `BlockingQueue<Connection>` field, sized to `pool.size`.
2. The pre-fill loop in the private constructor — open exactly `size`
   connections via `DriverManager.getConnection(url, user, password)`
   and `offer()` them onto the queue.
3. The `acquire()` body: `queue.poll(acquireTimeoutMs, TimeUnit.MILLISECONDS)`.
   Throw `SQLException("Pool exhausted")` if the poll returns `null`.
4. The `release(Connection)` body: reset the connection's state
   (`setAutoCommit(true)`, `setTransactionIsolation(...)` from
   `db.isolation`), then `queue.offer(conn)`. **Do not call** `conn.close()`.
5. The `shutdown()` body: drain the queue and call `close()` on each.

## Why the wrapper layer

The DAO classes treat the pool as an `acquire`/`release` pair around a
plain `java.sql.Connection`. They never touch `DriverManager`, never see
`pool.size`, never know whether the underlying database is MySQL or
PostgreSQL. That is the whole point — the JDBC URL stays in
`db.properties`, the driver gets loaded by the JDBC 4 ServiceLoader, and
the pool is the only class in the project that imports
`java.sql.DriverManager`.

## Production version

The production-grade equivalent is **HikariCP**. Same pattern (bounded
queue + acquire/release semantics), refined by a decade of operational
experience: connection liveness probing, leak detection, per-connection
metrics, configurable validation queries. When a real codebase reaches
the point where a hand-rolled pool is not enough, swap to HikariCP — the
DAO classes do not change because the contract is identical.
