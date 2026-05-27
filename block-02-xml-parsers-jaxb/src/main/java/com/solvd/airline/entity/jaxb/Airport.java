package com.solvd.airline.entity.jaxb;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;

import java.util.Objects;

/**
 * JAXB binding for {@code <airport>}.
 *
 * <p>Demonstrates the attribute-vs-element rule from Act 1:
 * <ul>
 *   <li>{@code iata} → {@code @XmlAttribute} — three-character metadata key, never structured.</li>
 *   <li>{@code name}, {@code city}, {@code countryCode}, {@code timezone} → {@code @XmlElement} — document content.</li>
 * </ul>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AirportType", propOrder = {"name", "city", "countryCode", "timezone"})
public class Airport {

    @XmlAttribute(name = "iata", required = true)
    private String iata;

    @XmlElement(required = true)
    private String name;

    @XmlElement(required = true)
    private String city;

    @XmlElement(required = true)
    private String countryCode;

    @XmlElement(required = true)
    private String timezone;

    public Airport() { }

    public Airport(String iata, String name, String city, String countryCode, String timezone) {
        this.iata = iata;
        this.name = name;
        this.city = city;
        this.countryCode = countryCode;
        this.timezone = timezone;
    }

    public String getIata()        { return iata; }
    public void   setIata(String iata) { this.iata = iata; }
    public String getName()        { return name; }
    public void   setName(String name) { this.name = name; }
    public String getCity()        { return city; }
    public void   setCity(String city) { this.city = city; }
    public String getCountryCode() { return countryCode; }
    public void   setCountryCode(String countryCode) { this.countryCode = countryCode; }
    public String getTimezone()    { return timezone; }
    public void   setTimezone(String timezone) { this.timezone = timezone; }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (!(other instanceof Airport a)) return false;
        return Objects.equals(iata, a.iata)
            && Objects.equals(name, a.name)
            && Objects.equals(city, a.city)
            && Objects.equals(countryCode, a.countryCode)
            && Objects.equals(timezone, a.timezone);
    }

    @Override
    public int hashCode() {
        return Objects.hash(iata, name, city, countryCode, timezone);
    }

    @Override
    public String toString() {
        return "Airport{" + iata + " · " + name + " (" + city + ", " + countryCode + ")}";
    }
}
