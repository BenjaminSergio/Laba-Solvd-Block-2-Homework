package com.solvd.airline.service;

import com.solvd.airline.dao.DaoException;
import com.solvd.airline.dao.PassengerDao;
import com.solvd.airline.entity.Passenger;

import java.time.LocalDate;
import java.util.Objects;
import java.util.regex.Pattern;

/**
 * Small, deliberately boring service. The interesting one is
 * {@link JdbcBookingService} — this exists so students see that not every
 * service spans multiple DAOs. Some are simply business-rule wrappers
 * around a single DAO.
 */
public interface PassengerService {

    Passenger register(String firstName, String lastName, String email, LocalDate dob);

    Passenger findOrFail(long id);

    /** Reference implementation. */
    final class Default implements PassengerService {

        private static final Pattern EMAIL = Pattern.compile("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$");

        private final PassengerDao dao;

        public Default(PassengerDao dao) {
            this.dao = Objects.requireNonNull(dao);
        }

        @Override
        public Passenger register(String firstName, String lastName, String email, LocalDate dob) {
            if (!EMAIL.matcher(email).matches()) {
                throw new IllegalArgumentException("Invalid email: " + email);
            }
            if (dob.isAfter(LocalDate.now().minusYears(2))) {
                throw new IllegalArgumentException("date_of_birth implausible: " + dob);
            }
            dao.findByEmail(email).ifPresent(p -> {
                throw new IllegalStateException("Email already registered: " + email);
            });
            Passenger p = new Passenger();
            p.setFirstName(firstName);
            p.setLastName(lastName);
            p.setEmail(email);
            p.setDateOfBirth(dob);
            return dao.save(p);
        }

        @Override
        public Passenger findOrFail(long id) {
            return dao.findById(id)
                    .orElseThrow(() -> new DaoException("Unknown passenger id=" + id));
        }
    }
}
