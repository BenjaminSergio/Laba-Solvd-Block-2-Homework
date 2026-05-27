package com.solvd.airline.entity.jackson.stubs;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * STUB — homework task 4 (Group D · Polymorphism is anchored here).
 *
 * <p>Annotate so {@code {"method": "CARD", "amount": ..., ...}} round-trips.
 * Coverage:
 *
 * <ul>
 *   <li>{@code method} → no annotation needed. The {@link Method} enum is
 *       UPPERCASE — matches the JSON Schema {@code enum} values byte-for-byte,
 *       so Jackson serialises by {@code name()} by default (the JSON parallel
 *       of JAXB's {@code @XmlEnum} when names already match).</li>
 *   <li>{@code amount} → plain {@link BigDecimal}, no annotation.</li>
 *   <li>{@code currency} → plain string (ISO-4217 three-letter code).</li>
 *   <li>{@code paidAt} → {@link LocalDateTime} + optional {@code @JsonFormat}.</li>
 * </ul>
 *
 * <p><strong>Bonus exercise · Group D (Polymorphism):</strong> split this
 * class into a sealed hierarchy — {@code Payment} abstract, with subtypes
 * {@code CardPayment} (extra {@code cardLast4}), {@code BankTransferPayment}
 * (extra {@code accountIban}), {@code VoucherPayment} (extra {@code voucherCode}).
 * Then on the base class add:
 *
 * <pre>
 * &#64;JsonTypeInfo(use = Id.NAME, property = "method")
 * &#64;JsonSubTypes({
 *     &#64;JsonSubTypes.Type(value = CardPayment.class,         name = "CARD"),
 *     &#64;JsonSubTypes.Type(value = BankTransferPayment.class, name = "BANK_TRANSFER"),
 *     &#64;JsonSubTypes.Type(value = VoucherPayment.class,      name = "VOUCHER")
 * })
 * </pre>
 *
 * <p>Jackson then deserialises each JSON object based on its {@code "method"}
 * key into the correct subtype — the JSON parallel of JAXB's
 * {@code @XmlSeeAlso}. Important: this is the <strong>safe</strong> form of
 * polymorphism. NEVER use {@code @JsonTypeInfo(use = Id.CLASS)} or
 * {@code enableDefaultTyping()} on data crossing a trust boundary — that
 * opens the CVE-2017-7525 family of remote-code-execution gadgets covered
 * in the Act-6 security slide.
 */
public class Payment {

    public enum Method { CARD, BANK_TRANSFER, VOUCHER, REFUND }

    private Method        method;
    private BigDecimal    amount;
    private String        currency;
    private LocalDateTime paidAt;

    // TODO: getters / setters / Jackson annotations
}
