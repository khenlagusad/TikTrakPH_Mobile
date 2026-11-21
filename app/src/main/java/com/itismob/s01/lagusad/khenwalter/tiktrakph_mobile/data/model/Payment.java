
package com.itismob.s01.lagusad.khenwalter.tiktrakph_mobile.data.model;

import java.math.BigDecimal;

public class Payment {

    private int id;
    private int ticketId;
    private BigDecimal amountPaid;
    private String paymentDate;

    public Payment(int id, int ticketId, BigDecimal amountPaid,
                   String paymentDate) {

        this.id = id;
        this.ticketId = ticketId;
        this.amountPaid = amountPaid;
        this.paymentDate = paymentDate;
    }

    public int getId() {
        return id;
    }

    public int getTicketId() {
        return ticketId;
    }

    public BigDecimal getAmountPaid() {
        return amountPaid;
    }

    public String getPaymentDate() {
        return paymentDate;
    }
}
