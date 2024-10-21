package com.viewmodel;

import androidx.lifecycle.ViewModel;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.model.Database;
import com.model.User;

import java.util.function.Consumer;

public class AuthenticationViewModel extends ViewModel {
    public void signUp(String username, String password, Runnable onSuccess, Consumer<String> onFail) {
        // validate login info format
        if (!isValidLogin(username, password)) {
            onFail.accept("Login info improperly formatted. "
                    + "Cannot have whitespace or invalid characters.");
            return;
        }

        Database.getInstance().signUp(User.formatEmail(username), password, onSuccess, onFail);
    }

    public void logIn(String username, String password, Runnable onSuccess, Consumer<String> onFail) {
        // validate login info format
        if (!isValidLogin(username, password)) {
            onFail.accept("Login info improperly formatted. "
                    + "Cannot have whitespace or invalid characters.");
            return;
        }

        Database.getInstance().logIn(User.formatEmail(username), password, onSuccess, onFail);
    }


    // returns whether the login information is properly formatted
    private boolean isValidLogin(String username, String password) {
        if (username == null || password == null
                || username.trim().isEmpty() || password.trim().isEmpty()) {
            return false;
        }
        return username.matches("^[a-zA-Z0-9_]+$");
    }
}
