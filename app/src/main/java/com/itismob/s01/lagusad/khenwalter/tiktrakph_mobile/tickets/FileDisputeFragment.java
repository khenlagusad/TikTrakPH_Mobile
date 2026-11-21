
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
import com.itismob.s01.lagusad.khenwalter.tiktrakph_mobile.databinding.FragmentFileDisputeBinding;

public class FileDisputeFragment extends Fragment {

    private FragmentFileDisputeBinding binding;
    private FileDisputeViewModel viewModel;
    private int ticketId;

    public FileDisputeFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState
    ) {
        binding = FragmentFileDisputeBinding.inflate(
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
                .get(FileDisputeViewModel.class);

        ticketId = requireArguments().getInt("ticketId", -1);

        setupButtons();
        observeViewModel();

        if (ticketId == -1) {
            showError("Invalid ticket selection.");
            binding.btnSubmitDispute.setEnabled(false);
            return;
        }

        viewModel.loadTicket(ticketId);
    }

    private void setupButtons() {

        binding.btnBack.setOnClickListener(v ->
                NavHostFragment.findNavController(this).navigateUp()
        );

        binding.btnCancelDispute.setOnClickListener(v ->
                NavHostFragment.findNavController(this).navigateUp()
        );

        binding.btnSubmitDispute.setOnClickListener(v ->
                showSubmissionConfirmation()
        );
    }

    private void showSubmissionConfirmation() {

        String title = binding.etDisputeTitle.getText() != null
                ? binding.etDisputeTitle.getText().toString().trim()
                : "";

        String explanation = binding.etDisputeExplanation.getText() != null
                ? binding.etDisputeExplanation.getText().toString().trim()
                : "";

        if (title.isEmpty()) {
            showError("Please enter a dispute title.");
            return;
        }

        if (explanation.isEmpty()) {
            showError("Please provide an explanation.");
            return;
        }

        new MaterialAlertDialogBuilder(requireContext())
                .setTitle("Submit Dispute")
                .setMessage(
                        "Are you sure you want to submit this dispute? "
                                + "You can only file one dispute per ticket."
                )
                .setNegativeButton("Review", (dialog, which) ->
                        dialog.dismiss()
                )
                .setPositiveButton("Submit", (dialog, which) ->
                        submitDispute()
                )
                .show();
    }

    private void submitDispute() {

        String title = binding.etDisputeTitle.getText() != null
                ? binding.etDisputeTitle.getText().toString().trim()
                : "";

        String explanation = binding.etDisputeExplanation.getText() != null
                ? binding.etDisputeExplanation.getText().toString().trim()
                : "";

        binding.btnSubmitDispute.setEnabled(false);
        binding.tvDisputeError.setVisibility(View.GONE);

        viewModel.submitDispute(
                ticketId,
                title,
                explanation
        );
    }

    private void observeViewModel() {

        viewModel.getTicketReference().observe(
                getViewLifecycleOwner(),
                reference -> {
                    if (reference != null) {
                        binding.tvDisputeReference.setText(reference);
                    }
                }
        );

        viewModel.getDisputeSuccess().observe(
                getViewLifecycleOwner(),
                success -> {

                    if (Boolean.TRUE.equals(success)) {

                        Toast.makeText(
                                requireContext(),
                                "Dispute submitted successfully!",
                                Toast.LENGTH_SHORT
                        ).show();

                        NavHostFragment.findNavController(this)
                                .popBackStack();
                    }
                }
        );

        viewModel.getDisputeError().observe(
                getViewLifecycleOwner(),
                error -> {

                    if (error != null) {
                        showError(error);
                        binding.btnSubmitDispute.setEnabled(true);
                    }
                }
        );
    }

    private void showError(String message) {

        binding.tvDisputeError.setText(message);
        binding.tvDisputeError.setVisibility(View.VISIBLE);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
