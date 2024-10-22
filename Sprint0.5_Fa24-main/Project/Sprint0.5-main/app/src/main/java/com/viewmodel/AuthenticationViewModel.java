package com.viewmodel;

import android.util.Log;

import androidx.lifecycle.ViewModel;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.model.Database;
import com.model.User;
import com.model.UserDestinationData;

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

    public void loginSuccess(String username, Runnable onSuccess)
    {
        CurrentUserInfo currentUserInfo = CurrentUserInfo.getInstance();
        User user = new User(username);
        currentUserInfo.setUser(user);
        onSuccess.run();
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
