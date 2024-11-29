package com.view;

import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import com.example.sprintproject.R;
import android.util.Log;

public class MainAppScreensActivity extends AppCompatActivity {
    private static final String TAG = "MainAppScreensActivity"; // Tag for logging
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_app_screens);
        Log.d(TAG, "MainAppScreensActivity created");
        // Initialize buttons
        Button logisticsButton = findViewById(R.id.button_logistics);
        Button destinationButton = findViewById(R.id.button_destination);
        Button diningButton = findViewById(R.id.button_dining);
        Button accommodationButton = findViewById(R.id.button_accommodation);
        Button transportationButton = findViewById(R.id.button_transportation);
        Button communityButton = findViewById(R.id.button_community);

        // Set button click listeners to switch fragments
        logisticsButton.setOnClickListener(v -> switchFragment(new LogisticsFragment()));
        destinationButton.setOnClickListener(v -> switchFragment(new DestinationFragment()));
        diningButton.setOnClickListener(v -> switchFragment(new DiningFragment()));
        accommodationButton.setOnClickListener(v -> switchFragment(new AccommodationFragment()));
        transportationButton.setOnClickListener(v -> switchFragment(new TransportationFragment()));
        communityButton.setOnClickListener(v -> switchFragment(new CommunityFragment()));

        // Set the default fragment when the activity is created
        if (savedInstanceState == null) {
            switchFragment(new LogisticsFragment()); // Show LogisticsFragment by default
        }
    }

    private void switchFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit();
    }
}
