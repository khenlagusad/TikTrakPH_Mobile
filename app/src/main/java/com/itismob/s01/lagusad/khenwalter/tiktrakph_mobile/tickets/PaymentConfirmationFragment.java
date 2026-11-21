
package com.itismob.s01.lagusad.khenwalter.tiktrakph_mobile.tickets;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.itismob.s01.lagusad.khenwalter.tiktrakph_mobile.R;
import com.itismob.s01.lagusad.khenwalter.tiktrakph_mobile.databinding.FragmentPaymentConfirmationBinding;

public class PaymentConfirmationFragment extends Fragment {

    private FragmentPaymentConfirmationBinding binding;
    private PaymentConfirmationViewModel viewModel;
    private int ticketId;

    public PaymentConfirmationFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState
    ) {
        binding = FragmentPaymentConfirmationBinding.inflate(
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
                .get(PaymentConfirmationViewModel.class);

        ticketId = requireArguments().getInt("ticketId", -1);

        setupButtons();
        observeViewModel();

        if (ticketId == -1) {
            showError("Invalid ticket selection.");
            binding.btnConfirmPayment.setEnabled(false);
            return;
        }

        viewModel.loadTicket(ticketId);
    }

    private void setupButtons() {

        binding.btnBack.setOnClickListener(v ->
                NavHostFragment.findNavController(this).navigateUp()
        );

        binding.btnCancelPayment.setOnClickListener(v ->
                NavHostFragment.findNavController(this).navigateUp()
        );

        binding.btnConfirmPayment.setOnClickListener(v ->
                showPaymentConfirmation()
        );
    }

    private void showPaymentConfirmation() {

        new MaterialAlertDialogBuilder(requireContext())
                .setTitle("Confirm Payment")
                .setMessage(
                        "Are you sure you want to proceed with this simulated payment?"
                )
                .setNegativeButton("Cancel", (dialog, which) ->
                        dialog.dismiss()
                )
                .setPositiveButton("Confirm", (dialog, which) ->
                        confirmPayment()
                )
                .show();
    }

    private void confirmPayment() {

        binding.btnConfirmPayment.setEnabled(false);
        binding.tvPaymentError.setVisibility(View.GONE);

        viewModel.confirmPayment(ticketId);
    }

    private void observeViewModel() {

        viewModel.getPaymentDetails().observe(
                getViewLifecycleOwner(),
                details -> {

                    if (details == null) {
                        return;
                    }

                    binding.tvPaymentReference.setText(
                            details.referenceNumber
                    );

                    binding.tvPaymentAmount.setText(
                            details.amountDue
                    );
                }
        );

        viewModel.getPaymentSuccess().observe(
                getViewLifecycleOwner(),
                success -> {

                    if (Boolean.TRUE.equals(success)) {

                        Toast.makeText(
                                requireContext(),
                                "Payment completed successfully!",
                                Toast.LENGTH_SHORT
                        ).show();

                        NavHostFragment.findNavController(this)
                                .popBackStack();

                    }
                }
        );

        viewModel.getPaymentError().observe(
                getViewLifecycleOwner(),
                error -> {

                    if (error != null) {
                        showError(error);
                        binding.btnConfirmPayment.setEnabled(true);
                    }
                }
        );
    }

    private void showError(String message) {

        binding.tvPaymentError.setText(message);
        binding.tvPaymentError.setVisibility(View.VISIBLE);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
