package com.view;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;  // Import Log
import androidx.appcompat.app.AppCompatActivity;
import com.example.sprintproject.R;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity"; // Tag for logging

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Log.d(TAG, "LoginActivity created"); // Log statement

        findViewById(R.id.loginButton).setOnClickListener(view -> {
            Log.d(TAG, "Login button clicked"); // Log statement for login button click
            Intent intent = new Intent(LoginActivity.this, MainAppScreensActivity.class);
            startActivity(intent);
            finish(); // finish LoginActivity
        });

        findViewById(R.id.createAccountButton).setOnClickListener(view -> {
            Log.d(TAG, "Create Account button clicked"); // Log statement for create account button click
            Intent intent = new Intent(LoginActivity.this, CreateAccountActivity.class);
            startActivity(intent);
        });
    }
}
