
package com.itismob.s01.lagusad.khenwalter.tiktrakph_mobile.guest;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.itismob.s01.lagusad.khenwalter.tiktrakph_mobile.data.model.City;
import com.itismob.s01.lagusad.khenwalter.tiktrakph_mobile.data.model.Enforcer;
import com.itismob.s01.lagusad.khenwalter.tiktrakph_mobile.data.model.Ticket;
import com.itismob.s01.lagusad.khenwalter.tiktrakph_mobile.data.model.TicketStatus;
import com.itismob.s01.lagusad.khenwalter.tiktrakph_mobile.data.model.Violation;
import com.itismob.s01.lagusad.khenwalter.tiktrakph_mobile.data.repository.RepositoryProvider;
import com.itismob.s01.lagusad.khenwalter.tiktrakph_mobile.data.source.MockData;

import java.util.Locale;

public class TicketDetailsViewModel extends ViewModel {

    public static class TicketDetails {

        public final int ticketId;
        public final TicketStatus ticketStatus;

        public final String referenceNumber;
        public final String amountDue;
        public final String status;
        public final String violationName;
        public final String violationDescription;
        public final String plateNumber;
        public final String licenseNumber;
        public final String dateIssued;
        public final String enforcerName;
        public final String cityName;

        public TicketDetails(
                int ticketId,
                TicketStatus ticketStatus,
                String referenceNumber,
                String amountDue,
                String status,
                String violationName,
                String violationDescription,
                String plateNumber,
                String licenseNumber,
                String dateIssued,
                String enforcerName,
                String cityName
        ) {
            this.ticketId = ticketId;
            this.ticketStatus = ticketStatus;
            this.referenceNumber = referenceNumber;
            this.amountDue = amountDue;
            this.status = status;
            this.violationName = violationName;
            this.violationDescription = violationDescription;
            this.plateNumber = plateNumber;
            this.licenseNumber = licenseNumber;
            this.dateIssued = dateIssued;
            this.enforcerName = enforcerName;
            this.cityName = cityName;
        }
    }

    private final MutableLiveData<TicketDetails> ticketDetails =
            new MutableLiveData<>();

    public LiveData<TicketDetails> getTicketDetails() {
        return ticketDetails;
    }

    public void loadTicket(int ticketId) {

        Ticket ticket = RepositoryProvider
                .getTicketRepository()
                .getTicketById(ticketId);

        if (ticket == null) {
            ticketDetails.setValue(null);
            return;
        }

        Violation violation = null;

        for (Violation item : MockData.getViolations()) {
            if (item.getId() == ticket.getViolationId()) {
                violation = item;
                break;
            }
        }

        Enforcer enforcer = null;
        Integer enforcerId = ticket.getEnforcerId();

        if (enforcerId != null) {
            for (Enforcer item : MockData.getEnforcers()) {
                if (item.getId() == enforcerId) {
                    enforcer = item;
                    break;
                }
            }
        }

        City city = null;

        for (City item : MockData.getCities()) {
            if (item.getId() == ticket.getCityId()) {
                city = item;
                break;
            }
        }

        TicketStatus status = RepositoryProvider
                .getTicketRepository()
                .getTicketStatus(ticket.getId());

        String amount = String.format(
                Locale.getDefault(),
                "₱%,.2f",
                ticket.getAmountDue()
        );

        TicketDetails details = new TicketDetails(
                ticket.getId(),
                status,
                ticket.getReferenceNumber(),
                amount,
                status.name(),
                violation != null ? violation.getName() : "Unknown violation",
                violation != null ? violation.getDescription() : "No description available.",
                ticket.getPlateNumber(),
                ticket.getLicenseNumber(),
                ticket.getIssuedAt(),
                enforcer != null ? enforcer.getFullName() : "Not available",
                city != null ? city.getName() : "Not available"
        );

        ticketDetails.setValue(details);
    }
}
