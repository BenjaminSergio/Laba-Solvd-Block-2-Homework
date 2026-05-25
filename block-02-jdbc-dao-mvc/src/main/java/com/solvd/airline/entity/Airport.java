package com.solvd.airline.entity;

/**
 * Maps the {@code airports} table.
 *
 * <pre>
 * airport_id    BIGINT       PK / surrogate
 * iata          CHAR(3)      natural unique key, e.g. "WAW"
 * name          VARCHAR(120)
 * city          VARCHAR(80)
 * country_code  CHAR(2)      ISO-3166-1 alpha-2
 * timezone      VARCHAR(40)  e.g. "Europe/Warsaw"
 * </pre>
 */
public class Airport extends BaseEntity {

    private String iata;
    private String name;
    private String city;
    private String countryCode;
    private String timezone;

    public Airport() { }

    public Airport(Long id, String iata, String name, String city,
                   String countryCode, String timezone) {
        this.id = id;
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
    public String toString() {
        return "Airport{" + iata + " · " + name + " (" + city + ", " + countryCode + ")}";
    }
}
