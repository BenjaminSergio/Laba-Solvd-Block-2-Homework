package com.solvd.airline.entity.jaxb.stubs;

import java.math.BigDecimal;

/**
 * STUB — homework task 5.
 *
 * <p>Annotate so {@code <fareClass code="Y"> ... </fareClass>} round-trips.
 *
 * <ul>
 *   <li>{@code code} → {@code @XmlAttribute} (single uppercase letter — Y/W/C/F).</li>
 *   <li>{@code name} → {@code @XmlElement}.</li>
 *   <li>{@code priceMultiplier} → {@code @XmlElement} ({@code BigDecimal}).</li>
 *   <li>{@code refundable} → {@code @XmlElement} (boolean → {@code xs:boolean}).</li>
 * </ul>
 */
public class FareClass {

    private String     code;
    private String     name;
    private BigDecimal priceMultiplier;
    private boolean    refundable;

    // TODO: getters / setters / annotations
}
