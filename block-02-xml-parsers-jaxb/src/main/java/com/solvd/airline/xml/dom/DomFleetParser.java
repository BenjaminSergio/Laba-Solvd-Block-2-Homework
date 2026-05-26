package com.solvd.airline.xml.dom;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * DOM reference parser for {@code fleet.xml}. Demonstrates:
 *
 * <ul>
 *   <li>Hardened {@link DocumentBuilderFactory} (XXE-safe).</li>
 *   <li>{@code getElementsByTagName} traversal.</li>
 *   <li>Attribute read via {@link Element#getAttribute(String)}.</li>
 *   <li>Text content read via {@link Element#getTextContent()}.</li>
 * </ul>
 *
 * <p>Walks the entire {@code <fleet>} document and returns flat record lists
 * of airports + flights for assertion in the integration test.
 */
public class DomFleetParser {

    /** Parse the document and return a flat list of airport IATA codes. */
    public static List<String> parseAirportIataCodes(Path xmlPath)
            throws ParserConfigurationException, IOException, SAXException {
        Document doc = parse(xmlPath);
        NodeList nodes = doc.getElementsByTagName("airport");
        List<String> out = new ArrayList<>(nodes.getLength());
        for (int i = 0; i < nodes.getLength(); i++) {
            out.add(((Element) nodes.item(i)).getAttribute("iata"));
        }
        return out;
    }

    /** Parse the document and return a flat list of flight numbers. */
    public static List<String> parseFlightNumbers(Path xmlPath)
            throws ParserConfigurationException, IOException, SAXException {
        Document doc = parse(xmlPath);
        NodeList nodes = doc.getElementsByTagName("flight");
        List<String> out = new ArrayList<>(nodes.getLength());
        for (int i = 0; i < nodes.getLength(); i++) {
            out.add(((Element) nodes.item(i)).getAttribute("number"));
        }
        return out;
    }

    /** Parse and return the raw {@link Document} for downstream XPath usage. */
    public static Document parse(Path xmlPath)
            throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);
        factory.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
        factory.setFeature("http://xml.org/sax/features/external-general-entities", false);
        factory.setFeature("http://xml.org/sax/features/external-parameter-entities", false);
        factory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
        factory.setXIncludeAware(false);
        factory.setExpandEntityReferences(false);

        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(xmlPath.toFile());
        doc.getDocumentElement().normalize();
        return doc;
    }
}
