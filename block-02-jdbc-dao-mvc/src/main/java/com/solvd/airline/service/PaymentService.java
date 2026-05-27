package com.solvd.airline.service;

import com.solvd.airline.entity.Payment;

import java.math.BigDecimal;

/**
 * Service contract para registrar pagamentos.
 * Implementações: JdbcPaymentService (homework), futuramente JpaPaymentService.
 */
public interface PaymentService {

    Payment recordPayment(long bookingId, BigDecimal amount, String currency, Payment.Method method);
}