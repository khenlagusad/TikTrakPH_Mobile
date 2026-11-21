
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
import androidx.navigation.fragment.NavHostFragment;

import com.itismob.s01.lagusad.khenwalter.tiktrakph_mobile.R;
import com.itismob.s01.lagusad.khenwalter.tiktrakph_mobile.data.model.User;
import com.itismob.s01.lagusad.khenwalter.tiktrakph_mobile.data.session.SessionManager;
import com.itismob.s01.lagusad.khenwalter.tiktrakph_mobile.databinding.FragmentEditProfileBinding;

public class EditProfileFragment extends Fragment {

    private FragmentEditProfileBinding binding;
    private EditProfileViewModel viewModel;

    public EditProfileFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState
    ) {
        binding = FragmentEditProfileBinding.inflate(
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
                .get(EditProfileViewModel.class);

        loadCurrentValues();
        setupButtons();
        observeViewModel();
    }

    private void loadCurrentValues() {
        User user = SessionManager.getInstance().getCurrentUser();

        if (user == null) {
            return;
        }

        binding.etUsername.setText(user.getUsername());
        binding.etPhone.setText(user.getPhone());
    }

    private void setupButtons() {
        binding.btnSaveProfile.setOnClickListener(v -> saveProfile());

        binding.btnCancelEdit.setOnClickListener(v ->
                NavHostFragment.findNavController(this).popBackStack()
        );
    }

    private void saveProfile() {
        binding.tvEditProfileError.setVisibility(View.GONE);

        String username = binding.etUsername.getText() != null
                ? binding.etUsername.getText().toString()
                : "";

        String phone = binding.etPhone.getText() != null
                ? binding.etPhone.getText().toString()
                : "";

        String currentPassword = binding.etCurrentPassword.getText() != null
                ? binding.etCurrentPassword.getText().toString()
                : "";

        String newPassword = binding.etNewPassword.getText() != null
                ? binding.etNewPassword.getText().toString()
                : "";

        String confirmPassword = binding.etConfirmPassword.getText() != null
                ? binding.etConfirmPassword.getText().toString()
                : "";

        viewModel.saveProfile(
                username,
                phone,
                currentPassword,
                newPassword,
                confirmPassword
        );
    }

    private void observeViewModel() {
        viewModel.getErrorMessage().observe(
                getViewLifecycleOwner(),
                error -> {
                    if (error != null && !error.isEmpty()) {
                        binding.tvEditProfileError.setText(error);
                        binding.tvEditProfileError.setVisibility(View.VISIBLE);
                    }
                }
        );

        viewModel.getUpdateSuccessful().observe(
                getViewLifecycleOwner(),
                successful -> {
                    if (Boolean.TRUE.equals(successful)) {
                        Toast.makeText(
                                requireContext(),
                                "Profile updated successfully.",
                                Toast.LENGTH_SHORT
                        ).show();

                        NavHostFragment.findNavController(this)
                                .popBackStack(R.id.profileFragment, false);
                    }
                }
        );
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
