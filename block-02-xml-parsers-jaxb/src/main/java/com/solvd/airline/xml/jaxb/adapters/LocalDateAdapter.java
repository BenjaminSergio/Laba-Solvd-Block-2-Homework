package com.solvd.airline.xml.jaxb.adapters;

import jakarta.xml.bind.annotation.adapters.XmlAdapter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * JAXB adapter for {@link LocalDate} ↔ {@code xs:date} string.
 *
 * <p>Hook via {@code @XmlJavaTypeAdapter(LocalDateAdapter.class)} on the field.
 * Native JAXB has no mapping for {@code java.time.*} — this five-line adapter
 * is the bridge.
 */
public class LocalDateAdapter extends XmlAdapter<String, LocalDate> {

    @Override
    public LocalDate unmarshal(String s) {
        return (s == null || s.isEmpty()) ? null : LocalDate.parse(s);
    }

    @Override
    public String marshal(LocalDate d) {
        return (d == null) ? null : d.format(DateTimeFormatter.ISO_LOCAL_DATE);
    }
}
