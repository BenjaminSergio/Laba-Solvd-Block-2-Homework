package com.solvd.airline.xml.stax;

import com.solvd.airline.entity.jaxb.Airport;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * StAX reference reader (cursor API). Streams {@code fleet.xml} and emits a
 * flat {@code List<Airport>}. Counterpart to {@link com.solvd.airline.xml.sax.SaxFleetHandler}
 * — same output, different control flow.
 *
 * <p>The factory is hardened against XXE via {@link XMLInputFactory#SUPPORT_DTD}
 * and {@link XMLInputFactory#IS_SUPPORTING_EXTERNAL_ENTITIES}.
 */
public class StaxFleetReader {

    public static List<Airport> parseAirports(Path xmlPath) throws IOException, XMLStreamException {
        XMLInputFactory factory = XMLInputFactory.newInstance();
        factory.setProperty(XMLInputFactory.SUPPORT_DTD, false);
        factory.setProperty(XMLInputFactory.IS_SUPPORTING_EXTERNAL_ENTITIES, false);

        List<Airport> out = new ArrayList<>();
        try (InputStream in = Files.newInputStream(xmlPath)) {
            XMLStreamReader r = factory.createXMLStreamReader(in);
            Airport current = null;
            String  currentElement = null;
            try {
                while (r.hasNext()) {
                    int event = r.next();
                    switch (event) {
                        case XMLStreamConstants.START_ELEMENT -> {
                            currentElement = r.getLocalName();
                            if ("airport".equals(currentElement)) {
                                current = new Airport();
                                current.setIata(r.getAttributeValue(null, "iata"));
                            }
                        }
                        case XMLStreamConstants.CHARACTERS -> {
                            if (current != null && currentElement != null && !r.isWhiteSpace()) {
                                String text = r.getText();
                                switch (currentElement) {
                                    case "name"        -> current.setName(text);
                                    case "city"        -> current.setCity(text);
                                    case "countryCode" -> current.setCountryCode(text);
                                    case "timezone"    -> current.setTimezone(text);
                                    default            -> { /* ignore */ }
                                }
                            }
                        }
                        case XMLStreamConstants.END_ELEMENT -> {
                            if ("airport".equals(r.getLocalName()) && current != null) {
                                out.add(current);
                                current = null;
                            }
                            currentElement = null;
                        }
                        default -> { /* ignore */ }
                    }
                }
            } finally {
                r.close();
            }
        }
        return out;
    }
}
