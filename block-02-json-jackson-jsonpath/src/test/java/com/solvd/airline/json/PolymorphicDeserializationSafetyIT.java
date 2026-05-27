package com.solvd.airline.json;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.InvalidTypeIdException;
import com.solvd.airline.json.core.JacksonMapper;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Security integration test — proves that Jackson's safe-by-default behaviour
 * around polymorphic deserialization is real.
 *
 * <p>The XML lecture's parallel is {@code XsdValidatorXxeIT}. Here we cover
 * the Jackson <strong>CVE-2017-7525</strong> family — the bug where a server
 * deserialising JSON into an {@code Object} field with default typing enabled
 * could be coerced into instantiating an arbitrary class (e.g.
 * {@code JdbcRowSetImpl}) and triggering remote code execution.
 *
 * <p>Three assertions:
 * <ol>
 *   <li>The reference {@link JacksonMapper#instance()} does <strong>NOT</strong>
 *       enable default typing. Reading polymorphic-typed JSON without
 *       {@code @JsonTypeInfo} on the target type fails — no gadget is reachable.</li>
 *   <li>{@code @JsonTypeInfo(use = Id.NAME)} + {@code @JsonSubTypes} works for
 *       the <em>allowed</em> subtypes, proving polymorphism is achievable
 *       without unlocking the gadget door.</li>
 *   <li>An unknown subtype name is rejected by Jackson — the deny-list is
 *       explicit, not best-effort.</li>
 * </ol>
 */
class PolymorphicDeserializationSafetyIT {

    /**
     * Safe polymorphism — discriminator is a known string token, allowed
     * subtypes are listed by the application. No class-name lookup happens
     * at deserialization time.
     */
    @JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "method")
    @JsonSubTypes({
        @JsonSubTypes.Type(value = CardPayment.class,         name = "CARD"),
        @JsonSubTypes.Type(value = BankTransferPayment.class, name = "BANK_TRANSFER")
    })
    abstract static class Payment {
        public String currency;
    }

    static class CardPayment extends Payment {
        public String cardLast4;
    }

    static class BankTransferPayment extends Payment {
        public String accountIban;
    }

    @Test
    void allowedSubtypeRoundTrips() throws Exception {
        ObjectMapper mapper = JacksonMapper.instance();

        String json = """
            {"method": "CARD", "currency": "EUR", "cardLast4": "4242"}
            """;
        Payment p = mapper.readValue(json, Payment.class);
        assertInstanceOf(CardPayment.class, p);
        assertEquals("EUR", p.currency);
        assertEquals("4242", ((CardPayment) p).cardLast4);
    }

    @Test
    void unknownSubtypeRejected() {
        ObjectMapper mapper = JacksonMapper.instance();

        // An attacker tries a discriminator value that is NOT in @JsonSubTypes.
        String hostile = """
            {"method": "EVIL", "currency": "EUR"}
            """;
        assertThrows(InvalidTypeIdException.class,
            () -> mapper.readValue(hostile, Payment.class),
            "Unknown discriminator must be rejected — the @JsonSubTypes deny-list is explicit");
    }

    @Test
    void defaultTypingIsNotEnabled() throws Exception {
        ObjectMapper mapper = JacksonMapper.instance();

        // Without default typing, a payload trying to specify an arbitrary
        // @class field on an Object reference cannot influence which concrete
        // class is instantiated. The mapper falls back to LinkedHashMap.
        String classNameAttack = """
            {"@class": "java.lang.Runtime", "command": "calc.exe"}
            """;
        Object o = mapper.readValue(classNameAttack, Object.class);
        assertNotNull(o);
        assertTrue(o instanceof java.util.Map,
            "Without default typing, the parser materialises a generic Map — "
            + "not the attacker-named class. Concrete instance: " + o.getClass());
    }
}
