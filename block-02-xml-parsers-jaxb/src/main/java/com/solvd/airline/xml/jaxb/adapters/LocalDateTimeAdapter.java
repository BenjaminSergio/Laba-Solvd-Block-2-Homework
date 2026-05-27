package com.solvd.airline.xml.jaxb.adapters;

import jakarta.xml.bind.annotation.adapters.XmlAdapter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * JAXB adapter for {@link LocalDateTime} ↔ {@code xs:dateTime} string
 * (without timezone offset — local time in some understood zone).
 */
public class LocalDateTimeAdapter extends XmlAdapter<String, LocalDateTime> {

    @Override
    public LocalDateTime unmarshal(String s) {
        return (s == null || s.isEmpty()) ? null : LocalDateTime.parse(s);
    }

    @Override
    public String marshal(LocalDateTime t) {
        return (t == null) ? null : t.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
    }
}
