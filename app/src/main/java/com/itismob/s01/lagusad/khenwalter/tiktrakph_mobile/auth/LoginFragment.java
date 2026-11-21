
package com.itismob.s01.lagusad.khenwalter.tiktrakph_mobile.auth;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.itismob.s01.lagusad.khenwalter.tiktrakph_mobile.R;
import com.itismob.s01.lagusad.khenwalter.tiktrakph_mobile.databinding.FragmentLoginBinding;

public class LoginFragment extends Fragment {

    private FragmentLoginBinding binding;
    private LoginViewModel viewModel;

    public LoginFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState
    ) {
        binding = FragmentLoginBinding.inflate(
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
                .get(LoginViewModel.class);

        setupBackButton();
        setupLoginButton();
        observeLoginResult();
    }

    private void setupBackButton() {
        binding.btnBack.setOnClickListener(v ->
                Navigation.findNavController(v).navigateUp()
        );
    }

    private void setupLoginButton() {

        binding.btnLogin.setOnClickListener(v -> {

            String username = binding.etUsername.getText() != null
                    ? binding.etUsername.getText().toString()
                    : "";

            String password = binding.etPassword.getText() != null
                    ? binding.etPassword.getText().toString()
                    : "";

            binding.tilUsername.setError(null);
            binding.tilPassword.setError(null);
            binding.tvLoginMessage.setVisibility(View.GONE);

            viewModel.login(username, password);
        });
    }

    private void observeLoginResult() {

        viewModel.getLoginError().observe(
                getViewLifecycleOwner(),
                error -> {

                    if (error != null) {
                        binding.tvLoginMessage.setText(error);
                        binding.tvLoginMessage.setVisibility(View.VISIBLE);
                    } else {
                        binding.tvLoginMessage.setVisibility(View.GONE);
                    }
                }
        );

        viewModel.getAuthenticatedUser().observe(
                getViewLifecycleOwner(),
                user -> {

                    if (user != null && isAdded()) {
                        Navigation.findNavController(requireView()).navigate(
                                R.id.action_loginFragment_to_dashboardFragment
                        );
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
