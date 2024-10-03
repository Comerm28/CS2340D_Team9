package com.viewmodel;

import androidx.lifecycle.ViewModel;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.model.User;

import java.util.function.Consumer;

public class AuthenticationViewModel extends ViewModel {
    private final DatabaseReference dbRef;
    private final FirebaseAuth mAuth;

    public AuthenticationViewModel() {
        dbRef = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
    }

    public void signUp(String username, String password, Runnable onSuccess, Consumer<String> onFail) {
        // username contains invalid characters, so fail
        if (!User.isValidUsername(username)) {
            onFail.accept("Username may not contain special characters.");
            return;
        }

        // attempt to create the user, and if successful, call onSuccess,
        // else call onFail with the error message
        mAuth.createUserWithEmailAndPassword(User.formatEmail(username), password)
                .addOnSuccessListener(task -> {
                    dbRef.child("users").child(username).setValue(new User(username));
                    onSuccess.run();
                }).addOnFailureListener(fail -> onFail.accept(fail.getMessage().replace("email address", "username")));
    }

    public void logIn(String username, String password, Runnable onSuccess, Consumer<String> onFail) {
        // username contains invalid characters, so fail
        if (!User.isValidUsername(username)) {
            onFail.accept("Username may not contain special characters.");
            return;
        }

        // attempt to create the user, and if successful, call onSuccess,
        // else call onFail with the error message
        mAuth.signInWithEmailAndPassword(User.formatEmail(username), password)
                .addOnSuccessListener(task -> onSuccess.run())
                .addOnFailureListener(fail -> onFail.accept(fail.getMessage().replace("email address", "username")));
    }

}
