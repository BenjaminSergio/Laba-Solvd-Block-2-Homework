package com.solvd.airline.app;

import com.solvd.airline.db.MyBatisSessionFactory;
import com.solvd.airline.entity.Airport;
import com.solvd.airline.entity.Flight;
import com.solvd.airline.mapper.AirportMapper;
import com.solvd.airline.mapper.FlightMapper;
import org.apache.ibatis.session.SqlSession;

/**
 * CLI demo — proves the project wires up correctly and produces output
 * against the dockerised MySQL.
 *
 * Pre-flight:
 *   docker compose up -d
 *   docker compose exec -T mysql mysql -uroot -proot &lt; ../databases-schemas-mysql/schema.sql
 *   docker compose exec -T mysql mysql -uroot -proot airline_booking &lt; ../sql-crud-ddl/seed.sql
 *
 * Run:
 *   mvn -q exec:java -Dexec.mainClass=com.solvd.airline.app.Main
 */
public class Main {

    public static void main(String[] args) {
        // Read-only session — autocommit is fine since nothing is written.
        try (SqlSession session = MyBatisSessionFactory.getInstance().openSession(true)) {

            AirportMapper airports = session.getMapper(AirportMapper.class);
            FlightMapper  flights  = session.getMapper(FlightMapper.class);

            System.out.println("─── airports (first 5 from findAll) ───");
            airports.findAll().stream().limit(5).forEach(System.out::println);

            System.out.println();
            System.out.println("─── airport WAW via XML mapper ───");
            airports.findByIata("WAW").ifPresent(System.out::println);

            System.out.println();
            System.out.println("─── search(countryCode=US) — dynamic <where>/<if> ───");
            airports.search("US", null, null).forEach(System.out::println);

            System.out.println();
            System.out.println("─── flights (top 3 upcoming via annotation mapper) ───");
            flights.findUpcoming(3).forEach(System.out::println);

            System.out.println();
            System.out.println("Done. Build a session for write-paths and call the *Service classes.");
        }
    }
}
