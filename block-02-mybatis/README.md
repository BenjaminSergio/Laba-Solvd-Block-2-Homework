# MyBatis homework — Section 02 / Lecture 06

Parallel project to the Lecture 03 JDBC homework (`../jdbc-dao-mvc/code/`).
Same `airline_booking` schema, same twelve entities, MyBatis layer instead of
the hand-rolled `AbstractDao`.

## What you get

```
code/
├── pom.xml                                  ← mybatis 3.5.x + mysql-connector-j + junit
├── docker-compose.yml                       ← MySQL 8.4 for local dev / tests
├── db.properties                            ← URL + creds (mirrored under src/main/resources/)
├── src/main/java/com/solvd/airline/
│   ├── app/Main.java                        ← CLI demo
│   ├── db/MyBatisSessionFactory.java        ← Holder-idiom SqlSessionFactory singleton
│   ├── entity/                              ← 12 plain-Java entities (8 complete + 4 stubs)
│   ├── mapper/
│   │   ├── AirportMapper.java               ← XML-backed mapper (reference)
│   │   ├── FlightMapper.java                ← annotation-backed mapper (reference)
│   │   ├── PassengerMapper.java             ← annotation (used by booking service)
│   │   ├── BookingMapper.java               ← annotation (used by booking service)
│   │   ├── TicketMapper.java                ← annotation (used by booking service)
│   │   ├── PaymentMapper.java               ← annotation (used by payment service)
│   │   └── …Mapper.java                     ← 6 stubs you complete (Aircraft, AircraftModel,
│   │                                          Route, SeatAssignment, FareClass, Seat)
│   └── service/
│       ├── BookingService.java              ← interface (identical to L03)
│       ├── MyBatisBookingService.java       ← drop-in replacement for JdbcBookingService
│       ├── PaymentService.java
│       └── MyBatisPaymentService.java
├── src/main/resources/
│   ├── db.properties
│   ├── logback.xml
│   ├── mybatis-config.xml                   ← single MyBatis config file
│   └── mappers/AirportMapper.xml            ← XML reference
└── src/test/java/com/solvd/airline/mapper/
    ├── AirportMapperIT.java                 ← integration tests for XML mapper
    └── FlightMapperIT.java                  ← integration tests for annotation mapper
```

## Run

```bash
# 1. Boot MySQL
docker compose up -d

# 2. Load schema + seed (paths point at the Lecture 01/02 artifacts)
docker compose exec -T mysql mysql -uroot -proot \
    < ../../databases-schemas-mysql/schema.sql
docker compose exec -T mysql mysql -uroot -proot airline_booking \
    < ../../sql-crud-ddl/seed.sql

# 3. Demo
mvn -q exec:java -Dexec.mainClass=com.solvd.airline.app.Main

# 4. Tests
mvn -q test
```

## What's done · what you do

| Done in the starter | Your homework |
|---------------------|---------------|
| `pom.xml`, `docker-compose.yml`, `db.properties`, `logback.xml` | — |
| `mybatis-config.xml` with all settings, type aliases, environments, and the six reference mapper entries | Add the six stub mappers to the `<mappers>` block as you implement them |
| 8 complete entities (Airport, Flight, Booking, Passenger, Ticket, Payment, BaseEntity, Identifiable) | Copy or extend the 6 minimal entity stubs (Aircraft, AircraftModel, Route, SeatAssignment, FareClass, Seat) from your L03 work |
| `AirportMapper` — XML reference with `<resultMap>`, `<sql>` + `<include>`, `<where>`/`<if>`, `useGeneratedKeys` | Pick XML or annotations per stub mapper; use AirportMapper.xml as the template for any mapper that may grow dynamic SQL |
| `FlightMapper` — annotation reference with `@Select`/`@Insert`/`@Update`/`@Delete` and `@Options(useGeneratedKeys=true)` | Use as the template for short static SQL |
| `PassengerMapper`, `BookingMapper`, `TicketMapper`, `PaymentMapper` — annotation mappers used by the services | — |
| `MyBatisBookingService` — the JdbcBookingService replacement; one `try (SqlSession session = ...)` owns the whole transaction | Implement equivalents for any other multi-mapper operations you want |
| `MyBatisPaymentService` — one-tx payment-record path | — |
| `AirportMapperIT` (5 tests) and `FlightMapperIT` (4 tests) | Add at least two of your own — one for a mapper you wrote, one for a service |

## Lecture anchors

| Lecture slide | File / pattern |
|---------------|----------------|
| Act 1 slide 9 — `Builder → Factory → Session` | `db/MyBatisSessionFactory.java` |
| Act 1 slide 10 — `mybatis-config.xml` | `src/main/resources/mybatis-config.xml` |
| Act 2 slide 16 — XML mapper anatomy | `src/main/resources/mappers/AirportMapper.xml` |
| Act 2 slide 17 — annotation mapper | `mapper/FlightMapper.java` |
| Act 2 slide 21 — `#{}` vs `${}` | every mapper uses `#{}` only |
| Act 3 slide 27 — multi-mapper transaction | `service/MyBatisBookingService.bookFlight` |
| Act 4 slide 31 — `<resultMap>` with `<id>`/`<result>` | `AirportMapper.xml` |
| Act 5 slide 39 — `<where>` + `<if>` | `AirportMapper.xml` `search` statement |
| Act 6 slide 46 — L1 cache | `AirportMapperIT.l1Cache_sameInstanceWithinSession` |

## Submit

Zip the project (excluding `target/` and `*.iml`) and upload as
`<surname>_mybatis_homework.zip` to next week's submission folder.
