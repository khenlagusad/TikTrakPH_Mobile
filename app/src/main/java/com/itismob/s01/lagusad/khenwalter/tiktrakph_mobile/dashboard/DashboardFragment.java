
package com.itismob.s01.lagusad.khenwalter.tiktrakph_mobile.dashboard;

import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.google.android.material.card.MaterialCardView;

import com.itismob.s01.lagusad.khenwalter.tiktrakph_mobile.R;
import com.itismob.s01.lagusad.khenwalter.tiktrakph_mobile.data.model.Ticket;
import com.itismob.s01.lagusad.khenwalter.tiktrakph_mobile.data.model.TicketStatus;
import com.itismob.s01.lagusad.khenwalter.tiktrakph_mobile.data.model.User;
import com.itismob.s01.lagusad.khenwalter.tiktrakph_mobile.data.model.Vehicle;
import com.itismob.s01.lagusad.khenwalter.tiktrakph_mobile.data.repository.RepositoryProvider;
import com.itismob.s01.lagusad.khenwalter.tiktrakph_mobile.databinding.FragmentDashboardBinding;

import java.util.List;
import java.util.Locale;

public class DashboardFragment extends Fragment {

    private FragmentDashboardBinding binding;
    private DashboardViewModel viewModel;

    public DashboardFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState
    ) {
        binding = FragmentDashboardBinding.inflate(
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
                .get(DashboardViewModel.class);

        observeDashboard();
        viewModel.loadDashboard();
    }

    private void observeDashboard() {

        viewModel.getCurrentUser().observe(
                getViewLifecycleOwner(),
                this::displayWelcome
        );

        viewModel.getVehicles().observe(
                getViewLifecycleOwner(),
                this::displayVehicles
        );

        viewModel.getActiveTickets().observe(
                getViewLifecycleOwner(),
                this::displayTickets
        );
    }

    private void displayWelcome(User user) {

        if (user != null) {
            binding.tvWelcome.setText(
                    "Welcome back, " + user.getFirstName() + "!"
            );
        } else {
            binding.tvWelcome.setText(
                    "No active user session."
            );
        }
    }

    private void displayVehicles(List<Vehicle> vehicles) {

        binding.vehicleContainer.removeAllViews();

        binding.tvVehicleCount.setText(
                String.valueOf(vehicles.size())
        );

        if (vehicles.isEmpty()) {
            addMessage(
                    binding.vehicleContainer,
                    "No registered vehicles found."
            );
            return;
        }

        for (Vehicle vehicle : vehicles) {

            MaterialCardView card = createCard();

            LinearLayout content = createVerticalLayout();
            content.setPadding(dp(16), dp(14), dp(16), dp(14));

            TextView vehicleName = createText(
                    vehicle.getBrand() + " " + vehicle.getModel(),
                    16,
                    R.color.primary_dark,
                    true
            );

            TextView plateNumber = createText(
                    vehicle.getPlateNumber(),
                    13,
                    R.color.primary,
                    true
            );

            plateNumber.setPadding(
                    dp(10), dp(5), dp(10), dp(5)
            );

            plateNumber.setBackground(
                    createRoundedBackground(
                            "#E8EFFC",
                            "#D5E1F5",
                            8
                    )
            );

            LinearLayout.LayoutParams plateParams =
                    new LinearLayout.LayoutParams(
                            ViewGroup.LayoutParams.WRAP_CONTENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT
                    );

            plateParams.topMargin = dp(9);
            plateNumber.setLayoutParams(plateParams);

            TextView specifications = createText(
                    "Year: " + vehicle.getYear()
                            + "     •     Color: " + vehicle.getColor(),
                    12,
                    R.color.text_secondary,
                    false
            );

            LinearLayout.LayoutParams specificationsParams =
                    new LinearLayout.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT
                    );

            specificationsParams.topMargin = dp(10);
            specifications.setLayoutParams(specificationsParams);

            content.addView(vehicleName);
            content.addView(plateNumber);
            content.addView(specifications);

            card.addView(content);

            addCardToContainer(
                    binding.vehicleContainer,
                    card
            );
        }
    }

    private void displayTickets(List<Ticket> tickets) {

        binding.ticketContainer.removeAllViews();

        binding.tvActiveTicketCount.setText(
                String.valueOf(tickets.size())
        );

        if (tickets.isEmpty()) {
            addMessage(
                    binding.ticketContainer,
                    "You have no active tickets. Keep driving safely!"
            );
            return;
        }

        for (Ticket ticket : tickets) {

            TicketStatus status =
                    RepositoryProvider.getTicketRepository()
                            .getTicketStatus(ticket.getId());

            MaterialCardView card = createCard();

            card.setClickable(true);
            card.setFocusable(true);

            card.setOnClickListener(v ->
                    openTicketDetails(ticket.getId())
            );

            LinearLayout content = createVerticalLayout();
            content.setPadding(dp(16), dp(15), dp(16), dp(15));

            // Reference and Status Row
            LinearLayout topRow = new LinearLayout(
                    requireContext()
            );

            topRow.setOrientation(LinearLayout.HORIZONTAL);
            topRow.setGravity(Gravity.CENTER_VERTICAL);

            TextView reference = createText(
                    ticket.getReferenceNumber(),
                    11,
                    R.color.text_secondary,
                    false
            );

            reference.setSingleLine(true);

            LinearLayout.LayoutParams referenceParams =
                    new LinearLayout.LayoutParams(
                            0,
                            ViewGroup.LayoutParams.WRAP_CONTENT,
                            1
                    );

            reference.setLayoutParams(referenceParams);

            TextView statusBadge = createStatusBadge(status);

            topRow.addView(reference);
            topRow.addView(statusBadge);

            // Plate Number
            TextView plateNumber = createText(
                    ticket.getPlateNumber(),
                    20,
                    R.color.primary_dark,
                    true
            );

            LinearLayout.LayoutParams plateParams =
                    new LinearLayout.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT
                    );

            plateParams.topMargin = dp(15);
            plateNumber.setLayoutParams(plateParams);

            // Divider
            View divider = new View(requireContext());

            divider.setBackgroundColor(
                    Color.parseColor("#E8EDF5")
            );

            LinearLayout.LayoutParams dividerParams =
                    new LinearLayout.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            dp(1)
                    );

            dividerParams.topMargin = dp(13);
            dividerParams.bottomMargin = dp(12);

            divider.setLayoutParams(dividerParams);

            // Amount Label
            TextView amountLabel = createText(
                    "AMOUNT DUE",
                    10,
                    R.color.text_secondary,
                    true
            );

            // Amount
            TextView amount = createText(
                    formatAmount(ticket),
                    18,
                    R.color.primary_dark,
                    true
            );

            LinearLayout.LayoutParams amountParams =
                    new LinearLayout.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT
                    );

            amountParams.topMargin = dp(3);
            amount.setLayoutParams(amountParams);

            // View Details
            TextView viewDetails = createText(
                    "View Ticket Details  →",
                    13,
                    R.color.primary,
                    true
            );

            viewDetails.setGravity(Gravity.END);

            LinearLayout.LayoutParams detailsParams =
                    new LinearLayout.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT
                    );

            detailsParams.topMargin = dp(13);
            viewDetails.setLayoutParams(detailsParams);

            content.addView(topRow);
            content.addView(plateNumber);
            content.addView(divider);
            content.addView(amountLabel);
            content.addView(amount);
            content.addView(viewDetails);

            card.addView(content);

            addCardToContainer(
                    binding.ticketContainer,
                    card
            );
        }
    }

    private String formatAmount(Ticket ticket) {

        return String.format(
                Locale.US,
                "₱%,.2f",
                ticket.getAmountDue()
        );
    }

    private TextView createStatusBadge(TicketStatus status) {

        TextView badge = new TextView(requireContext());

        badge.setText(
                formatStatus(status).toUpperCase(Locale.ROOT)
        );

        badge.setTextSize(10);
        badge.setTypeface(
                Typeface.create("sans-serif", Typeface.BOLD)
        );

        // FIX: These are raw colors, not resource IDs.
        badge.setTextColor(getStatusTextColor(status));

        badge.setPadding(
                dp(10), dp(6), dp(10), dp(6)
        );

        badge.setBackground(
                createRoundedBackground(
                        getStatusBackground(status),
                        getStatusStroke(status),
                        20
                )
        );

        return badge;
    }

    private String formatStatus(TicketStatus status) {

        switch (status) {
            case UNPAID:
                return "Unpaid";
            case PENDING:
                return "Pending";
            case REJECTED:
                return "Rejected";
            case PAID:
                return "Paid";
            case RESOLVED:
                return "Resolved";
            default:
                return "Unknown";
        }
    }

    private int getStatusTextColor(TicketStatus status) {

        switch (status) {
            case UNPAID:
                return Color.parseColor("#B45309");
            case PENDING:
                return Color.parseColor("#1D4ED8");
            case REJECTED:
                return Color.parseColor("#CE1126");
            case PAID:
                return Color.parseColor("#15803D");
            case RESOLVED:
                return Color.parseColor("#64748B");
            default:
                return Color.parseColor("#64748B");
        }
    }

    private String getStatusBackground(TicketStatus status) {

        switch (status) {
            case UNPAID:
                return "#FFF4D6";
            case PENDING:
                return "#E8EFFC";
            case REJECTED:
                return "#FDE8E8";
            case PAID:
                return "#DCFCE7";
            case RESOLVED:
                return "#F1F5F9";
            default:
                return "#F1F5F9";
        }
    }

    private String getStatusStroke(TicketStatus status) {

        switch (status) {
            case UNPAID:
                return "#F5D58A";
            case PENDING:
                return "#C8D9F5";
            case REJECTED:
                return "#F5C2C2";
            case PAID:
                return "#BBEACB";
            case RESOLVED:
                return "#E2E8F0";
            default:
                return "#E2E8F0";
        }
    }

    private MaterialCardView createCard() {

        MaterialCardView card = new MaterialCardView(
                requireContext()
        );

        card.setCardBackgroundColor(
                ContextCompat.getColor(
                        requireContext(),
                        R.color.surface
                )
        );

        card.setRadius(dp(16));
        card.setCardElevation(dp(1));

        card.setStrokeWidth(dp(1));
        card.setStrokeColor(
                Color.parseColor("#DCE6F7")
        );

        return card;
    }

    private LinearLayout createVerticalLayout() {

        LinearLayout layout = new LinearLayout(
                requireContext()
        );

        layout.setOrientation(LinearLayout.VERTICAL);

        layout.setLayoutParams(
                new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                )
        );

        return layout;
    }

    private TextView createText(
            String value,
            int size,
            int colorResource,
            boolean bold
    ) {

        TextView textView = new TextView(requireContext());

        textView.setText(value);
        textView.setTextSize(size);

        // This helper accepts actual color RESOURCE IDs only.
        textView.setTextColor(
                ContextCompat.getColor(
                        requireContext(),
                        colorResource
                )
        );

        textView.setTypeface(
                Typeface.create(
                        "sans-serif",
                        bold ? Typeface.BOLD : Typeface.NORMAL
                )
        );

        return textView;
    }

    private GradientDrawable createRoundedBackground(
            String color,
            String strokeColor,
            int radius
    ) {

        GradientDrawable drawable = new GradientDrawable();

        drawable.setColor(Color.parseColor(color));
        drawable.setCornerRadius(dp(radius));

        drawable.setStroke(
                dp(1),
                Color.parseColor(strokeColor)
        );

        return drawable;
    }

    private void addCardToContainer(
            LinearLayout container,
            MaterialCardView card
    ) {

        LinearLayout.LayoutParams params =
                new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                );

        params.bottomMargin = dp(12);

        card.setLayoutParams(params);

        container.addView(card);
    }

    private void addMessage(
            LinearLayout container,
            String message
    ) {

        MaterialCardView card = createCard();

        TextView textView = createText(
                message,
                13,
                R.color.text_secondary,
                false
        );

        textView.setPadding(
                dp(16), dp(18), dp(16), dp(18)
        );

        card.addView(textView);

        addCardToContainer(container, card);
    }

    private int dp(float value) {

        return (int) (
                value * getResources()
                        .getDisplayMetrics().density
        );
    }

    private void openTicketDetails(int ticketId) {

        Bundle bundle = new Bundle();

        bundle.putInt("ticketId", ticketId);

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
            viewModel.loadDashboard();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
