package com.solvd.airline.app;

import com.solvd.airline.dao.AirportDao;
import com.solvd.airline.dao.FlightDao;
import com.solvd.airline.entity.Airport;
import com.solvd.airline.entity.Flight;

import java.time.LocalDate;
import java.util.List;

/**
 * CLI demo — the "controller" layer in MVC terms. It NEVER imports
 * {@code java.sql.*} and NEVER touches the {@link com.solvd.airline.db.ConnectionPool}
 * directly. Every persistence concern is hidden behind a DAO call.
 *
 * Run:  mvn -q exec:java
 *
 * Output:
 *   - the LAX airport row
 *   - every flight on the LAX -> JFK route on a sample date
 */
public final class Main {

    public static void main(String[] args) {
        AirportDao airportDao = new AirportDao();
        FlightDao  flightDao  = new FlightDao();

        Airport lax = airportDao.findByIata("LAX")
                .orElseThrow(() -> new IllegalStateException("LAX missing — did you load seed.sql?"));
        Airport jfk = airportDao.findByIata("JFK")
                .orElseThrow(() -> new IllegalStateException("JFK missing"));

        System.out.println("Origin      : " + lax);
        System.out.println("Destination : " + jfk);

        // The route id between these two airports lives in the routes table; for
        // the demo we read whatever flights exist on LAX -> JFK on 2026-06-01.
        // Adjust the date / route_id to match your seed.
        long sampleRouteId = 1L;
        LocalDate when = LocalDate.of(2026, 6, 1);
        List<Flight> flights = flightDao.findByRouteOnDate(sampleRouteId, when);

        System.out.println();
        System.out.println(flights.size() + " flight(s) on route " + sampleRouteId + " on " + when);
        flights.forEach(f -> System.out.println("  " + f));
    }

    private Main() { }
}
