package com.view;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sprintproject.R;
import com.google.android.material.snackbar.Snackbar;
import com.viewmodel.AuthenticationViewModel;

public class CreateAccountActivity extends AppCompatActivity {
    private AuthenticationViewModel authenticationViewModel;
    private EditText createUsernameEditText;
    private EditText createPasswordEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account); // Reference to your create account layout

        authenticationViewModel = new AuthenticationViewModel();
        createUsernameEditText = findViewById(R.id.createUsernameEditText);
        createPasswordEditText = findViewById(R.id.createPasswordEditText);
        Button registerButton = findViewById(R.id.registerButton);
        Button loginButton = findViewById(R.id.loginButton);

        // Handle register button click
        registerButton.setOnClickListener(view -> {
            String username = createUsernameEditText.getText().toString();
            String password = createPasswordEditText.getText().toString();
            authenticationViewModel.signUp(username, password, () -> {
                //todo: maybe bundle user name and password
                // and autofill them into the edittext on login screen?
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
            }, fail -> Snackbar.make(view, fail, Snackbar.LENGTH_LONG).show());
        });

        // Handle login button click
        loginButton.setOnClickListener(view -> {
            startActivity(new Intent(this, LoginActivity.class));
        });
    }

}
