
package com.itismob.s01.lagusad.khenwalter.tiktrakph_mobile.guest;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.itismob.s01.lagusad.khenwalter.tiktrakph_mobile.R;
import com.itismob.s01.lagusad.khenwalter.tiktrakph_mobile.data.repository.RepositoryProvider;
import com.itismob.s01.lagusad.khenwalter.tiktrakph_mobile.databinding.FragmentSearchResultsBinding;

import java.util.ArrayList;

public class SearchResultsFragment extends Fragment {

    private FragmentSearchResultsBinding binding;
    private SearchResultsViewModel viewModel;
    private TicketAdapter ticketAdapter;

    public SearchResultsFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {

        binding = FragmentSearchResultsBinding.inflate(
                inflater, container, false
        );

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(
            @NonNull View view,
            @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(requireActivity())
                .get(SearchResultsViewModel.class);

        setupRecyclerView();
        setupBackButton();
        observeSearchResults();
    }

    private void setupRecyclerView() {

        binding.rvSearchResults.setLayoutManager(
                new LinearLayoutManager(requireContext())
        );

        ticketAdapter = new TicketAdapter(
                new ArrayList<>(),
                RepositoryProvider.getTicketRepository(),
                ticket -> navigateToTicketDetails(ticket.getId())
        );

        binding.rvSearchResults.setAdapter(ticketAdapter);
    }

    private void navigateToTicketDetails(int ticketId) {

        Bundle args = new Bundle();
        args.putInt("ticketId", ticketId);

        Navigation.findNavController(requireView()).navigate(
                R.id.ticketDetailsFragment,
                args
        );
    }

    private void setupBackButton() {

        binding.btnBack.setOnClickListener(v ->
                Navigation.findNavController(v).navigateUp()
        );
    }

    private void observeSearchResults() {

        viewModel.getSearchQuery().observe(
                getViewLifecycleOwner(),
                query -> {
                    if (query != null) {
                        binding.tvSearchSummary.setText(
                                "Results for " + query
                        );
                    }
                }
        );

        viewModel.getSearchResults().observe(
                getViewLifecycleOwner(),
                tickets -> {

                    if (tickets == null) {
                        return;
                    }

                    ticketAdapter.updateTickets(tickets);

                    boolean hasResults = !tickets.isEmpty();

                    binding.tvResultCount.setText(
                            tickets.size() + (
                                    tickets.size() == 1
                                            ? " ticket found"
                                            : " tickets found"
                            )
                    );

                    binding.rvSearchResults.setVisibility(
                            hasResults ? View.VISIBLE : View.GONE
                    );

                    binding.tvEmptyState.setVisibility(
                            hasResults ? View.GONE : View.VISIBLE
                    );
                }
        );
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
        ticketAdapter = null;
    }
}
