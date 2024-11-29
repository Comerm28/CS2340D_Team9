package com.viewmodel;

import androidx.lifecycle.ViewModel;

import com.model.Database;

import java.util.function.Consumer;

public class AuthenticationViewModel extends ViewModel {
    public void signUp(String username, String password,
                       Runnable onSuccess, Consumer<String> onFail) {
        // validate login info format
        if (!isValidLogin(username, password)) {
            onFail.accept("Login info improperly formatted. "
                    + "Cannot have whitespace or invalid characters.");
            return;
        }
        Database.getInstance().signUp(username,
                password, onSuccess, onFail);
    }

    public void logIn(String username, String password,
                      Runnable onSuccess, Consumer<String> onFail) {
        // validate login info format
        if (!isValidLogin(username, password)) {
            onFail.accept("Login info improperly formatted. "
                    + "Cannot have whitespace or invalid characters.");
            return;
        }

        Database.getInstance().logIn(username, password, onSuccess, onFail);

    }


    // returns whether the login information is properly formatted
    private boolean isValidLogin(String username, String password) {
        if (username == null || password == null
                || username.trim().isEmpty() || password.trim().isEmpty()) {
            return false;
        }
        return username.matches("^\\w+$");
    }
}