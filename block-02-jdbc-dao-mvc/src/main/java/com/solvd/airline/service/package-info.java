/**
 * Service layer — owns transaction boundaries and orchestrates multiple DAOs.
 *
 * The contract is the public interface ({@link com.solvd.airline.service.BookingService},
 * {@link com.solvd.airline.service.PassengerService}). Implementations are swappable —
 * a JDBC implementation today, a JPA implementation later, a remote-RPC implementation
 * tomorrow — without touching anything in {@link com.solvd.airline.app}.
 *
 * <strong>Rule:</strong> only this layer calls {@code Connection.setAutoCommit(false)},
 * {@code commit()}, and {@code rollback()}. DAOs do not own transactions; controllers
 * never see them.
 */
package com.solvd.airline.service;
