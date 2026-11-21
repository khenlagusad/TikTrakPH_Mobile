
package com.itismob.s01.lagusad.khenwalter.tiktrakph_mobile.guest;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.itismob.s01.lagusad.khenwalter.tiktrakph_mobile.R;
import com.itismob.s01.lagusad.khenwalter.tiktrakph_mobile.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;

    public HomeFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState
    ) {
        binding = FragmentHomeBinding.inflate(
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

        setupGuestSearchButton();
        setupLoginButton();
        setupRegisterButton();
    }

    private void setupGuestSearchButton() {
        binding.btnGuestSearch.setOnClickListener(v ->
                Navigation.findNavController(v).navigate(
                        R.id.guestSearchFragment
                )
        );
    }

    private void setupLoginButton() {
        binding.btnLogin.setOnClickListener(v ->
                Navigation.findNavController(v).navigate(
                        R.id.loginFragment
                )
        );
    }

    private void setupRegisterButton() {
        binding.btnRegister.setOnClickListener(v ->
                Navigation.findNavController(v).navigate(
                        R.id.registerFragment
                )
        );
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
