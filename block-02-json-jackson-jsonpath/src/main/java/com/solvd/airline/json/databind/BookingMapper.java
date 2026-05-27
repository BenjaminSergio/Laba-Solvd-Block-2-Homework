package com.solvd.airline.json.databind;

import com.solvd.airline.entity.jackson.stubs.Booking;

import java.io.IOException;
import java.nio.file.Path;

/**
 * STUB — homework task 5 (parse JSON using Jackson).
 *
 * <p>Mirror {@link FleetMapper} for the {@link Booking} root. The same
 * preconfigured {@link com.solvd.airline.json.core.JacksonMapper#instance()}
 * mapper is reused — no new {@code ObjectMapper} per call.
 *
 * <p>Round-trip target (the integration test you write asserts this):
 * <pre>
 *   Booking original = BookingMapper.readBooking(bookingJson);
 *   String  json     = BookingMapper.writeBookingAsString(original);
 *   Booking again    = BookingMapper.readBooking(json);
 *   assertEquals(original, again);
 * </pre>
 *
 * <p>For the {@code equals(...)} contract to hold, your {@link Booking}
 * implementation must override {@code equals(Object)} and {@code hashCode()} —
 * the same shape as the reference {@code Flight.equals(...)}.
 */
public final class BookingMapper {

    private BookingMapper() { }

    public static Booking readBooking(Path jsonPath) throws IOException {
        throw new UnsupportedOperationException("Implement as part of homework task 5 — mirror FleetMapper.readFleet(Path)");
    }

    public static Booking readBooking(String json) throws IOException {
        throw new UnsupportedOperationException("Implement as part of homework task 5 — mirror FleetMapper.readFleet(String)");
    }

    public static void writeBooking(Booking booking, Path outputPath) throws IOException {
        throw new UnsupportedOperationException("Implement as part of homework task 5 — mirror FleetMapper.writeFleet");
    }

    public static String writeBookingAsString(Booking booking) throws IOException {
        throw new UnsupportedOperationException("Implement as part of homework task 5 — mirror FleetMapper.writeFleetAsString");
    }
}
