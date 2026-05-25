package com.solvd.airline.dao;

import com.solvd.airline.entity.Airport;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Integration test — runs against the Dockerised MySQL booted from
 * {@code docker-compose.yml} after the schema + seed have been loaded.
 *
 * Naming convention: classes ending in {@code IT} are integration tests.
 * Surefire (see {@code pom.xml}) picks both {@code *Test} and {@code *IT}.
 *
 * Pre-flight:
 *   docker compose up -d
 *   docker compose exec -T mysql mysql -uroot -proot < ../databases-schemas-mysql/schema.sql
 *   docker compose exec -T mysql mysql -uroot -proot airline_booking < ../sql-crud-ddl/seed.sql
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(org.junit.jupiter.api.MethodOrderer.OrderAnnotation.class)
class AirportDaoIT {

    private final AirportDao dao = new AirportDao();

    @Test
    @org.junit.jupiter.api.Order(1)
    @DisplayName("findAll returns at least one row from the seeded data")
    void findAll_returnsSeededRows() {
        List<Airport> rows = dao.findAll();
        assertFalse(rows.isEmpty(), "seed.sql should have populated `airports`");
        rows.stream().limit(3).forEach(System.out::println);
    }

    @Test
    @org.junit.jupiter.api.Order(2)
    @DisplayName("findByIata('LAX') returns Los Angeles International")
    void findByIata_lax() {
        Optional<Airport> lax = dao.findByIata("LAX");
        assertTrue(lax.isPresent(), "LAX should be in the seed");
        assertEquals("LAX", lax.get().getIata());
        assertEquals("US",  lax.get().getCountryCode());
    }

    @Test
    @org.junit.jupiter.api.Order(3)
    @DisplayName("save → findById → deleteById round-trips a new airport")
    void save_findById_delete_roundTrip() {
        Airport newAirport = new Airport(
                null, "ZZZ", "Test Airport", "Testville", "ZZ", "Etc/UTC");

        Airport saved = dao.save(newAirport);
        assertNotNull(saved.getId(), "save() should populate the generated id");

        Optional<Airport> found = dao.findById(saved.getId());
        assertTrue(found.isPresent());
        assertEquals("ZZZ", found.get().getIata());

        boolean deleted = dao.deleteById(saved.getId());
        assertTrue(deleted, "deleteById should report success");

        assertTrue(dao.findById(saved.getId()).isEmpty(),
                "row should be gone after deleteById");
    }

    @AfterAll
    void shutdown() {
        // The pool's shutdown hook is best-effort; calling it explicitly here
        // makes test runs deterministic on CI.
        try {
            com.solvd.airline.db.ConnectionPool.getInstance().shutdown();
        } catch (Exception ignored) { }
    }
}
