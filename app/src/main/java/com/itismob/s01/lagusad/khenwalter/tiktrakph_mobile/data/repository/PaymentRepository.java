
package com.itismob.s01.lagusad.khenwalter.tiktrakph_mobile.data.repository;

import com.itismob.s01.lagusad.khenwalter.tiktrakph_mobile.data.model.Payment;
import com.itismob.s01.lagusad.khenwalter.tiktrakph_mobile.data.model.Ticket;
import com.itismob.s01.lagusad.khenwalter.tiktrakph_mobile.data.model.TicketStatus;
import com.itismob.s01.lagusad.khenwalter.tiktrakph_mobile.data.source.MockData;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class PaymentRepository {

    private final TicketRepository ticketRepository;

    public PaymentRepository(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    public Payment getPaymentByTicketId(int ticketId) {

        for (Payment payment : MockData.getPayments()) {

            if (payment.getTicketId() == ticketId) {
                return payment;
            }
        }

        return null;
    }

    public boolean hasPayment(int ticketId) {
        return getPaymentByTicketId(ticketId) != null;
    }

    public Payment createPayment(int ticketId) {

        Ticket ticket = ticketRepository.getTicketById(ticketId);

        if (ticket == null) {
            return null;
        }

        TicketStatus status = ticketRepository.getTicketStatus(ticketId);

        // Only Unpaid and Rejected tickets can be paid.
        if (status != TicketStatus.UNPAID
                && status != TicketStatus.REJECTED) {
            return null;
        }

        // Prevent duplicate payment records.
        if (hasPayment(ticketId)) {
            return null;
        }

        int nextId = 1;

        for (Payment payment : MockData.getPayments()) {

            if (payment.getId() >= nextId) {
                nextId = payment.getId() + 1;
            }
        }

        String paymentDate = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss",
                Locale.getDefault()
        ).format(new Date());

        Payment newPayment = new Payment(
                nextId,
                ticketId,
                ticket.getAmountDue(),
                paymentDate
        );

        MockData.getPayments().add(newPayment);

        return newPayment;
    }
}
