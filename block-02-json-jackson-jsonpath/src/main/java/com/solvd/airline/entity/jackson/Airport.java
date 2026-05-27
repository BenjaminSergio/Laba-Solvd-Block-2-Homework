package com.solvd.airline.entity.jackson;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.Objects;

/**
 * Jackson binding for an Airport.
 *
 * <p>JSON has no attribute-vs-element distinction — every value is a JSON key.
 * The JAXB sibling marks {@code iata} as {@code @XmlAttribute}; here it is a
 * plain property. The five fields all serialise as JSON keys with the same
 * names as the Java fields (no {@code @JsonProperty} needed because the field
 * names already match the wire format).
 *
 * <p>{@code @JsonPropertyOrder} is the JSON parallel of JAXB's
 * {@code @XmlType(propOrder=...)} — controls serialisation key order.
 */
@JsonPropertyOrder({"iata", "name", "city", "countryCode", "timezone"})
public class Airport {

    private String iata;
    private String name;
    private String city;
    private String countryCode;
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
