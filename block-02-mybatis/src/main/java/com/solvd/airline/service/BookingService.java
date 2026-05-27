package com.solvd.airline.service;

import com.solvd.airline.entity.Booking;

import java.math.BigDecimal;

/**
 * Service contract — identical to the Lecture 03 interface. The whole
 * point of declaring the service layer through an interface is that
 * the implementation can swap (JDBC -> MyBatis -> JPA) without any
 * controller / caller change.
 *
 * Reference implementation: {@link MyBatisBookingService}.
 */
public interface BookingService {

    Booking bookFlight(long passengerId, long flightId, long fareClassId,
                       BigDecimal basePrice);

    void cancelBooking(long bookingId);
}
