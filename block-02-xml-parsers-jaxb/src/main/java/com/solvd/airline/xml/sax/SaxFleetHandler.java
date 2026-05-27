package com.solvd.airline.xml.sax;

import com.solvd.airline.entity.jaxb.Airport;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * SAX reference handler — parses {@code fleet.xml} and produces a flat
 * {@code List<Airport>}. The reference implementation that students mirror
 * when filling in {@link SaxBookingHandler}.
 *
 * <p>Three patterns the homework grades on:
 * <ol>
 *   <li>Read attributes inside {@code startElement} — they vanish on
 *       {@code endElement}.</li>
 *   <li>Buffer all character content into a {@link StringBuilder} — JAXP is
 *       free to chunk text across multiple {@code characters()} callbacks.</li>
 *   <li>Dispatch buffered text in {@code endElement} via the element name.</li>
 * </ol>
 */
public class SaxFleetHandler extends DefaultHandler {

    private final List<Airport> airports = new ArrayList<>();

    private Airport       current;
    private StringBuilder buffer;
    private boolean       insideAirport;

    /** Convenience entry point — configures the factory and runs the parse. */
    public static List<Airport> parseAirports(Path xmlPath) throws Exception {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        factory.setNamespaceAware(true);
        factory.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
        factory.setFeature("http://xml.org/sax/features/external-general-entities", false);
        factory.setFeature("http://xml.org/sax/features/external-parameter-entities", false);

        SAXParser parser = factory.newSAXParser();
        SaxFleetHandler handler = new SaxFleetHandler();
        parser.parse(xmlPath.toFile(), handler);
        return handler.airports;
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attrs) {
        String name = elementName(localName, qName);
        if ("airport".equals(name)) {
            current = new Airport();
            current.setIata(attrs.getValue("iata"));
            insideAirport = true;
        }
        buffer = new StringBuilder();
    }

    @Override
    public void characters(char[] ch, int start, int length) {
        if (buffer != null) buffer.append(ch, start, length);
    }

    @Override
    public void endElement(String uri, String localName, String qName) {
        if (!insideAirport) { buffer = null; return; }
        String name = elementName(localName, qName);
        String text = (buffer == null) ? "" : buffer.toString().trim();
        switch (name) {
            case "name"        -> current.setName(text);
            case "city"        -> current.setCity(text);
            case "countryCode" -> current.setCountryCode(text);
            case "timezone"    -> current.setTimezone(text);
            case "airport"     -> { airports.add(current); current = null; insideAirport = false; }
            default            -> { /* ignore */ }
        }
        buffer = null;
    }

    /**
     * When the document has no namespace, some JAXP implementations populate
     * {@code qName} but leave {@code localName} empty even with
     * {@code namespaceAware=true}. Falling back to {@code qName} is portable.
     */
    private static String elementName(String localName, String qName) {
        return (localName == null || localName.isEmpty()) ? qName : localName;
    }

    public List<Airport> getAirports() { return airports; }
}
