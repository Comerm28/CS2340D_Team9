package com.viewmodel;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.DatabaseError;

import com.model.Database;
import com.model.Destination;
import com.model.User;
import com.model.UserDestinationData;

import java.util.Date;
import java.util.List;
import java.util.function.Consumer;

public class CurrentUserInfo {
    private static CurrentUserInfo instance;

    private User user;
    private UserDestinationData userDestinationData;

    private DatabaseReference dbRef;

    private CurrentUserInfo() {
        dbRef = FirebaseDatabase.getInstance().getReference();
    }

    public static synchronized CurrentUserInfo getInstance() {
        if (instance == null) {
            instance = new CurrentUserInfo();
        }
        return instance;
    }

    public void setUser(User user) {
        this.user = user;
        dbRef.child("destinations").child(user.getUsername())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            userDestinationData = dataSnapshot.getValue(UserDestinationData.class);

                            if (userDestinationData == null) {
                                userDestinationData = new UserDestinationData(user.getUsername());
                                dbRef.child("destinations").child(user.getUsername())
                                        .setValue(userDestinationData);
                            }

                            userDestinationData.setUsername(user.getUsername());

                        } else {
                            userDestinationData = new UserDestinationData(user.getUsername());
                            dbRef.child("destinations")
                                    .child(user.getUsername()).setValue(userDestinationData);
                            userDestinationData.setUsername(user.getUsername());
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        // Handle possible errors perchance
                    }
                });

        dbRef.child("users").child(user.getUsername()).setValue(user);
    }

    public User getUser() {
        return user;
    }

    public void getDestinations(Consumer<List<Destination>> onLoad, Consumer<String> onFail) {
        if (userDestinationData.isCollaborating()) {
            Database.getInstance()
                    .getUserDestinationData(userDestinationData.getCollaboratorUsername(),
                        destinationData -> onLoad.accept(destinationData.getDestinations()),
                        onFail);
        } else {
            onLoad.accept(userDestinationData.getDestinations());
        }
    }

    public UserDestinationData getUserDestinationData() {
        return userDestinationData;
    }

    public void getAllottedVacationDays(Consumer<Integer> onLoad, Consumer<String> onFail) {
        if (userDestinationData.isCollaborating()) {
            Database.getInstance()
                    .getUserDestinationData(userDestinationData.getCollaboratorUsername(),
                        destinationData -> onLoad.accept(user.getAllottedVacationDays()),
                        onFail);
        } else {
            onLoad.accept(user.getAllottedVacationDays());
        }
    }

    public void getNotes(Consumer<List<String>> onLoad, Consumer<String> onFail) {
        if (userDestinationData.isCollaborating()) {
            Database.getInstance()
                    .getUserDestinationData(userDestinationData.getCollaboratorUsername(),
                        destinationData -> onLoad.accept(destinationData.getNotes()),
                        onFail);
        } else {
            onLoad.accept(userDestinationData.getNotes());
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

    public void getPlannedDays(Consumer<Integer> onLoad, Consumer<String> onFail) {
        getDestinations(destinations -> {
            int sum = 0;
            for (Destination d : destinations) {
                sum += (int) d.getDurationDays();
            }
            onLoad.accept(sum);
        }, onFail);
    }

    public Date getUserActualDateAndTime()
    {
        //todo get user date and time and store it in date object prob, might do something else but
        // that makes sense for now
        return new Date();
    }
}