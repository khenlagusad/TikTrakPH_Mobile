
package com.itismob.s01.lagusad.khenwalter.tiktrakph_mobile.profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.itismob.s01.lagusad.khenwalter.tiktrakph_mobile.R;
import com.itismob.s01.lagusad.khenwalter.tiktrakph_mobile.data.model.User;
import com.itismob.s01.lagusad.khenwalter.tiktrakph_mobile.data.session.SessionManager;
import com.itismob.s01.lagusad.khenwalter.tiktrakph_mobile.databinding.FragmentProfileBinding;

public class ProfileFragment extends Fragment {

    private FragmentProfileBinding binding;
    private ProfileViewModel viewModel;

    public ProfileFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState
    ) {
        binding = FragmentProfileBinding.inflate(
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
                .get(ProfileViewModel.class);

        binding.btnEditProfile.setOnClickListener(v ->
                openEditProfile()
        );

        binding.btnLogout.setOnClickListener(v ->
                showLogoutConfirmation()
        );

        viewModel.getCurrentUser().observe(
                getViewLifecycleOwner(),
                this::displayProfile
        );

        viewModel.loadProfile();
    }

    private void openEditProfile() {
        NavController navController =
                NavHostFragment.findNavController(this);

        navController.navigate(R.id.editProfileFragment);
    }

    private void displayProfile(User user) {
        if (user == null) {
            return;
        }

        binding.tvFullName.setText(
                user.getFirstName() + " " + user.getLastName()
        );

        binding.tvUsername.setText("@" + user.getUsername());
        binding.tvPhone.setText(user.getPhone());
        binding.tvLicenseNumber.setText(user.getLicenseNumber());
        binding.tvCreatedAt.setText(user.getCreatedAt());
    }

    private void showLogoutConfirmation() {
        new MaterialAlertDialogBuilder(requireContext())
                .setTitle("Log Out")
                .setMessage("Are you sure you want to log out of your account?")
                .setNegativeButton("Cancel", (dialog, which) ->
                        dialog.dismiss()
                )
                .setPositiveButton("Log Out", (dialog, which) ->
                        performLogout()
                )
                .show();
    }

    private void performLogout() {
        SessionManager.getInstance().logout();

        NavController navController =
                NavHostFragment.findNavController(this);

        boolean returnedToHome =
                navController.popBackStack(R.id.homeFragment, false);

        if (!returnedToHome) {
            navController.navigate(R.id.homeFragment);
        }

        Toast.makeText(
                requireContext(),
                "You have been logged out.",
                Toast.LENGTH_SHORT
        ).show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
