
package com.itismob.s01.lagusad.khenwalter.tiktrakph_mobile.guest;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.itismob.s01.lagusad.khenwalter.tiktrakph_mobile.data.model.Ticket;
import com.itismob.s01.lagusad.khenwalter.tiktrakph_mobile.data.repository.RepositoryProvider;
import com.itismob.s01.lagusad.khenwalter.tiktrakph_mobile.data.repository.TicketRepository;

import java.util.List;

public class GuestSearchViewModel extends ViewModel {

    private final TicketRepository ticketRepository =
            RepositoryProvider.getTicketRepository();

    private final MutableLiveData<List<Ticket>> searchResults =
            new MutableLiveData<>();

    private final MutableLiveData<String> searchMessage =
            new MutableLiveData<>();

    public LiveData<List<Ticket>> getSearchResults() {
        return searchResults;
    }

    public LiveData<String> getSearchMessage() {
        return searchMessage;
    }

    public void searchByPlateNumber(String plateNumber) {

        List<Ticket> results =
                ticketRepository.getTicketsByPlateNumber(plateNumber);

        updateResults(results);
    }

    public void searchByLicenseNumber(String licenseNumber) {

        List<Ticket> results =
                ticketRepository.getTicketsByLicenseNumber(licenseNumber);

        updateResults(results);
    }

    private void updateResults(List<Ticket> results) {

        searchResults.setValue(results);

        if (results.isEmpty()) {
            searchMessage.setValue("No tickets found for your search.");
        } else {
            searchMessage.setValue(
                    results.size() + " ticket(s) found."
            );
        }
    }
}
