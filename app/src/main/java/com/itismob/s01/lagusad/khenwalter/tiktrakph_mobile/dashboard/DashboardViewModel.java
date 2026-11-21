
package com.itismob.s01.lagusad.khenwalter.tiktrakph_mobile.dashboard;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.itismob.s01.lagusad.khenwalter.tiktrakph_mobile.data.model.Ticket;
import com.itismob.s01.lagusad.khenwalter.tiktrakph_mobile.data.model.TicketStatus;
import com.itismob.s01.lagusad.khenwalter.tiktrakph_mobile.data.model.User;
import com.itismob.s01.lagusad.khenwalter.tiktrakph_mobile.data.model.Vehicle;
import com.itismob.s01.lagusad.khenwalter.tiktrakph_mobile.data.repository.RepositoryProvider;
import com.itismob.s01.lagusad.khenwalter.tiktrakph_mobile.data.session.SessionManager;

import java.util.ArrayList;
import java.util.List;

public class DashboardViewModel extends ViewModel {

    private final MutableLiveData<User> currentUser = new MutableLiveData<>();
    private final MutableLiveData<List<Vehicle>> vehicles =
            new MutableLiveData<>(new ArrayList<>());
    private final MutableLiveData<List<Ticket>> activeTickets =
            new MutableLiveData<>(new ArrayList<>());

    public LiveData<User> getCurrentUser() {
        return currentUser;
    }

    public LiveData<List<Vehicle>> getVehicles() {
        return vehicles;
    }

    public LiveData<List<Ticket>> getActiveTickets() {
        return activeTickets;
    }

    public void loadDashboard() {
        User user = SessionManager.getInstance().getCurrentUser();

        currentUser.setValue(user);

        if (user == null) {
            vehicles.setValue(new ArrayList<>());
            activeTickets.setValue(new ArrayList<>());
            return;
        }

        List<Vehicle> userVehicles =
                RepositoryProvider.getVehicleRepository()
                        .getVehiclesByUserId(user.getId());

        List<Ticket> userTickets =
                RepositoryProvider.getTicketRepository()
                        .getTicketsByLicenseNumber(user.getLicenseNumber());

        List<Ticket> active = new ArrayList<>();

        for (Ticket ticket : userTickets) {
            TicketStatus status =
                    RepositoryProvider.getTicketRepository()
                            .getTicketStatus(ticket.getId());

            if (status == TicketStatus.UNPAID
                    || status == TicketStatus.PENDING
                    || status == TicketStatus.REJECTED) {
                active.add(ticket);
            }
        }

        vehicles.setValue(userVehicles);
        activeTickets.setValue(active);
    }
}
