
package com.itismob.s01.lagusad.khenwalter.tiktrakph_mobile.data.model;

import java.math.BigDecimal;

public class Ticket {

    private int id;
    private String referenceNumber;
    private BigDecimal amountDue;
    private String plateNumber;
    private String licenseNumber;
    private int violationId;
    private Integer enforcerId;
    private int cityId;
    private String issuedAt;

    public Ticket(int id, String referenceNumber, BigDecimal amountDue,
                  String plateNumber, String licenseNumber,
                  int violationId, Integer enforcerId,
                  int cityId, String issuedAt) {

        this.id = id;
        this.referenceNumber = referenceNumber;
        this.amountDue = amountDue;
        this.plateNumber = plateNumber;
        this.licenseNumber = licenseNumber;
        this.violationId = violationId;
        this.enforcerId = enforcerId;
        this.cityId = cityId;
        this.issuedAt = issuedAt;
    }

    public int getId() {
        return id;
    }

    public String getReferenceNumber() {
        return referenceNumber;
    }

    public BigDecimal getAmountDue() {
        return amountDue;
    }

    public String getPlateNumber() {
        return plateNumber;
    }

    public String getLicenseNumber() {
        return licenseNumber;
    }

    public int getViolationId() {
        return violationId;
    }

    public Integer getEnforcerId() {
        return enforcerId;
    }

    public int getCityId() {
        return cityId;
    }

    public String getIssuedAt() {
        return issuedAt;
    }
}
