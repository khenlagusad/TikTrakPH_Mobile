
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

import com.itismob.s01.lagusad.khenwalter.tiktrakph_mobile.R;
import com.itismob.s01.lagusad.khenwalter.tiktrakph_mobile.data.model.Ticket;
import com.itismob.s01.lagusad.khenwalter.tiktrakph_mobile.databinding.FragmentGuestSearchBinding;

import java.util.List;

public class GuestSearchFragment extends Fragment {

    private FragmentGuestSearchBinding binding;
    private GuestSearchViewModel viewModel;
    private SearchResultsViewModel sharedViewModel;

    public GuestSearchFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {

        binding = FragmentGuestSearchBinding.inflate(
                inflater, container, false
        );

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(
            @NonNull View view,
            @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(this)
                .get(GuestSearchViewModel.class);

        sharedViewModel = new ViewModelProvider(requireActivity())
                .get(SearchResultsViewModel.class);

        setupSearchType();
        setupSearchButton();
        setupBackButton();
        observeSearchResults();
    }

    private void setupSearchType() {

        binding.rgSearchType.setOnCheckedChangeListener(
                (group, checkedId) -> {

                    binding.etSearch.setText("");
                    binding.tilSearch.setError(null);
                    binding.tvSearchMessage.setVisibility(View.GONE);

                    if (checkedId == binding.rbPlateNumber.getId()) {
                        binding.tilSearch.setHint("Enter plate number");
                        binding.etSearch.setInputType(
                                android.text.InputType.TYPE_CLASS_TEXT
                        );
                    } else {
                        binding.tilSearch.setHint(
                                "Enter driver's license number"
                        );
                        binding.etSearch.setInputType(
                                android.text.InputType.TYPE_CLASS_TEXT
                        );
                    }
                }
        );
    }

    private void setupSearchButton() {

        binding.btnSearch.setOnClickListener(v -> {

            String searchValue = binding.etSearch
                    .getText()
                    .toString()
                    .trim();

            if (searchValue.isEmpty()) {
                binding.tilSearch.setError(
                        "This field is required."
                );
                return;
            }

            binding.tilSearch.setError(null);

            if (binding.rbPlateNumber.isChecked()) {
                viewModel.searchByPlateNumber(searchValue);
            } else {
                viewModel.searchByLicenseNumber(searchValue);
            }

            List<Ticket> tickets =
                    viewModel.getSearchResults().getValue();

            if (tickets == null) {
                tickets = new java.util.ArrayList<>();
            }

            sharedViewModel.setSearchResults(
                    searchValue,
                    tickets
            );

            Navigation.findNavController(v)
                    .navigate(R.id.searchResultsFragment);
        });
    }

    private void setupBackButton() {

        binding.btnBack.setOnClickListener(v ->
                Navigation.findNavController(v).navigateUp()
        );
    }

    private void observeSearchResults() {

        viewModel.getSearchMessage().observe(
                getViewLifecycleOwner(),
                message -> {

                    if (message != null) {
                        binding.tvSearchMessage.setText(message);
                        binding.tvSearchMessage.setVisibility(
                                View.VISIBLE
                        );
                    } else {
                        binding.tvSearchMessage.setVisibility(
                                View.GONE
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
