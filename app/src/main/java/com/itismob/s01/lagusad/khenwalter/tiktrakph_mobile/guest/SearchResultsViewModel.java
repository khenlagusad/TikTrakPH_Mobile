package com.itismob.s01.lagusad.khenwalter.tiktrakph_mobile.guest;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.itismob.s01.lagusad.khenwalter.tiktrakph_mobile.data.model.Ticket;

import java.util.ArrayList;
import java.util.List;

public class SearchResultsViewModel extends ViewModel {

    private final MutableLiveData<List<Ticket>> searchResults =
            new MutableLiveData<>(new ArrayList<>());

    private final MutableLiveData<String> searchQuery =
            new MutableLiveData<>("");

    public LiveData<List<Ticket>> getSearchResults() {
        return searchResults;
    }

    public LiveData<String> getSearchQuery() {
        return searchQuery;
    }

    public void setSearchResults(String query, List<Ticket> tickets) {

        searchQuery.setValue(query);

        searchResults.setValue(
                new ArrayList<>(tickets)
        );
    }

    public int getResultCount() {

        List<Ticket> tickets = searchResults.getValue();

        return tickets == null ? 0 : tickets.size();
    }
}
