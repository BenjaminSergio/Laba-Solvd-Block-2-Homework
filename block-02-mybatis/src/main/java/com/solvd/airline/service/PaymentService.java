package com.solvd.airline.service;

import com.solvd.airline.entity.Payment;

import java.math.BigDecimal;

/**
 * Framework-agnostic service contract for the payment use-case.
 * Reference implementation: {@link MyBatisPaymentService}.
 */
public interface PaymentService {

    Payment recordPayment(long bookingId, BigDecimal amount,
                          String currency, Payment.Method method);
}
