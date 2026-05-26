package com.solvd.airline.xml;

import com.solvd.airline.xml.validation.XsdValidator;

import org.junit.jupiter.api.Test;
import org.xml.sax.SAXException;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Happy-path + sad-path integration tests for {@link XsdValidator}.
 *
 * <ul>
 *   <li>Valid fleet.xml passes validation against fleet.xsd.</li>
 *   <li>A document with a bad IATA code (4 letters) fails with a precise
 *       schema-rule message.</li>
 * </ul>
 */
class XsdValidatorIT {

    private static final Path FLEET_XML = Path.of("src/main/resources/xml/fleet.xml");
    private static final Path FLEET_XSD = Path.of("src/main/resources/xml/fleet.xsd");

    @Test
    void canonicalDocumentValidates() {
        assertDoesNotThrow(() -> XsdValidator.validate(FLEET_XML, FLEET_XSD),
            "fleet.xml must validate cleanly against fleet.xsd");
    }

    @Test
    void invalidIataCodeRejected() {
        // 4-letter IATA — violates the IataCode pattern [A-Z]{3}
        String bad = """
            <?xml version="1.0" encoding="UTF-8"?>
            <fleet>
              <airports>
                <airport iata="WAWW">
                  <name>x</name><city>x</city><countryCode>PL</countryCode><timezone>x</timezone>
                </airport>
              </airports>
              <aircraftModels/><aircraft/><routes/><flights/>
            </fleet>
            """;
        SAXException ex = assertThrows(SAXException.class,
            () -> XsdValidator.validate(
                new ByteArrayInputStream(bad.getBytes(StandardCharsets.UTF_8)),
                FLEET_XSD));
        assertTrue(ex.getMessage().contains("WAWW") || ex.getMessage().contains("pattern"),
            "error message should name the bad value or the pattern rule: " + ex.getMessage());
    }
}
