package com.solvd.airline.entity;

/**
 * Stub — homework. Maps the {@code seats} table.
 *
 * <pre>
 * seat_id        BIGINT       PK
 * aircraft_id    BIGINT       FK -> aircraft
 * fare_class_id  BIGINT       FK -> fare_classes
 * seat_label     VARCHAR(4)   "12A"
 * is_window      BOOLEAN
 * is_exit_row    BOOLEAN
 * UNIQUE (aircraft_id, seat_label)
 * </pre>
 *
 * TODO: fields, getters, setters.
 */
public class Seat extends BaseEntity {

    private Long    aircraftId;
    private Long    fareClassId;
    private String  seatLabel;
    private boolean window;
    private boolean exitRow;

    public Long getAircraftId() {
        return aircraftId;
    }

    public void setAircraftId(Long aircraftId) {
        this.aircraftId = aircraftId;
    }

    public Long getFareClassId() {
        return fareClassId;
    }

    public void setFareClassId(Long fareClassId) {
        this.fareClassId = fareClassId;
    }

    public String getSeatLabel() {
        return seatLabel;
    }

    public void setSeatLabel(String seatLabel) {
        this.seatLabel = seatLabel;
    }

    public boolean isWindow() {
        return window;
    }

    public void setWindow(boolean window) {
        this.window = window;
    }

    public boolean isExitRow() {
        return exitRow;
    }

    public void setExitRow(boolean exitRow) {
        this.exitRow = exitRow;
    }

    @java.lang.Override
    public java.lang.String toString() {
        return "Seat{" +
                "aircraftId=" + aircraftId +
                ", fareClassId=" + fareClassId +
                ", seatLabel='" + seatLabel + '\'' +
                ", window=" + window +
                ", exitRow=" + exitRow +
                '}';
    }
}
