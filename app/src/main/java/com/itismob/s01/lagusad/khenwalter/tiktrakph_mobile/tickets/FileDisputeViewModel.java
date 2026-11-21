
package com.itismob.s01.lagusad.khenwalter.tiktrakph_mobile.tickets;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.itismob.s01.lagusad.khenwalter.tiktrakph_mobile.data.model.Ticket;
import com.itismob.s01.lagusad.khenwalter.tiktrakph_mobile.data.model.TicketStatus;
import com.itismob.s01.lagusad.khenwalter.tiktrakph_mobile.data.model.User;
import com.itismob.s01.lagusad.khenwalter.tiktrakph_mobile.data.repository.RepositoryProvider;
import com.itismob.s01.lagusad.khenwalter.tiktrakph_mobile.data.session.SessionManager;

public class FileDisputeViewModel extends ViewModel {

    private final MutableLiveData<String> ticketReference =
            new MutableLiveData<>();

    private final MutableLiveData<Boolean> disputeSuccess =
            new MutableLiveData<>();

    private final MutableLiveData<String> disputeError =
            new MutableLiveData<>();

    public LiveData<String> getTicketReference() {
        return ticketReference;
    }

    public LiveData<Boolean> getDisputeSuccess() {
        return disputeSuccess;
    }

    public LiveData<String> getDisputeError() {
        return disputeError;
    }

    public void loadTicket(int ticketId) {

        Ticket ticket = RepositoryProvider
                .getTicketRepository()
                .getTicketById(ticketId);

        if (ticket == null) {
            disputeError.setValue("Ticket could not be found.");
            return;
        }

        TicketStatus status = RepositoryProvider
                .getTicketRepository()
                .getTicketStatus(ticketId);

        if (status != TicketStatus.UNPAID) {
            disputeError.setValue(
                    "Only unpaid tickets can be disputed."
            );
            return;
        }

        User currentUser = SessionManager
                .getInstance()
                .getCurrentUser();

        if (currentUser == null) {
            disputeError.setValue(
                    "Please log in before filing a dispute."
            );
            return;
        }

        ticketReference.setValue(ticket.getReferenceNumber());
    }

    public void submitDispute(
            int ticketId,
            String title,
            String explanation
    ) {

        disputeError.setValue(null);
        disputeSuccess.setValue(false);

        if (title == null || title.trim().isEmpty()) {
            disputeError.setValue("Please enter a dispute title.");
            return;
        }

        if (explanation == null || explanation.trim().isEmpty()) {
            disputeError.setValue(
                    "Please explain why you are disputing this ticket."
            );
            return;
        }

        User currentUser = SessionManager
                .getInstance()
                .getCurrentUser();

        if (currentUser == null) {
            disputeError.setValue(
                    "Your session has expired. Please log in again."
            );
            return;
        }

        boolean alreadySubmitted = RepositoryProvider
                .getDisputeRepository()
                .getDisputeByTicketId(ticketId) != null;

        if (alreadySubmitted) {
            disputeError.setValue(
                    "A dispute has already been submitted for this ticket."
            );
            return;
        }

        boolean success = RepositoryProvider
                .getDisputeRepository()
                .submitDispute(
                        ticketId,
                        currentUser.getId(),
                        title.trim(),
                        explanation.trim()
                ) != null;

        if (success) {
            disputeSuccess.setValue(true);
        } else {
            disputeError.setValue(
                    "Unable to submit your dispute. "
                            + "Please check the ticket and try again."
            );
        }
    }
}
