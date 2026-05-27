package com.solvd.airline.entity;

/**
 * Stub — homework. Maps the {@code seats} table.
 *
 * <pre>
 * seat_id      BIGINT      PK
 * aircraft_id  BIGINT      FK -> aircraft
 * seat_label   VARCHAR(4)  "12A", "23F"
 * is_exit_row  BOOLEAN
 * UNIQUE (aircraft_id, seat_label)
 * </pre>
 *
 * TODO: copy from your Lecture 03 entity.
 */
public class Seat extends BaseEntity {

    private Long    aircraftId;
    private String  seatLabel;
    private Boolean isExitRow;

    public Long getAircraftId()                   { return aircraftId; }
    public void setAircraftId(Long a)             { this.aircraftId = a; }

    public String getSeatLabel()                  { return seatLabel; }
    public void   setSeatLabel(String s)          { this.seatLabel = s; }

    public Boolean getIsExitRow()                 { return isExitRow; }
    public void    setIsExitRow(Boolean b)        { this.isExitRow = b; }
}
