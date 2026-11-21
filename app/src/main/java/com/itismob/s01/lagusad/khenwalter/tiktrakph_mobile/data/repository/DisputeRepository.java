
package com.itismob.s01.lagusad.khenwalter.tiktrakph_mobile.data.repository;

import com.itismob.s01.lagusad.khenwalter.tiktrakph_mobile.data.model.Dispute;
import com.itismob.s01.lagusad.khenwalter.tiktrakph_mobile.data.model.Ticket;
import com.itismob.s01.lagusad.khenwalter.tiktrakph_mobile.data.model.TicketStatus;
import com.itismob.s01.lagusad.khenwalter.tiktrakph_mobile.data.model.User;
import com.itismob.s01.lagusad.khenwalter.tiktrakph_mobile.data.source.MockData;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class DisputeRepository {

    private final TicketRepository ticketRepository;
    private final UserRepository userRepository;

    public DisputeRepository(TicketRepository ticketRepository,
                             UserRepository userRepository) {

        this.ticketRepository = ticketRepository;
        this.userRepository = userRepository;
    }
    public Dispute getDisputeByTicketId(int ticketId) {

        for (Dispute dispute : MockData.getDisputes()) {

            if (dispute.getTicketId() == ticketId) {
                return dispute;
            }
        }

        return null;
    }

    public List<Dispute> getDisputesByUserId(int userId) {

        List<Dispute> result = new ArrayList<>();

        for (Dispute dispute : MockData.getDisputes()) {

            if (dispute.getUserId() == userId) {
                result.add(dispute);
            }
        }

        return result;
    }

    public Dispute submitDispute(int ticketId, int userId,
                                 String title, String explanation) {

        Ticket ticket = ticketRepository.getTicketById(ticketId);
        User user = userRepository.getUserById(userId);

        if (ticket == null || user == null) {
            return null;
        }

        // Verify that the ticket belongs to the submitting user.
        if (!ticket.getLicenseNumber().equalsIgnoreCase(user.getLicenseNumber())) {
            return null;
        }

        // A dispute can only be filed for an Unpaid ticket.
        if (ticketRepository.getTicketStatus(ticketId) != TicketStatus.UNPAID) {
            return null;
        }

        // Only one dispute is allowed per ticket.
        if (getDisputeByTicketId(ticketId) != null) {
            return null;
        }

        if (title == null || title.trim().isEmpty()
                || explanation == null || explanation.trim().isEmpty()) {
            return null;
        }

        int nextId = 1;

        for (Dispute dispute : MockData.getDisputes()) {

            if (dispute.getId() >= nextId) {
                nextId = dispute.getId() + 1;
            }
        }

        String submittedAt = getCurrentDateTime();

        Dispute newDispute = new Dispute(
                nextId,
                ticketId,
                userId,
                title.trim(),
                explanation.trim(),
                Dispute.PENDING,
                null,
                null,
                submittedAt,
                null
        );

        MockData.getDisputes().add(newDispute);

        return newDispute;
    }

    public boolean reviewDispute(int ticketId, String status,
                                 Integer adminId, String adminNotes) {

        Dispute dispute = getDisputeByTicketId(ticketId);

        if (dispute == null) {
            return false;
        }

        // Only pending disputes can be reviewed.
        if (!Dispute.PENDING.equalsIgnoreCase(dispute.getStatus())) {
            return false;
        }

        // A review can only resolve or reject a dispute.
        if (!Dispute.RESOLVED.equalsIgnoreCase(status)
                && !Dispute.REJECTED.equalsIgnoreCase(status)) {
            return false;
        }

        dispute.setStatus(status.toLowerCase(Locale.ROOT));
        dispute.setAdminId(adminId);
        dispute.setAdminNotes(adminNotes);
        dispute.setReviewedAt(getCurrentDateTime());

        return true;
    }

    private String getCurrentDateTime() {

        return new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss",
                Locale.getDefault()
        ).format(new Date());
    }
}
