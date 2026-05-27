package com.solvd.airline.mapper;

import com.solvd.airline.db.MyBatisSessionFactory;
import com.solvd.airline.entity.Airport;
import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Integration test for the XML-mapped {@link AirportMapper}. Runs against
 * the dockerised MySQL booted from {@code docker-compose.yml} after the
 * Lecture 03 schema + seed have been loaded.
 *
 * Pre-flight:
 *   docker compose up -d
 *   docker compose exec -T mysql mysql -uroot -proot &lt; ../databases-schemas-mysql/schema.sql
 *   docker compose exec -T mysql mysql -uroot -proot airline_booking &lt; ../sql-crud-ddl/seed.sql
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class AirportMapperIT {

    @Test
    @Order(1)
    @DisplayName("XML mapper · findAll returns seeded rows")
    void findAll_returnsSeededRows() {
        try (SqlSession session = MyBatisSessionFactory.getInstance().openSession(true)) {
            AirportMapper mapper = session.getMapper(AirportMapper.class);
            List<Airport> all = mapper.findAll();
            assertFalse(all.isEmpty(), "seed.sql should have populated `airports`");
            all.stream().limit(3).forEach(System.out::println);
        }
    }

    @Test
    @Order(2)
    @DisplayName("XML mapper · findByIata('LAX') returns Los Angeles")
    void findByIata_lax() {
        try (SqlSession session = MyBatisSessionFactory.getInstance().openSession(true)) {
            AirportMapper mapper = session.getMapper(AirportMapper.class);
            Optional<Airport> lax = mapper.findByIata("LAX");
            assertTrue(lax.isPresent(), "LAX should be in the seed");
            assertEquals("LAX", lax.get().getIata());
            assertEquals("US",  lax.get().getCountryCode());
        }
    }

    @Test
    @Order(3)
    @DisplayName("XML mapper · dynamic SQL: search by country only")
    void search_byCountryCode() {
        try (SqlSession session = MyBatisSessionFactory.getInstance().openSession(true)) {
            AirportMapper mapper = session.getMapper(AirportMapper.class);
            List<Airport> us = mapper.search("US", null, null);
            assertFalse(us.isEmpty(), "expected at least one US airport in seed");
            us.forEach(a -> assertEquals("US", a.getCountryCode()));
        }
    }

    @Test
    @Order(4)
    @DisplayName("save → findById → deleteById round-trips a new airport")
    void save_findById_delete_roundTrip() {
        try (SqlSession session = MyBatisSessionFactory.getInstance().openSession()) {
            AirportMapper mapper = session.getMapper(AirportMapper.class);

            Airport airport = new Airport(
                    null, "ZZ1", "Test Field 1", "Testville", "ZZ", "Etc/UTC");
            mapper.save(airport);
            session.commit();

            assertNotNull(airport.getId(), "save() should populate the generated id");

            Optional<Airport> found = mapper.findById(airport.getId());
            assertTrue(found.isPresent());
            assertEquals("ZZ1", found.get().getIata());

            boolean deleted = mapper.deleteById(airport.getId());
            session.commit();
            assertTrue(deleted, "deleteById should report success");

            assertTrue(mapper.findById(airport.getId()).isEmpty(),
                    "row should be gone after deleteById");
        }
    }

    @Test
    @Order(5)
    @DisplayName("L1 cache: repeat findByIata inside one session returns same instance")
    void l1Cache_sameInstanceWithinSession() {
        try (SqlSession session = MyBatisSessionFactory.getInstance().openSession(true)) {
            AirportMapper mapper = session.getMapper(AirportMapper.class);
            Airport first  = mapper.findByIata("LAX").orElseThrow();
            Airport second = mapper.findByIata("LAX").orElseThrow();
            assertSame(first, second,
                    "L1 cache should return the cached object, not a fresh instance");
        }
    }
}
