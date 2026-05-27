# Lecture 05 — JSON · Jackson · JSONPath · Homework starter

Starter project for the homework that ships with **Section 02 / Lecture 05 — JSON · Jackson · JSONPath**.

Same `airline_booking` domain you DAO-mapped in Lecture 03 and JAXB-bound in Lecture 04; this time the wire format is JSON, the schema is JSON Schema 2020-12, the binding is Jackson 2.17, and queries are written in JSONPath (Jayway). JDK 17 only — no database, no Docker. Run `mvn test` and the five reference integration tests pass; finish the four stubs and the integration tests you add also pass.

## Homework

1. **Read** the linked articles — Crockford's `json.org`, the Jackson docs on GitHub, the Jayway JSONPath README, and the JSON Schema 2020-12 spec.
2. **Create `booking.json` + `booking-schema.json`.** Cover at least five classes from the airline hierarchy (suggested: `Booking`, `Passenger`, `Ticket`, `FareClass`, `Payment`). The reference `fleet.json` + `fleet-schema.json` in `src/main/resources/json/` are the example to mirror.
3. **Validate JSON using the schema.** Use the reference `JsonSchemaValidator` (preconfigured for Draft 2020-12) on both `fleet.json` and your new `booking.json`.
4. **Add Jackson annotations to the hierarchy.** Four entity stubs are in `entity/jackson/stubs/` — `Booking`, `Ticket`, `Payment`, `FareClass`. Cover all five Jackson annotation groups from Act 4:
   - **A · Naming**: `@JsonProperty`, `@JsonAlias`, `@JsonRootName`, `@JsonNaming` (at least two of the four).
   - **B · Date**: `@JsonFormat` on at least one `LocalDate`/`LocalDateTime`/`Instant` field.
   - **C · Skip / control**: at least one `@JsonIgnore` *and* at least one `@JsonProperty(access = READ_ONLY)` or `WRITE_ONLY` — the asymmetric exam-Q3 answer.
   - **D · Polymorphism**: `@JsonTypeInfo(use = Id.NAME)` + `@JsonSubTypes` on at least one type — e.g. `Payment` with `CreditCardPayment` / `BankTransferPayment` subtypes.
   - **E · Construction**: one `@JsonCreator` annotated constructor or factory method on an immutable POJO/record.
5. **Parse `booking.json` using Jackson.** Write a `BookingMapper` (mirror `FleetMapper`) and a `BookingJacksonRoundTripIT` test asserting `serialize → deserialize → equals`.
6. **Write 5 JSONPath queries against `booking.json`** in `BookingJsonPathQueries` (mirror `JsonPathQueries`). At minimum: a child-path query, a recursive-descent (`..`) query, a predicate filter (`[?(@.field == 'X')]`), an array slice, and a function call (`length()`, `min()`, `max()`).

The Jackson entities must remain **annotation-only** — no business logic, no `java.sql` imports, no Hibernate. The only allowed dependencies are `com.fasterxml.jackson.annotation.*`, `java.time.*`, `java.math.BigDecimal`, and `java.util.*`.

## Run it locally

```bash
# Compile and run the reference integration tests
mvn -q compile
mvn -q test

# Run the CLI demo: validate, three reading modes, JSONPath, round-trip, polymorphism-safe demo
mvn -q exec:java -Dexec.mainClass="com.solvd.airline.app.Main"
```

Expected output: every step prints success. Schema validation, JsonNode tree walk, streaming parser count, JSONPath query results, databind round-trip, polymorphism-safe demo.

## Layout

