package com.solvd.airline.xml;

import com.solvd.airline.xml.validation.XsdValidator;

import org.junit.jupiter.api.Test;
import org.xml.sax.SAXException;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Security integration test — proves the XXE hardening on
 * {@link XsdValidator} is real, not theatre.
 *
 * <p>The payload embeds a {@code <!DOCTYPE>} block with an external entity
 * pointing at a local file. With {@code disallow-doctype-decl} and
 * {@code ACCESS_EXTERNAL_DTD=""}, the validator must reject the document
 * before the entity is ever dereferenced.
 */
class XsdValidatorXxeIT {

    private static final Path FLEET_XSD = Path.of("src/main/resources/xml/fleet.xsd");

    @Test
    void xxePayloadRejected() {
        String xxe = """
            <?xml version="1.0" encoding="UTF-8"?>
            <!DOCTYPE foo [
              <!ENTITY xxe SYSTEM "file:///etc/passwd">
            ]>
            <fleet>
              <airports>
                <airport iata="WAW">
                  <name>&xxe;</name><city>x</city><countryCode>PL</countryCode><timezone>x</timezone>
                </airport>
              </airports>
              <aircraftModels/><aircraft/><routes/><flights/>
            </fleet>
            """;

        // Either a SAXException naming DOCTYPE / DTD, or any failure that
        // stops the parse before file resolution. The point is: no /etc/passwd
        // content ends up in the parser's text buffer.
        Exception ex = assertThrows(Exception.class,
            () -> XsdValidator.validate(
                new ByteArrayInputStream(xxe.getBytes(StandardCharsets.UTF_8)),
                FLEET_XSD),
            "XXE payload must be rejected by the hardened validator");

        // The most common rejection message names DOCTYPE explicitly.
        String msg = String.valueOf(ex.getMessage()).toLowerCase();
        assertTrue(msg.contains("doctype") || msg.contains("dtd") || msg.contains("external"),
            "rejection should name the DTD / DOCTYPE / external-entity feature: " + ex.getMessage());
    }
}
