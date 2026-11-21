
package com.itismob.s01.lagusad.khenwalter.tiktrakph_mobile.tickets;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.material.chip.Chip;
import com.itismob.s01.lagusad.khenwalter.tiktrakph_mobile.R;
import com.itismob.s01.lagusad.khenwalter.tiktrakph_mobile.data.model.Ticket;
import com.itismob.s01.lagusad.khenwalter.tiktrakph_mobile.databinding.FragmentManageTicketsBinding;

public class ManageTicketsFragment extends Fragment {

    private FragmentManageTicketsBinding binding;
    private ManageTicketsViewModel viewModel;
    private ManagedTicketAdapter adapter;

    public ManageTicketsFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState
    ) {
        binding = FragmentManageTicketsBinding.inflate(
                inflater, container, false
        );

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(
            @NonNull View view,
            @Nullable Bundle savedInstanceState
    ) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(this)
                .get(ManageTicketsViewModel.class);

        setupRecyclerView();
        setupFilters();
        observeTickets();

        viewModel.loadTickets();
    }

    private void setupRecyclerView() {
        adapter = new ManagedTicketAdapter(this::openTicketDetails);

        binding.rvTickets.setLayoutManager(
                new LinearLayoutManager(requireContext())
        );

        binding.rvTickets.setAdapter(adapter);
    }

    private void setupFilters() {
        binding.filterChipGroup.setOnCheckedStateChangeListener(
                (group, checkedIds) -> {
                    if (checkedIds.isEmpty()) {
                        return;
                    }

                    Chip selectedChip = group.findViewById(
                            checkedIds.get(0)
                    );

                    if (selectedChip != null) {
                        viewModel.setFilter(
                                selectedChip.getText().toString()
                        );
                    }
                }
        );
    }

    private void observeTickets() {
        viewModel.getDisplayedTickets().observe(
                getViewLifecycleOwner(),
                tickets -> {
                    adapter.setTickets(tickets);

                    binding.tvTicketCount.setText(
                            tickets.size() + (
                                    tickets.size() == 1
                                            ? " ticket"
                                            : " tickets"
                            )
                    );

                    boolean isEmpty = tickets.isEmpty();

                    binding.tvEmptyTickets.setVisibility(
                            isEmpty ? View.VISIBLE : View.GONE
                    );

                    binding.rvTickets.setVisibility(
                            isEmpty ? View.GONE : View.VISIBLE
                    );
                }
        );
    }

    private void openTicketDetails(Ticket ticket) {
        Bundle bundle = new Bundle();
        bundle.putInt("ticketId", ticket.getId());

        NavController navController =
                NavHostFragment.findNavController(this);

        navController.navigate(
                R.id.ticketDetailsFragment,
                bundle
        );
    }

    @Override
    public void onResume() {
        super.onResume();

        if (viewModel != null) {
            viewModel.loadTickets();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
