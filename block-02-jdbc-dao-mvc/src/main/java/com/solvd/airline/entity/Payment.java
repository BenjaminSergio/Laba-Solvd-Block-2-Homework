package com.solvd.airline.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Stub — homework. Maps the {@code payments} table.
 *
 * <pre>
 * payment_id  BIGINT        PK
 * booking_id  BIGINT        FK -> bookings (ON DELETE CASCADE)
 * amount      DECIMAL(10,2) — BigDecimal, never double
 * currency    CHAR(3)       ISO-4217: "EUR", "USD"
 * method      ENUM('CARD','BANK_TRANSFER','VOUCHER','REFUND')
 * paid_at     DATETIME
 * </pre>
 *
 * TODO: fields, getters, setters, plus a {@code Method} enum.
 */
public class Payment extends BaseEntity {

    public enum Method { CARD, BANK_TRANSFER, VOUCHER, REFUND }

    private Long          bookingId;
    private BigDecimal    amount;
    private String        currency;
    private Method        method;
    private LocalDateTime paidAt;

    // TODO: getters / setters
}
