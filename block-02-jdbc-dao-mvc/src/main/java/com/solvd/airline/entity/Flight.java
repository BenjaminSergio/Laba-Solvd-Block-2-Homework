package com.solvd.airline.entity;

import java.time.LocalDateTime;

/**
 * Maps the {@code flights} table.
 *
 * <pre>
 * flight_id      BIGINT     PK
 * flight_number  VARCHAR(8) e.g. "LO281"
 * route_id       BIGINT     FK -> routes
 * aircraft_id    BIGINT     FK -> aircraft, NULL allowed (ON DELETE SET NULL)
 * scheduled_dep  DATETIME
 * scheduled_arr  DATETIME
 * status         ENUM('SCHEDULED','BOARDING','DEPARTED','ARRIVED','CANCELLED')
 * </pre>
 */
public class Flight extends BaseEntity {

    public enum Status { SCHEDULED, BOARDING, DEPARTED, ARRIVED, CANCELLED }

    private String        flightNumber;
    private Long          routeId;
    private Long          aircraftId;          // nullable
    private LocalDateTime scheduledDep;
    private LocalDateTime scheduledArr;
    private Status        status;

    public Flight() { }

    public String getFlightNumber()        { return flightNumber; }
    public void   setFlightNumber(String n){ this.flightNumber = n; }

    public Long   getRouteId()             { return routeId; }
    public void   setRouteId(Long r)       { this.routeId = r; }

    public Long   getAircraftId()          { return aircraftId; }
    public void   setAircraftId(Long a)    { this.aircraftId = a; }

    public LocalDateTime getScheduledDep() { return scheduledDep; }
    public void setScheduledDep(LocalDateTime t) { this.scheduledDep = t; }

    public LocalDateTime getScheduledArr() { return scheduledArr; }
    public void setScheduledArr(LocalDateTime t) { this.scheduledArr = t; }

    public Status getStatus()              { return status; }
    public void   setStatus(Status status) { this.status = status; }

    @Override
    public String toString() {
        return "Flight{" + flightNumber + " · " + scheduledDep + " · " + status + "}";
    }
}
