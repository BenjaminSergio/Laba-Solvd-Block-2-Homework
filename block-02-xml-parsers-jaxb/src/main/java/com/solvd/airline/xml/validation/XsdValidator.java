package com.solvd.airline.xml.validation;

import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * XSD validation, XXE-hardened. The {@link SchemaFactory} is configured to
 * disable external DTD and external schema resolution at construction time
 * (the secure-by-default boilerplate from Act 1 / Slide 12).
 *
 * <p>The class is stateless and thread-safe after construction.
 */
public final class XsdValidator {

    private XsdValidator() { }

    /**
     * Validate an XML file against an XSD schema file.
     *
     * @throws SAXException if the XML is invalid against the schema —
     *         the message names line, column, and rule (e.g.
     *         {@code cvc-pattern-valid: ...}).
     */
    public static void validate(Path xmlPath, Path xsdPath) throws SAXException, IOException {
        Schema schema = secureSchemaFactory().newSchema(new StreamSource(xsdPath.toFile()));
        Validator v = schema.newValidator();
        v.validate(new StreamSource(xmlPath.toFile()));
    }

    /**
     * Validate XML bytes (from an {@link InputStream}) against an XSD file.
     * Used by the XXE-rejection test to feed a hand-crafted payload.
     */
    public static void validate(InputStream xmlStream, Path xsdPath) throws SAXException, IOException {
        Schema schema = secureSchemaFactory().newSchema(new StreamSource(xsdPath.toFile()));
        Validator v = schema.newValidator();
        v.validate(new StreamSource(xmlStream));
    }

    /**
     * Convenience: validate an XML file against an XSD shipped on the classpath
     * (under {@code src/main/resources/}).
     */
    public static void validateClasspath(Path xmlPath, String xsdResource) throws SAXException, IOException {
        try (InputStream in = XsdValidator.class.getClassLoader().getResourceAsStream(xsdResource)) {
            if (in == null) throw new IOException("XSD resource not found: " + xsdResource);
            Schema schema = secureSchemaFactory().newSchema(new StreamSource(in));
            Validator v = schema.newValidator();
            try (InputStream xml = Files.newInputStream(xmlPath)) {
                v.validate(new StreamSource(xml));
            }
        }
    }

    private static SchemaFactory secureSchemaFactory() throws SAXException {
        SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        factory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
        factory.setProperty(XMLConstants.ACCESS_EXTERNAL_DTD,    "");
        factory.setProperty(XMLConstants.ACCESS_EXTERNAL_SCHEMA, "");
        return factory;
    }
}
