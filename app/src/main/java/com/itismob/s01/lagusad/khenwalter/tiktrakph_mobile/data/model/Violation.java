
package com.itismob.s01.lagusad.khenwalter.tiktrakph_mobile.data.model;

import java.math.BigDecimal;

public class Violation {

    private int id;
    private String name;
    private String description;
    private BigDecimal currentFineAmount;

    public Violation(int id, String name, String description,
                     BigDecimal currentFineAmount) {

        this.id = id;
        this.name = name;
        this.description = description;
        this.currentFineAmount = currentFineAmount;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public BigDecimal getCurrentFineAmount() {
        return currentFineAmount;
    }
}
