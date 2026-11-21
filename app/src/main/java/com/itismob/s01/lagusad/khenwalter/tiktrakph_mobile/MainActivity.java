
package com.itismob.s01.lagusad.khenwalter.tiktrakph_mobile;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.fragment.NavHostFragment;

import com.itismob.s01.lagusad.khenwalter.tiktrakph_mobile.data.session.SessionManager;

public class MainActivity extends AppCompatActivity {

    private LinearLayout bottomNavigation;
    private NavController navController;

    private LinearLayout navDashboard;
    private LinearLayout navTickets;
    private LinearLayout navProfile;

    private ImageView iconDashboard;
    private ImageView iconTickets;
    private ImageView iconProfile;

    private TextView labelDashboard;
    private TextView labelTickets;
    private TextView labelProfile;

    private final int selectedColor = 0xFF0038A8;
    private final int unselectedColor = 0xFF8A9BB5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(
                findViewById(R.id.main),
                (view, insets) -> {

                    Insets systemBars = insets.getInsets(
                            WindowInsetsCompat.Type.systemBars()
                    );

                    view.setPadding(
                            systemBars.left,
                            systemBars.top,
                            systemBars.right,
                            systemBars.bottom
                    );

                    return insets;
                }
        );

        initializeBottomNavigation();

        NavHostFragment navHostFragment =
                (NavHostFragment) getSupportFragmentManager()
                        .findFragmentById(R.id.nav_host_fragment);

        if (navHostFragment == null) {
            throw new IllegalStateException(
                    "Navigation host not found."
            );
        }

        navController = navHostFragment.getNavController();

        setupBottomNavigation();

        navController.addOnDestinationChangedListener(
                (controller, destination, arguments) ->
                        updateBottomNavigation(destination)
        );

        updateBottomNavigation(
                navController.getCurrentDestination()
        );
    }

    private void initializeBottomNavigation() {

        bottomNavigation = findViewById(R.id.bottom_navigation);

        navDashboard = findViewById(R.id.nav_dashboard);
        navTickets = findViewById(R.id.nav_tickets);
        navProfile = findViewById(R.id.nav_profile);

        iconDashboard = findViewById(R.id.icon_dashboard);
        iconTickets = findViewById(R.id.icon_tickets);
        iconProfile = findViewById(R.id.icon_profile);

        labelDashboard = findViewById(R.id.label_dashboard);
        labelTickets = findViewById(R.id.label_tickets);
        labelProfile = findViewById(R.id.label_profile);
    }

    private void setupBottomNavigation() {

        navDashboard.setOnClickListener(
                view -> navigateTo(R.id.dashboardFragment)
        );

        navTickets.setOnClickListener(
                view -> navigateTo(R.id.ticketsFragment)
        );

        navProfile.setOnClickListener(
                view -> navigateTo(R.id.profileFragment)
        );
    }

    private void navigateTo(int destinationId) {

        NavDestination currentDestination =
                navController.getCurrentDestination();

        if (currentDestination == null) {
            return;
        }

        // Keep Profile selected while editing a profile.
        if (destinationId == R.id.profileFragment
                && currentDestination.getId()
                == R.id.editProfileFragment) {

            return;
        }

        if (currentDestination.getId() != destinationId) {
            navController.navigate(destinationId);
        }
    }

    private void updateBottomNavigation(
            NavDestination destination
    ) {

        boolean loggedIn =
                SessionManager.getInstance().isLoggedIn();

        bottomNavigation.setVisibility(
                loggedIn ? View.VISIBLE : View.GONE
        );

        if (!loggedIn || destination == null) {
            return;
        }

        int destinationId = destination.getId();

        if (destinationId == R.id.dashboardFragment) {

            updateSelectedItem(R.id.dashboardFragment);

        } else if (destinationId == R.id.ticketsFragment) {

            updateSelectedItem(R.id.ticketsFragment);

        } else if (destinationId == R.id.profileFragment
                || destinationId == R.id.editProfileFragment) {

            updateSelectedItem(R.id.profileFragment);
        }
    }

    private void updateSelectedItem(int selectedDestination) {

        setNavigationItemState(
                iconDashboard,
                labelDashboard,
                selectedDestination == R.id.dashboardFragment
        );

        setNavigationItemState(
                iconTickets,
                labelTickets,
                selectedDestination == R.id.ticketsFragment
        );

        setNavigationItemState(
                iconProfile,
                labelProfile,
                selectedDestination == R.id.profileFragment
        );
    }

    private void setNavigationItemState(
            ImageView icon,
            TextView label,
            boolean selected
    ) {

        int color = selected ? selectedColor : unselectedColor;

        icon.setColorFilter(color);
        label.setTextColor(color);
    }
}
