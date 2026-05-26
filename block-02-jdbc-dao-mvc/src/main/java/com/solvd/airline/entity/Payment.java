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

    public Long getBookingId() {
        return bookingId;
    }

    public void setBookingId(Long bookingId) {
        this.bookingId = bookingId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public LocalDateTime getPaidAt() {
        return paidAt;
    }

    public void setPaidAt(LocalDateTime paidAt) {
        this.paidAt = paidAt;
    }

    @java.lang.Override
    public java.lang.String toString() {
        return "Payment{" +
                "bookingId=" + bookingId +
                ", amount=" + amount +
                ", currency='" + currency + '\'' +
                ", method=" + method +
                ", paidAt=" + paidAt +
                '}';
    }
}
