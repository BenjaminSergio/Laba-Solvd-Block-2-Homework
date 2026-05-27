package com.solvd.airline.entity.jaxb.stubs;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * STUB — homework task 5.
 *
 * <p>Annotate so {@code <payment method="CARD"> ... </payment>} round-trips.
 *
 * <ul>
 *   <li>{@code method} → {@code @XmlAttribute}. The {@link Method} enum is
 *       UPPERCASE — matches the XSD enumeration without {@code @XmlEnumValue}.</li>
 *   <li>{@code amount} → {@code @XmlElement} ({@code BigDecimal} → {@code xs:decimal}).</li>
 *   <li>{@code currency} → {@code @XmlElement} (ISO-4217 three-letter string).</li>
 *   <li>{@code paidAt} → {@code @XmlElement} + {@code XmlAdapter} for {@link LocalDateTime}.</li>
 * </ul>
 */
public class Payment {

    public enum Method { CARD, BANK_TRANSFER, VOUCHER, REFUND }

    private Method        method;
    private BigDecimal    amount;
    private String        currency;
    private LocalDateTime paidAt;

    // TODO: getters / setters / annotations
}
