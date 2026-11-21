package com.itismob.s01.lagusad.khenwalter.tiktrakph_mobile.data.repository;

import com.itismob.s01.lagusad.khenwalter.tiktrakph_mobile.data.model.Dispute;
import com.itismob.s01.lagusad.khenwalter.tiktrakph_mobile.data.model.Payment;
import com.itismob.s01.lagusad.khenwalter.tiktrakph_mobile.data.model.Ticket;
import com.itismob.s01.lagusad.khenwalter.tiktrakph_mobile.data.model.TicketStatus;
import com.itismob.s01.lagusad.khenwalter.tiktrakph_mobile.data.source.MockData;

import java.util.ArrayList;
import java.util.List;

public class TicketRepository {

    public List<Ticket> getAllTickets() {
        return MockData.getTickets();
    }

    public Ticket getTicketById(int ticketId) {

        for (Ticket ticket : MockData.getTickets()) {
            if (ticket.getId() == ticketId) {
                return ticket;
            }
        }

        return null;
    }

    public List<Ticket> getTicketsByLicenseNumber(String licenseNumber) {

        List<Ticket> result = new ArrayList<>();

        for (Ticket ticket : MockData.getTickets()) {

            if (ticket.getLicenseNumber().equalsIgnoreCase(licenseNumber)) {
                result.add(ticket);
            }
        }

        return result;
    }

    public List<Ticket> getTicketsByPlateNumber(String plateNumber) {

        List<Ticket> result = new ArrayList<>();

        for (Ticket ticket : MockData.getTickets()) {

            if (ticket.getPlateNumber().equalsIgnoreCase(plateNumber)) {
                result.add(ticket);
            }
        }

        return result;
    }

    public TicketStatus getTicketStatus(int ticketId) {

        // Payment takes priority over dispute status.
        for (Payment payment : MockData.getPayments()) {

            if (payment.getTicketId() == ticketId) {
                return TicketStatus.PAID;
            }
        }

        // If there is no payment, check the dispute record.
        for (Dispute dispute : MockData.getDisputes()) {

            if (dispute.getTicketId() == ticketId) {

                if (Dispute.PENDING.equalsIgnoreCase(dispute.getStatus())) {
                    return TicketStatus.PENDING;
                }

                if (Dispute.REJECTED.equalsIgnoreCase(dispute.getStatus())) {
                    return TicketStatus.REJECTED;
                }

                if (Dispute.RESOLVED.equalsIgnoreCase(dispute.getStatus())) {
                    return TicketStatus.RESOLVED;
                }
            }
        }

        // No payment and no dispute mean the ticket is unpaid.
        return TicketStatus.UNPAID;
    }
}
