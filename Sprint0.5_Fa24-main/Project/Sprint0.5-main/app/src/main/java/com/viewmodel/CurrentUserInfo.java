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


import com.model.Database;
import com.model.Destination;
import com.model.User;
import com.model.UserDestinationData;

import java.util.List;

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

                    if(userDestinationData == null)
                    {
                        userDestinationData = new UserDestinationData(user.getUsername());
                        dbRef.child("destinations").child(user.getUsername()).setValue(userDestinationData);
                    }

                    userDestinationData.setUsername(user.getUsername());

                } else {
                    userDestinationData = new UserDestinationData(user.getUsername());
                    dbRef.child("destinations").child(user.getUsername()).setValue(userDestinationData);
                    userDestinationData.setUsername(user.getUsername());
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

    public List<Destination> getDestinations()
    {
        if(userDestinationData.isCollaborating())
        {
            return Database.getInstance().getUserDestinationData(userDestinationData.getCollaboratorUsername()).getDestinations();
        }
        else{
            return userDestinationData.getDestinations();
        }
    }

    public UserDestinationData getUserDestinationData() {
        return userDestinationData;
    }

    public int getAllottedVacationDays()
    {
        if(userDestinationData.isCollaborating())
        {
            return Database.getInstance().getUserDestinationData(userDestinationData.getCollaboratorUsername()).getAllotedVacationDays();
        }
        else{
            return userDestinationData.getAllotedVacationDays();
        }
    }

    public List<String> getNotes()
    {
        if(userDestinationData.isCollaborating())
        {
            return Database.getInstance().getUserDestinationData(userDestinationData.getCollaboratorUsername()).getNotes();
        }
        else{
            return userDestinationData.getNotes();
        }
    }

    public boolean isLoggedIn() {
        return user != null;
    }

    public void clearUser() {
        user = null;
    }

    public void updateDestinationData() {
        Database.getInstance().updateDestinationData(user, userDestinationData);
    }
}
