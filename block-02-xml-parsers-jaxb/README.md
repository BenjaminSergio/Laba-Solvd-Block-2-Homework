# Lecture 04 — XML · StAX · SAX · DOM · XSD · XPath · JAXB · Homework starter

Starter project for the homework that ships with **Section 02 / Lecture 04 — XML · StAX · SAX · DOM · XSD · XPath · JAXB**.

Same `airline_booking` domain you DAO-mapped in Lecture 03; this time the storage is XML, the contract is XSD, and the binding is Jakarta XML Bind 4.0. JDK 17 only — no database, no Docker. Run `mvn test` and the four reference integration tests pass; finish the four stubs and the four homework integration tests you add also pass.

## Homework

1. **Read** the five articles linked from the lecture page — XML at w3schools, plus Oracle's StAX, SAX, DOM, and JAXB tutorials.
2. **Create `booking.xml` + `booking.xsd`.** Cover at least five classes from the airline hierarchy (suggested: `Booking`, `Passenger`, `Ticket`, `FareClass`, `Payment`). The reference `fleet.xml` + `fleet.xsd` in `src/main/resources/xml/` are the example to mirror. Validate against the XSD using the reference `XsdValidator`.
3. **Validate XML using the XSD.** `XsdValidator.validate(xml, xsd)` is shipped and XXE-hardened. Use it on both `fleet.xml` and your new `booking.xml`.
4. **Parse XML using SAX.** `SaxBookingHandler` is a stub — fill in the four `DefaultHandler` hooks following the reference `SaxFleetHandler`. Common bug: `characters()` may fire multiple times per element; always buffer, never assign directly.
5. **Add JAXB annotations** to the four stub entities in `entity/jaxb/stubs/` — `Booking`, `Ticket`, `Payment`, `FareClass`. Cover: `@XmlAttribute`, `@XmlElement`, `@XmlElementWrapper`, `@XmlTransient`, an `XmlAdapter` for at least one temporal type, and `@XmlEnum`/`@XmlEnumValue` for the enums.
6. **Parse `booking.xml` using JAXB.** Write a `BookingMarshaller` (mirror `FleetMarshaller`) and a `BookingJaxbRoundTripIT` test asserting `marshal → unmarshal → equals`.

The JAXB entities must remain **annotation-only** — no business logic, no `java.sql` imports, no Hibernate. The only allowed dependencies are `jakarta.xml.bind.*`, `java.time.*`, `java.math.BigDecimal`, and `java.util.*`.

## Run it locally

```bash
# Compile and run the reference integration tests
mvn -q compile
mvn -q test

# Run the CLI demo: validate, parse three ways, XPath, round-trip via JAXB
mvn -q exec:java -Dexec.mainClass="com.solvd.airline.app.Main"
```

Expected output: every step prints success. Validation, DOM count, SAX count, StAX count, XPath query results, JAXB unmarshal-marshal round-trip.

## Layout

```
code/
├── pom.xml                                       jakarta.xml.bind-api 4.0 + jaxb-runtime 4.0
├── README.md
└── src/
    ├── main/
    │   ├── resources/
    │   │   ├── xml/
    │   │   │   ├── fleet.xml                     reference document
    │   │   │   ├── fleet.xsd                     reference schema
    │   │   │   ├── booking.xml                   STUB — student writes
    │   │   │   └── booking.xsd                   STUB — student writes
    │   │   └── logback.xml
    │   └── java/com/solvd/airline/
    │       ├── entity/jaxb/                      5 annotated reference entities + package-info.java
    │       │   ├── Fleet.java                    root @XmlRootElement
    │       │   ├── Airport.java
    │       │   ├── AircraftModel.java
    │       │   ├── Aircraft.java
    │       │   ├── Route.java
    │       │   ├── Flight.java
    │       │   ├── package-info.java             namespace pattern (commented out)
    │       │   └── stubs/                        4 unannotated entities — student annotates
    │       │       ├── Booking.java
    │       │       ├── Ticket.java
    │       │       ├── Payment.java
    │       │       └── FareClass.java
    │       ├── xml/
    │       │   ├── dom/DomFleetParser.java       REFERENCE — full DOM walk
    │       │   ├── sax/SaxFleetHandler.java      REFERENCE — full DefaultHandler
    │       │   ├── sax/SaxBookingHandler.java    STUB — student implements
    │       │   ├── stax/StaxFleetReader.java     REFERENCE — cursor StAX
    │       │   ├── xpath/XPathQueries.java       STUB — 5 queries to write
    │       │   ├── jaxb/FleetMarshaller.java     REFERENCE — full round-trip
    │       │   ├── jaxb/adapters/
    │       │   │   ├── LocalDateAdapter.java
    │       │   │   ├── LocalDateTimeAdapter.java
    │       │   │   └── InstantAdapter.java
    │       │   └── validation/XsdValidator.java  REFERENCE — XXE-hardened
    │       └── app/Main.java                     CLI demo
    └── test/java/com/solvd/airline/xml/
        ├── DomFleetParserIT.java                 reference IT
        ├── JaxbRoundTripIT.java                  reference IT
        ├── XsdValidatorIT.java                   reference IT
        └── XsdValidatorXxeIT.java                reference IT — proves XXE rejection
```

## Why this layering

- **`entity/jaxb/`** holds pure JAXB-annotated data carriers. No XML code, no SAX, no DOM. Just classes + annotations.
- **`xml/dom/`, `xml/sax/`, `xml/stax/`, `xml/xpath/`** hold the three parser models + XPath, each parsing the *same* `fleet.xml` independently — direct comparison.
- **`xml/jaxb/`** wraps the JAXB context, marshaller, unmarshaller, and adapters. Reuse via `FleetMarshaller.unmarshal(file)`.
- **`xml/validation/`** is XSD validation, XXE-hardened. One class everyone calls before parsing untrusted XML.
- **`app/`** is the CLI demo that wires everything together — no business code.

That layering is the test of a well-built XML setup. Run a class-graph audit: `app` depends on `xml/*` and `entity/jaxb`; `xml/*` depends on `entity/jaxb`; `entity/jaxb` depends on nothing in the project. No layer reaches upward; no `org.xml.sax.*` import appears above `xml/`.

## Reference

- **Lecture deck:** `../index.html`
- **Spoken script:** `D:\!!! SOLVD\Lecture_Script_XML_Parsers_JAXB.md`
- **Sister project (JDBC layer for the same entities):** `../../jdbc-dao-mvc/code/`
- **Production parallel:** `D:\!!! SOLVD\solvd-laba\` — Spring's `Jaxb2Marshaller` and `pom.xml` (Maven XSD) consume the same patterns at production scale.
