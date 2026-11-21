
package com.itismob.s01.lagusad.khenwalter.tiktrakph_mobile.tickets;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.itismob.s01.lagusad.khenwalter.tiktrakph_mobile.data.model.Ticket;
import com.itismob.s01.lagusad.khenwalter.tiktrakph_mobile.data.model.TicketStatus;
import com.itismob.s01.lagusad.khenwalter.tiktrakph_mobile.data.model.User;
import com.itismob.s01.lagusad.khenwalter.tiktrakph_mobile.data.repository.RepositoryProvider;
import com.itismob.s01.lagusad.khenwalter.tiktrakph_mobile.data.session.SessionManager;

import java.util.ArrayList;
import java.util.List;

public class ManageTicketsViewModel extends ViewModel {

    private final MutableLiveData<List<Ticket>> displayedTickets =
            new MutableLiveData<>(new ArrayList<>());

    private List<Ticket> allTickets = new ArrayList<>();
    private String selectedFilter = "All";

    public LiveData<List<Ticket>> getDisplayedTickets() {
        return displayedTickets;
    }

    public void loadTickets() {
        User user = SessionManager.getInstance().getCurrentUser();

        if (user == null) {
            allTickets = new ArrayList<>();
        } else {
            allTickets = new ArrayList<>(
                    RepositoryProvider.getTicketRepository()
                            .getTicketsByLicenseNumber(user.getLicenseNumber())
            );
        }

        applyFilter();
    }

    public void setFilter(String filter) {
        selectedFilter = filter;
        applyFilter();
    }

    private void applyFilter() {
        List<Ticket> filteredTickets = new ArrayList<>();

        for (Ticket ticket : allTickets) {
            TicketStatus status =
                    RepositoryProvider.getTicketRepository()
                            .getTicketStatus(ticket.getId());

            if (matchesFilter(status)) {
                filteredTickets.add(ticket);
            }
        }

        displayedTickets.setValue(filteredTickets);
    }

    private boolean matchesFilter(TicketStatus status) {
        if ("All".equals(selectedFilter)) {
            return true;
        }

        return status.name().equalsIgnoreCase(selectedFilter);
    }
}