```
code/
├── pom.xml                                       Jackson 2.17 + jayway/json-path 2.9 + networknt 1.5
├── README.md
└── src/
    ├── main/
    │   ├── resources/
    │   │   ├── json/
    │   │   │   ├── fleet.json                    reference document
    │   │   │   ├── fleet-schema.json             reference JSON Schema 2020-12
    │   │   │   ├── booking.json                  STUB — student writes
    │   │   │   └── booking-schema.json           STUB — student writes
    │   │   └── logback.xml
    │   └── java/com/solvd/airline/
    │       ├── entity/jackson/                   6 annotated reference entities
    │       │   ├── Fleet.java                    root POJO, @JsonRootName("fleet")
    │       │   ├── Airport.java
    │       │   ├── AircraftModel.java
    │       │   ├── Aircraft.java
    │       │   ├── Route.java
    │       │   ├── Flight.java
    │       │   └── stubs/                        4 unannotated entities — student annotates
    │       │       ├── Booking.java
    │       │       ├── Ticket.java
    │       │       ├── Payment.java
    │       │       └── FareClass.java
    │       ├── json/
    │       │   ├── core/JacksonMapper.java                preconfigured ObjectMapper factory
    │       │   ├── tree/JsonTreeReader.java               REFERENCE — JsonNode walks (DOM-equivalent)
    │       │   ├── stream/JsonStreamReader.java           REFERENCE — JsonParser cursor (StAX-equivalent)
    │       │   ├── stream/JsonStreamWriter.java           REFERENCE — JsonGenerator (StAX-writer)
    │       │   ├── databind/FleetMapper.java              REFERENCE — full round-trip via ObjectMapper
    │       │   ├── databind/BookingMapper.java            STUB — student implements
    │       │   ├── jsonpath/JsonPathQueries.java          REFERENCE — 5 queries on fleet.json
    │       │   ├── jsonpath/BookingJsonPathQueries.java   STUB — student writes 5 on booking.json
    │       │   └── validation/JsonSchemaValidator.java    REFERENCE — networknt 2020-12
    │       └── app/Main.java                              CLI demo wires everything
    └── test/java/com/solvd/airline/json/
        ├── JsonTreeReaderIT.java                          reference IT
        ├── JacksonRoundTripIT.java                        reference IT
        ├── JsonSchemaValidatorIT.java                     reference IT
        ├── JsonPathQueriesIT.java                         reference IT
        └── PolymorphicDeserializationSafetyIT.java        reference IT — Jackson CVE-2017-7525 family
```

## Why this layering

- **`entity/jackson/`** holds pure Jackson-annotated data carriers. No JSON code, no ObjectMapper imports. Just classes + annotations.
- **`json/tree/`, `json/stream/`, `json/databind/`** hold the three reading modes (`JsonNode` tree, streaming `JsonParser`, full databind) — each handling the *same* `fleet.json` independently, direct comparison.
- **`json/jsonpath/`** wraps Jayway and ships five reference queries on the fleet.
- **`json/validation/`** is schema validation, preconfigured for Draft 2020-12. One class everyone calls before deserialising untrusted JSON.
- **`json/core/`** holds the one preconfigured `ObjectMapper` factory (`JavaTimeModule`, `Jdk8Module`, `ParameterNamesModule` registered, `FAIL_ON_UNKNOWN_PROPERTIES=true`, ISO-8601 dates).
- **`app/`** is the CLI demo wiring everything — no business code.

That layering is the test of a well-built Jackson setup. Run a class-graph audit: `app` depends on `json/*` and `entity/jackson`; `json/*` depends on `entity/jackson`; `entity/jackson` depends on nothing in the project. No layer reaches upward; no `com.fasterxml.jackson.core.*` import appears above `json/`.

## Reference

- **Lecture deck:** `../index.html`
- **Spoken script:** `D:\!!! SOLVD\Lecture_Script_JSON_Jackson_JSONPath.md`
- **Sister project (XML/JAXB layer for the same entities):** `../../xml-parsers-jaxb/code/`
- **Production parallel:** `D:\!!! SOLVD\solvd-laba\` — Spring Boot's autoconfigured `ObjectMapper`, the same Jackson 2.17 pin, the same `JavaTimeModule` registration. Same patterns at production scale.
