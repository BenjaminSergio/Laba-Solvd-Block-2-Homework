package com.solvd.airline.entity.jackson.stubs;

import java.math.BigDecimal;

/**
 * STUB — homework task 4.
 *
 * <p>Annotate so {@code {"code": "Y", "name": "Economy", ...}} round-trips.
 * Coverage:
 *
 * <ul>
 *   <li>{@code code} → no annotation needed; field name matches JSON key.</li>
 *   <li>{@code name} → no annotation; bonus: try {@code @JsonAlias({"name",
 *       "displayName"})} so legacy data with the old key still deserialises.</li>
 *   <li>{@code priceMultiplier} → plain {@link BigDecimal}.</li>
 *   <li>{@code refundable} → plain boolean. Jackson maps Java {@code boolean}
 *       directly to JSON {@code true}/{@code false} with no extra config.</li>
 * </ul>
 */
public class FareClass {

    private String     code;
    private String     name;
    private BigDecimal priceMultiplier;
    private boolean    refundable;

    // TODO: getters / setters / Jackson annotations
}
