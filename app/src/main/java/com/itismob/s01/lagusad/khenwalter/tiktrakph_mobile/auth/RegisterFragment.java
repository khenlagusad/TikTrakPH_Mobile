
package com.itismob.s01.lagusad.khenwalter.tiktrakph_mobile.auth;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.google.android.material.textfield.TextInputEditText;
import com.itismob.s01.lagusad.khenwalter.tiktrakph_mobile.R;
import com.itismob.s01.lagusad.khenwalter.tiktrakph_mobile.databinding.FragmentRegisterBinding;

public class RegisterFragment extends Fragment {

    private FragmentRegisterBinding binding;
    private RegisterViewModel viewModel;

    public RegisterFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState
    ) {
        binding = FragmentRegisterBinding.inflate(
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
                .get(RegisterViewModel.class);

        setupBackButton();
        setupRegisterButton();
        setupLoginLink();
        observeRegistrationResult();
    }

    private void setupBackButton() {
        binding.btnBack.setOnClickListener(v ->
                Navigation.findNavController(v).navigateUp()
        );
    }

    private void setupRegisterButton() {

        binding.btnRegister.setOnClickListener(v -> {

            String firstName = getTrimmedText(binding.etFirstName);
            String lastName = getTrimmedText(binding.etLastName);
            String username = getTrimmedText(binding.etUsername);
            String password = getRawText(binding.etPassword);
            String confirmPassword = getRawText(binding.etConfirmPassword);
            String phone = getTrimmedText(binding.etPhone);
            String licenseNumber = getTrimmedText(binding.etLicenseNumber);

            binding.tvRegisterMessage.setVisibility(View.GONE);

            viewModel.register(
                    firstName,
                    lastName,
                    username,
                    password,
                    confirmPassword,
                    phone,
                    licenseNumber
            );
        });
    }

    private String getTrimmedText(TextInputEditText editText) {
        return getRawText(editText).trim();
    }

    private String getRawText(TextInputEditText editText) {
        return editText.getText() != null
                ? editText.getText().toString()
                : "";
    }

    private void setupLoginLink() {
        binding.tvLoginLink.setOnClickListener(v ->
                Navigation.findNavController(v).navigate(
                        R.id.loginFragment
                )
        );
    }

    private void observeRegistrationResult() {

        viewModel.getRegisterError().observe(
                getViewLifecycleOwner(),
                error -> {

                    if (error != null) {
                        binding.tvRegisterMessage.setText(error);
                        binding.tvRegisterMessage.setVisibility(View.VISIBLE);
                    } else {
                        binding.tvRegisterMessage.setVisibility(View.GONE);
                    }
                }
        );

        viewModel.getRegisteredUser().observe(
                getViewLifecycleOwner(),
                user -> {

                    if (user != null && isAdded()) {

                        Toast.makeText(
                                requireContext(),
                                "Account created successfully! Please log in.",
                                Toast.LENGTH_LONG
                        ).show();

                        Navigation.findNavController(requireView())
                                .popBackStack(R.id.homeFragment, false);
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
