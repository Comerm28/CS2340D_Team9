package com.model;

import androidx.lifecycle.ViewModel;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.model.User;
import com.viewmodel.CurrentUserInfo;

import java.util.function.Consumer;

public class Database {
    private static Database instance;
    private final DatabaseReference dbRef;
    private final FirebaseAuth mAuth;

    private Database() {
        dbRef = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
    }

    public static Database getInstance() {
        if (instance == null) {
            instance = new Database();
        }

        return instance;
    }

    public void signUp(String username, String password, Runnable onSuccess, Consumer<String> onFail) {
        // attempt to create the user, and if successful, call onSuccess,
        // else call onFail with the error message
        mAuth.createUserWithEmailAndPassword(User.formatEmail(username), password)
                .addOnSuccessListener(task -> {
                    dbRef.child("users").child(username).setValue(new User(username));
                    onSuccess.run();
                }).addOnFailureListener(fail ->
                        onFail.accept(fail.getMessage().replace("email address", "username"))
                );
    }

    public void logIn(String username, String password, Runnable onSuccess, Consumer<String> onFail) {
        // attempt to create the user, and if successful, call onSuccess,
        // else call onFail with the error message
        mAuth.signInWithEmailAndPassword(User.formatEmail(username), password)
                .addOnSuccessListener(task -> loginSuccess(username, onSuccess))
                .addOnFailureListener(fail -> onFail.accept(fail.getMessage().
                        replace("email address", "username")));
    }

    public void loginSuccess(String username, Runnable onSuccess)
    {
        CurrentUserInfo currentUserInfo = CurrentUserInfo.getInstance();
        User user = new User(username);
        currentUserInfo.setUser(user);
        onSuccess.run();
    }

    public void updateDestinationData(User user, UserDestinationData userDestinationData)
    {
        dbRef.child("destinations").child(user.getUsername()).setValue(userDestinationData);
    }

    public String checkUser(String username)
    {
        String[] user = new String[1];
        dbRef.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    user[0] = dataSnapshot.getValue(String.class);
                } else {
                    user[0] = null;
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("Error: " + databaseError.getMessage());
            }
        });

        return user[0];
    }

    public UserDestinationData getUserDestinationData(String username)
    {
        UserDestinationData[] user = new UserDestinationData[1];
        dbRef.child("destinations").child(username).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    user[0] = dataSnapshot.getValue(UserDestinationData.class);
                } else {
                    user[0] = null;
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("Error: " + databaseError.getMessage());
            }
        });

        return user[0];
    }
}
