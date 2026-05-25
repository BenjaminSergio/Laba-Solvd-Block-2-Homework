# Lecture 03 — JDBC · DAO · MVC · Homework starter

Starter project for the homework that ships with **Section 02 / Lecture 03 — JDBC · DAO classes · MVC**.
Layered architecture: `entity` → `dao` → `service` → `app`. JDBC is the only persistence dependency. The connection pool is the one **you built in Block 01 / Lecture 12** (Holder idiom + `BlockingQueue<Connection>`); paste your implementation into `db/ConnectionPool.java`.

## Homework

1. **Read** the four articles linked from the lecture page — DAO pattern (Wikipedia ×2 + tutorialspoint), JDBC official Oracle tutorial, JDBC w3schools.
2. **Build the entity hierarchy** for the `airline_booking` schema. Four entities are shipped in full (`Airport`, `Flight`, `Passenger`, `Booking`). The remaining eight are stubs — fill in the fields, getters, setters, and `equals`/`hashCode` based on the columns documented in `schema.sql`.
3. **Implement the DAO classes.** Eight DAOs are stubbed (`AircraftDao`, `AircraftModelDao`, `RouteDao`, `TicketDao`, `SeatAssignmentDao`, `FareClassDao`, `SeatDao`, `PaymentDao`). Use `AirportDao` / `FlightDao` / `PassengerDao` / `BookingDao` as references. All five CRUD operations (`findById`, `findAll`, `save`, `update`, `deleteById`) must be implemented through JDBC `PreparedStatement` and `ResultSet`. The base class `AbstractDao<T, K>` already owns the connection-acquire/release boilerplate — concrete DAOs override `tableName()`, `idColumn()`, the four SQL strings, `mapRow(rs)`, and `bindForSave/Update(ps, t)`.
4. **Implement a Service layer** for the `Booking` use-case. `JdbcBookingService.bookFlight(...)` is shipped as the worked example — it opens a transaction, calls `PassengerDao.findById` + `FlightDao.findById` + `BookingDao.save` + `TicketDao.save`, and commits or rolls back as one unit. Add a sibling `PaymentService` that records a payment against a booking inside a single transaction.

The DAO classes must remain **scalable and framework-agnostic** — no Spring or Hibernate imports anywhere under `dao/` or `service/`. The only allowed dependency is `java.sql.*` and the connection pool.

## Run it locally

```bash
# Bring up MySQL with the airline_booking schema seeded
docker compose up -d

# (one-time) load the schema and seed
docker compose exec -T mysql mysql -uroot -proot < ../databases-schemas-mysql/schema.sql
docker compose exec -T mysql mysql -uroot -proot airline_booking < ../sql-crud-ddl/seed.sql

# Run the integration test against the live database
mvn -q test

# Smoke the CLI demo: list flights LAX → JFK
mvn -q exec:java -Dexec.mainClass="com.solvd.airline.app.Main"
```

## Layout

```
code/
├── pom.xml                                   maven build (mysql-connector-j, slf4j, junit-jupiter)
├── db.properties                             jdbc URL + credentials + isolation level
├── docker-compose.yml                        single-node MySQL 8 (root / root)
└── src/
    ├── main/
    │   ├── resources/
    │   │   ├── db.properties                 runtime copy of the file above
    │   │   └── logback.xml                   slf4j → logback console appender
    │   └── java/com/solvd/airline/
    │       ├── db/
    │       │   ├── ConnectionPool.java       paste your L12 implementation here
    │       │   └── ConnectionPoolNote.md     pointer to L12 source
    │       ├── entity/                       12 entity classes (4 full + 8 stubs)
    │       ├── dao/                          interface, abstract base, 12 concretes
    │       ├── service/                      interface + impl pairs
    │       └── app/Main.java                 CLI demo
    └── test/java/com/solvd/airline/
        └── dao/AirportDaoIT.java             integration test — happy path
```

## Why this layering

- **Entity** classes are pure data carriers. No JDBC code.
- **DAO** classes hide every `Connection`, `PreparedStatement`, and `ResultSet` from the rest of the app. Swap MySQL for PostgreSQL by changing one driver dependency and one URL — no business code touches.
- **Service** classes own transaction boundaries and orchestrate multiple DAOs. They are the only layer that calls `setAutoCommit(false)`, `commit()`, and `rollback()`.
- **App** is the CLI / controller — it never imports `java.sql.*`.

That four-layer rule is the test of a well-built DAO setup. Run a class-graph audit at the end of the homework: `app` depends on `service`, `service` on `dao`, `dao` on `entity`. No layer should reach upward; no `java.sql` import should appear above `dao`.

## Reference

- **Lecture deck:** `../index.html`
- **Spoken script:** `D:\!!! SOLVD\Lecture_Script_JDBC_DAO_MVC.md`
- **Schema:** `../../databases-schemas-mysql/schema.sql`
- **Seed:** `../../sql-crud-ddl/seed.sql`
- **Production parallel:** `D:\!!! SOLVD\solvd-laba\backend\auth\src\main\java\com\solvdlaba\auth\repo\UserRepository.java` — same pattern, with Spring's `NamedParameterJdbcTemplate` doing the boilerplate the `AbstractDao` does here.
