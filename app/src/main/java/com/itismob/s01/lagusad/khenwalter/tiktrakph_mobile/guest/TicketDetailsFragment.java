
package com.itismob.s01.lagusad.khenwalter.tiktrakph_mobile.guest;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavHostController;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.itismob.s01.lagusad.khenwalter.tiktrakph_mobile.R;
import com.itismob.s01.lagusad.khenwalter.tiktrakph_mobile.data.model.TicketStatus;
import com.itismob.s01.lagusad.khenwalter.tiktrakph_mobile.data.session.SessionManager;
import com.itismob.s01.lagusad.khenwalter.tiktrakph_mobile.databinding.FragmentTicketDetailsBinding;

public class TicketDetailsFragment extends Fragment {

    private FragmentTicketDetailsBinding binding;
    private TicketDetailsViewModel viewModel;
    private int ticketId;

    public TicketDetailsFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState
    ) {
        binding = FragmentTicketDetailsBinding.inflate(
                inflater,
                container,
                false
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
                .get(TicketDetailsViewModel.class);

        ticketId = requireArguments().getInt("ticketId", -1);

        setupBackButton();
        setupActions();
        observeTicketDetails();

        if (ticketId == -1) {
            Toast.makeText(
                    requireContext(),
                    "Invalid ticket selection.",
                    Toast.LENGTH_SHORT
            ).show();

            NavHostFragment.findNavController(this).navigateUp();
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        if (viewModel != null && ticketId != -1) {
            viewModel.loadTicket(ticketId);
        }
    }

    private void setupBackButton() {
        binding.btnBack.setOnClickListener(v ->
                NavHostFragment.findNavController(this).navigateUp()
        );
    }

    private void setupActions() {

        binding.btnPay.setOnClickListener(v -> {

            if (!SessionManager.getInstance().isLoggedIn()) {
                showLoginPrompt("pay for this ticket");
                return;
            }

            Bundle arguments = new Bundle();
            arguments.putInt("ticketId", ticketId);

            NavHostFragment.findNavController(this).navigate(
                    R.id.action_ticketDetailsFragment_to_paymentConfirmationFragment,
                    arguments
            );
        });

        binding.btnFileDispute.setOnClickListener(v -> {

            if (!SessionManager.getInstance().isLoggedIn()) {
                showLoginPrompt("file a dispute");
                return;
            }

            Bundle arguments = new Bundle();
            arguments.putInt("ticketId", ticketId);

            NavHostFragment.findNavController(this).navigate(
                    R.id.action_ticketDetailsFragment_to_fileDisputeFragment,
                    arguments
            );
        });
    }

    private void showLoginPrompt(String action) {

        new MaterialAlertDialogBuilder(requireContext())
                .setTitle("Login Required")
                .setMessage(
                        "You need to log in to " + action
                                + ". Please log in to continue."
                )
                .setNegativeButton("Cancel", (dialog, which) ->
                        dialog.dismiss()
                )
                .setPositiveButton("Log In", (dialog, which) ->
                        NavHostFragment.findNavController(this).navigate(
                                R.id.action_ticketDetailsFragment_to_loginFragment
                        )
                )
                .show();
    }

    private void observeTicketDetails() {

        viewModel.getTicketDetails().observe(
                getViewLifecycleOwner(),
                details -> {

                    if (details == null) {
                        Toast.makeText(
                                requireContext(),
                                "Ticket not found.",
                                Toast.LENGTH_SHORT
                        ).show();

                        NavHostFragment.findNavController(this).navigateUp();
                        return;
                    }

                    binding.tvReferenceNumber.setText(details.referenceNumber);
                    binding.tvAmountDue.setText(details.amountDue);
                    binding.tvTicketStatus.setText(details.status);
                    binding.tvViolationName.setText(details.violationName);
                    binding.tvViolationDescription.setText(details.violationDescription);
                    binding.tvPlateNumber.setText(details.plateNumber);
                    binding.tvLicenseNumber.setText(details.licenseNumber);
                    binding.tvDateIssued.setText(details.dateIssued);
                    binding.tvEnforcerName.setText(details.enforcerName);
                    binding.tvCityName.setText(details.cityName);

                    updateAvailableActions(details.ticketStatus);
                }
        );
    }

    private void updateAvailableActions(TicketStatus status) {

        boolean loggedIn = SessionManager.getInstance().isLoggedIn();

        boolean canPay =
                status == TicketStatus.UNPAID
                        || status == TicketStatus.REJECTED;

        boolean canDispute =
                status == TicketStatus.UNPAID;

        binding.btnPay.setVisibility(
                canPay ? View.VISIBLE : View.GONE
        );

        binding.btnFileDispute.setVisibility(
                canDispute ? View.VISIBLE : View.GONE
        );

        if (!loggedIn) {

            binding.tvDetailsMessage.setText(
                    "Log in to manage this ticket."
            );

        } else {

            switch (status) {

                case UNPAID:
                    binding.tvDetailsMessage.setText(
                            "You may pay this ticket or file a dispute."
                    );
                    break;

                case REJECTED:
                    binding.tvDetailsMessage.setText(
                            "Your dispute was rejected. You may now pay this ticket."
                    );
                    break;

                case PENDING:
                    binding.tvDetailsMessage.setText(
                            "Your dispute is currently being reviewed."
                    );
                    break;

                case PAID:
                    binding.tvDetailsMessage.setText(
                            "This ticket has already been paid."
                    );
                    break;

                case RESOLVED:
                    binding.tvDetailsMessage.setText(
                            "This dispute was resolved. No further action is required."
                    );
                    break;
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
