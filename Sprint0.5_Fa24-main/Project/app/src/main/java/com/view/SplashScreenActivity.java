// SplashScreenActivity.java
package com.view;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import androidx.appcompat.app.AppCompatActivity;
import com.example.sprintproject.R;

public class SplashScreenActivity extends AppCompatActivity {
    private static final String TAG = "SplashScreenActivity"; // Tag for logging

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        Log.d(TAG, "SplashScreenActivity created");

        // Set a delay for the splash screen
        new Handler().postDelayed(() -> {
            Log.d(TAG, "Navigating to StartScreenActivity");
            Intent intent = new Intent(SplashScreenActivity.this, StartScreenActivity.class);
            startActivity(intent);
            finish(); // Close this activity
        }, 3000); // Delay of 3 seconds
    }
}
