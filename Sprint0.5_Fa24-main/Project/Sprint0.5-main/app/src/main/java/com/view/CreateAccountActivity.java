package com.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import com.example.sprintproject.R;
import com.viewmodel.LoginViewModel;

public class CreateAccountActivity extends AppCompatActivity {
    private LoginViewModel loginViewModel;
    private EditText createUsernameEditText;
    private EditText createPasswordEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account); // Reference to your create account layout

        loginViewModel = new LoginViewModel();
        createUsernameEditText = findViewById(R.id.createUsernameEditText);
        createPasswordEditText = findViewById(R.id.createPasswordEditText);
        Button registerButton = findViewById(R.id.registerButton);
        Button loginButton = findViewById(R.id.loginButton);

        // Handle register button click
        registerButton.setOnClickListener(view -> {
            // Logic for registration can be added here
            String username = createUsernameEditText.getText().toString();
            String password = createPasswordEditText.getText().toString();
            if(loginViewModel.onCreateAccount(username, password)){
                //Navigate back to the login page
                Intent intent = new Intent(CreateAccountActivity.this, LoginActivity.class);
                startActivity(intent);
                //todo: maybe bundle user name and passowrd and autofill them into the edittext on login screen?
            }
            else{
                //todo: if account has already been made with this username, maybe add bundle with just username when sending back to login screen
                //or just general error handling like white space and stuff, or like empty strings
            }

        });

        // Handle login button click
        loginButton.setOnClickListener(view -> {
            Intent intent = new Intent(CreateAccountActivity.this, LoginActivity.class);
            startActivity(intent);
        });
    }
}
