package com.solvd.airline.xml.sax;

import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

import java.util.List;

/**
 * STUB — homework task 4.
 *
 * <p>Parse the {@code booking.xml} document you create in homework task 2 and
 * produce a {@code List<Booking>}. Mirror {@link SaxFleetHandler}:
 *
 * <ul>
 *   <li>Override {@code startElement}, {@code characters}, {@code endElement}.</li>
 *   <li>Track which element you are currently inside ({@code <booking>} vs
 *       {@code <passenger>} vs {@code <ticket>} vs {@code <payment>}). A
 *       {@code Deque<String>} or a set of flags both work; the {@code Deque}
 *       generalises better.</li>
 *   <li>Always buffer text into a {@code StringBuilder} — never assign in
 *       {@code characters}. The parser is free to chunk.</li>
 *   <li>Read attributes inside {@code startElement} — they are not available
 *       in {@code endElement}.</li>
 * </ul>
 *
 * <p>The integration test you write asserts the parsed booking count, the
 * first booking's reference, and the first ticket's number.
 */
public class SaxBookingHandler extends DefaultHandler {

    public List<Object /* Booking */> getBookings() {
        throw new UnsupportedOperationException("Implement as part of homework task 4");
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attrs) {
        throw new UnsupportedOperationException("Implement as part of homework task 4");
    }

    @Override
    public void characters(char[] ch, int start, int length) {
        throw new UnsupportedOperationException("Implement as part of homework task 4");
    }

    @Override
    public void endElement(String uri, String localName, String qName) {
        throw new UnsupportedOperationException("Implement as part of homework task 4");
    }
}
