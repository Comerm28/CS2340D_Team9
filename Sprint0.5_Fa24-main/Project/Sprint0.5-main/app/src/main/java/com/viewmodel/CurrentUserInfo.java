package com.viewmodel;
import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.DatabaseError;


import com.model.User;
import com.model.UserDestinationData;

public class CurrentUserInfo {
    private static CurrentUserInfo instance;

    private User user;
    private UserDestinationData userDestinationData;

    private DatabaseReference dbRef;

    private CurrentUserInfo() {
        dbRef = FirebaseDatabase.getInstance().getReference();
    }

    // Method to get the singleton instance
    public static synchronized CurrentUserInfo getInstance() {
        if (instance == null) {
            instance = new CurrentUserInfo();
        }
        return instance;
    }

    // Method to set the current user
    public void setUser(User user) {
        this.user = user;
        dbRef.child("destinations").child(user.getUsername()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // Check if the user exists
                if (dataSnapshot.exists()) {
                    // Get the User object from the snapshot
                    userDestinationData = dataSnapshot.getValue(UserDestinationData.class);

                } else {
                    // Handle the case where the user does not exist
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle possible errors
            }
        });
    }

    public User getUser() {
        return user;
    }

    public UserDestinationData getUserDestinationData() {
        return userDestinationData;
    }

    public boolean isLoggedIn() {
        return user != null;
    }

    public void clearUser() {
        user = null;
    }
}
