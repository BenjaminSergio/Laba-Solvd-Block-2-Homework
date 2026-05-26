package com.solvd.airline.xml.jaxb.adapters;

import jakarta.xml.bind.annotation.adapters.XmlAdapter;

import java.time.Instant;

/**
 * JAXB adapter for {@link Instant} ↔ {@code xs:dateTime} string in UTC
 * (the trailing {@code Z} disambiguates from {@code LocalDateTime}).
 */
public class InstantAdapter extends XmlAdapter<String, Instant> {

    @Override
    public Instant unmarshal(String s) {
        return (s == null || s.isEmpty()) ? null : Instant.parse(s);
    }

    @Override
    public String marshal(Instant i) {
        return (i == null) ? null : i.toString();
    }
}
