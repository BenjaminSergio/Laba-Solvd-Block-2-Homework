package com.solvd.airline.xml.xpath;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

/**
 * STUB — homework. Write five XPath queries against {@code fleet.xml}.
 *
 * <p>The reference {@link com.solvd.airline.xml.dom.DomFleetParser#parse(java.nio.file.Path)}
 * gives you a {@link Document}. Use {@link XPathFactory#newInstance()} to
 * obtain an {@link XPath}, then evaluate each expression against the document.
 *
 * <p>The slide-21 examples are your model. Each query returns a different
 * typed value; the test asserts on the expected shape.
 */
public class XPathQueries {

    private final Document doc;
    private final XPath    xpath = XPathFactory.newInstance().newXPath();

    public XPathQueries(Document doc) {
        this.doc = doc;
    }

    /**
     * Query 1: return the {@code name} text of the airport with the given IATA code.
     * Expression hint: {@code //airport[@iata=$IATA]/name/text()}.
     */
    public String airportName(String iata) throws XPathExpressionException {
        throw new UnsupportedOperationException("Implement as part of the XPath homework");
    }

    /**
     * Query 2: return every {@code <flight>} element with the given status.
     * Expression hint: {@code //flight[status=$STATUS]}.
     */
    public NodeList flightsWithStatus(String status) throws XPathExpressionException {
        throw new UnsupportedOperationException("Implement as part of the XPath homework");
    }

    /**
     * Query 3: count of flights scheduled to depart on or after the given
     * ISO-8601 local date-time string (lexical compare on ISO-8601).
     * Expression hint: {@code count(//flight[scheduledDep >= $T])}.
     */
    public int countFlightsDepartingFrom(String isoLocalDateTime) throws XPathExpressionException {
        throw new UnsupportedOperationException("Implement as part of the XPath homework");
    }

    /**
     * Query 4: return the {@code <aircraftUnit>} element with the given tail number.
     * Expression hint: {@code //aircraftUnit[@tailNumber=$T]}.
     */
    public Element aircraftByTail(String tailNumber) throws XPathExpressionException {
        throw new UnsupportedOperationException("Implement as part of the XPath homework");
    }

    /**
     * Query 5: return the count of airports in the given ISO-3166-1 country code.
     * Expression hint: {@code count(//airport[countryCode=$CC])}.
     */
    public int countAirportsInCountry(String countryCode) throws XPathExpressionException {
        throw new UnsupportedOperationException("Implement as part of the XPath homework");
    }
}
