package com.solvd.airline.app;

import com.solvd.airline.entity.jaxb.Airport;
import com.solvd.airline.entity.jaxb.Fleet;
import com.solvd.airline.xml.dom.DomFleetParser;
import com.solvd.airline.xml.jaxb.FleetMarshaller;
import com.solvd.airline.xml.sax.SaxFleetHandler;
import com.solvd.airline.xml.stax.StaxFleetReader;
import com.solvd.airline.xml.validation.XsdValidator;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import java.nio.file.Path;
import java.util.List;

/**
 * CLI demo that exercises every component shipped in this homework starter.
 *
 * <p>Run with:
 * <pre>mvn -q exec:java -Dexec.mainClass="com.solvd.airline.app.Main"</pre>
 *
 * <p>Validates {@code fleet.xml}, parses it three ways, runs one XPath query,
 * then unmarshals and re-marshals via JAXB and prints the round-trip head.
 */
public class Main {

    public static void main(String[] args) throws Exception {
        Path xml = Path.of("src/main/resources/xml/fleet.xml");
        Path xsd = Path.of("src/main/resources/xml/fleet.xsd");

        System.out.println("─── 1. XSD validation ───");
        try {
            XsdValidator.validate(xml, xsd);
            System.out.println("  ✓ fleet.xml is valid against fleet.xsd");
        } catch (SAXException e) {
            System.out.println("  ✗ validation failed: " + e.getMessage());
            return;
        }

        System.out.println("\n─── 2. DOM parse ───");
        List<String> domAirports = DomFleetParser.parseAirportIataCodes(xml);
        System.out.println("  airports (DOM) = " + domAirports);
        List<String> domFlights = DomFleetParser.parseFlightNumbers(xml);
        System.out.println("  flights  (DOM) = " + domFlights);

        System.out.println("\n─── 3. SAX parse ───");
        List<Airport> saxAirports = SaxFleetHandler.parseAirports(xml);
        System.out.println("  airports (SAX) = " + saxAirports);

        System.out.println("\n─── 4. StAX parse ───");
        List<Airport> staxAirports = StaxFleetReader.parseAirports(xml);
        System.out.println("  airports (StAX) = " + staxAirports);

        System.out.println("\n─── 5. XPath query ───");
        Document doc = DomFleetParser.parse(xml);
        XPath xp = XPathFactory.newInstance().newXPath();
        String warsawName = (String) xp.evaluate(
            "//airport[@iata='WAW']/name/text()", doc, XPathConstants.STRING);
        NodeList scheduled = (NodeList) xp.evaluate(
            "//flight[status='SCHEDULED']", doc, XPathConstants.NODESET);
        System.out.println("  //airport[@iata='WAW']/name = \"" + warsawName + "\"");
        System.out.println("  //flight[status='SCHEDULED'] → " + scheduled.getLength() + " element(s)");

        System.out.println("\n─── 6. JAXB unmarshal + marshal round-trip ───");
        Fleet fleet = FleetMarshaller.unmarshal(xml);
        System.out.println("  unmarshalled: " + fleet);
        String rendered = FleetMarshaller.marshalToString(fleet);
        System.out.println("  marshalled head: "
            + rendered.lines().limit(3).reduce("", (a, b) -> a + "\n    " + b));

        System.out.println("\n  ✓ all six steps completed");
    }
}
