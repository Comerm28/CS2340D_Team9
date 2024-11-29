package com.view;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.example.sprintproject.R;
import android.util.Log;


public class StartScreenActivity extends AppCompatActivity {
    private static final String TAG = "StartScreenActivity"; // Tag for logging

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_screen);
        Log.d(TAG, "StartScreenActivity created");

        // Start button navigates to LoginActivity
        findViewById(R.id.startButton).setOnClickListener(view -> {
            Log.d(TAG, "Start button clicked"); // Log statement for button click
            Intent intent = new Intent(StartScreenActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        });

        // Quit button closes the app
        findViewById(R.id.quitButton).setOnClickListener(view -> {
            Log.d(TAG, "Quit button clicked"); // Log statement for quit button click
            finishAffinity(); // Closes all activities and exits the app
        });
    }
}
