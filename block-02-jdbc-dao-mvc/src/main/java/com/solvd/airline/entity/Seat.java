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

    // TODO: getters / setters
}
