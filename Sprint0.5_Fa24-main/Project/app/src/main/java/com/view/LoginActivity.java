package com.view;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;  // Import Log
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sprintproject.R;
import com.google.android.material.snackbar.Snackbar;
import com.viewmodel.AuthenticationViewModel;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity"; // Tag for logging

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Log.d(TAG, "LoginActivity created"); // Log statement

        AuthenticationViewModel authenticationViewModel = new AuthenticationViewModel();
        EditText usernameEditText = findViewById(R.id.usernameEditText);
        EditText passwordEditText = findViewById(R.id.passwordEditText);

        findViewById(R.id.loginButton).setOnClickListener(view -> {
            Log.d(TAG, "Login button clicked"); // Log statement for login button click
            String username = usernameEditText.getText().toString();
            String password = passwordEditText.getText().toString();
            authenticationViewModel.logIn(username, password,
                    () -> startActivity(new Intent(this, MainAppScreensActivity.class)),
                    fail -> Snackbar.make(view, fail, Snackbar.LENGTH_LONG).show());
        });

        findViewById(R.id.createAccountButton).setOnClickListener(view -> {
            Log.d(TAG, "Create Account button clicked");
            // Log statement for create account button click
            Intent intent = new Intent(LoginActivity.this, CreateAccountActivity.class);
            startActivity(intent);
        });

        // quit Button functionality
        findViewById(R.id.quitButton).setOnClickListener(view -> {
            Log.d(TAG, "Quit button clicked"); // Log statement for quit button click
            finishAffinity(); // Close the app
        });
    }
}
