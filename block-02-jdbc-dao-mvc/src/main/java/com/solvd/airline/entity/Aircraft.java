package com.solvd.airline.entity;

import java.time.LocalDate;

/**
 * Stub — homework. Maps the {@code aircraft} table.
 *
 * <pre>
 * aircraft_id   BIGINT       PK
 * tail_number   CHAR(7)      UNIQUE
 * model_id      BIGINT       FK -> aircraft_models  (ON DELETE RESTRICT)
 * in_service    BOOLEAN      default TRUE
 * delivered_on  DATE         nullable
 * </pre>
 *
 * TODO: declare fields, getters, setters following the {@link Airport}
 *       template. The id field is inherited from {@link BaseEntity}.
 */
public class Aircraft extends BaseEntity {

    private String    tailNumber;
    private Long      modelId;
    private boolean   inService;
    private LocalDate deliveredOn;

    public String getTailNumber() {
        return tailNumber;
    }

    public void setTailNumber(String tailNumber) {
        this.tailNumber = tailNumber;
    }

    public Long getModelId() {
        return modelId;
    }

    public void setModelId(Long modelId) {
        this.modelId = modelId;
    }

    public boolean isInService() {
        return inService;
    }

    public void setInService(boolean inService) {
        this.inService = inService;
    }

    public LocalDate getDeliveredOn() {
        return deliveredOn;
    }

    public void setDeliveredOn(LocalDate deliveredOn) {
        this.deliveredOn = deliveredOn;
    }

    @java.lang.Override
    public java.lang.String toString() {
        return "Aircraft{" +
                "tailNumber='" + tailNumber + '\'' +
                ", modelId=" + modelId +
                ", inService=" + inService +
                ", deliveredOn=" + deliveredOn +
                '}';
    }
}
