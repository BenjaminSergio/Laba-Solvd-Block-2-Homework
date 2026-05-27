package com.solvd.airline.mapper;

import com.solvd.airline.db.MyBatisSessionFactory;
import com.solvd.airline.entity.Flight;
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
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Integration test for the annotation-mapped {@link FlightMapper}.
 *
 * Same pre-flight as {@link AirportMapperIT}: schema + seed loaded into
 * the dockerised MySQL. The annotation style proves out the symmetric
 * mapper-style story — both XML and annotation mappers reach the
 * database through the same SqlSession.
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class FlightMapperIT {

    @Test
    @Order(1)
    @DisplayName("Annotation mapper · findAll returns seeded rows")
    void findAll_returnsSeededRows() {
        try (SqlSession session = MyBatisSessionFactory.getInstance().openSession(true)) {
            FlightMapper mapper = session.getMapper(FlightMapper.class);
            List<Flight> all = mapper.findAll();
            assertFalse(all.isEmpty(), "seed.sql should have populated `flights`");
            all.stream().limit(3).forEach(System.out::println);
        }
    }

    @Test
    @Order(2)
    @DisplayName("Annotation mapper · findUpcoming(N) honours LIMIT")
    void findUpcoming_limits() {
        try (SqlSession session = MyBatisSessionFactory.getInstance().openSession(true)) {
            FlightMapper mapper = session.getMapper(FlightMapper.class);
            List<Flight> three = mapper.findUpcoming(3);
            assertTrue(three.size() <= 3, "LIMIT 3 must cap the result size");
        }
    }

    @Test
    @Order(3)
    @DisplayName("Annotation mapper · findByFlightNumber returns matching flights")
    void findByFlightNumber() {
        try (SqlSession session = MyBatisSessionFactory.getInstance().openSession(true)) {
            FlightMapper mapper = session.getMapper(FlightMapper.class);
            List<Flight> all = mapper.findAll();
            if (all.isEmpty()) return; // empty seed — skip the rest

            String knownNumber = all.get(0).getFlightNumber();
            List<Flight> match = mapper.findByFlightNumber(knownNumber);
            assertFalse(match.isEmpty(),
                    "findByFlightNumber should find the seed flight " + knownNumber);
            match.forEach(f -> assertEquals(knownNumber, f.getFlightNumber()));
        }
    }

    @Test
    @Order(4)
    @DisplayName("Annotation mapper · updateStatus persists the change")
    void updateStatus_roundTrip() {
        try (SqlSession session = MyBatisSessionFactory.getInstance().openSession()) {
            FlightMapper mapper = session.getMapper(FlightMapper.class);
            Optional<Flight> any = mapper.findAll().stream().findFirst();
            if (any.isEmpty()) return; // empty seed — skip
            Flight f = any.get();
            Flight.Status original = f.getStatus();
            Flight.Status target   = (original == Flight.Status.SCHEDULED)
                    ? Flight.Status.BOARDING : Flight.Status.SCHEDULED;

            int rows = mapper.updateStatus(f.getId(), target);
            assertEquals(1, rows);

            Flight after = mapper.findById(f.getId()).orElseThrow();
            assertEquals(target, after.getStatus());

            // Restore original to keep the seed deterministic.
            mapper.updateStatus(f.getId(), original);
            session.commit();
        }
    }
}
