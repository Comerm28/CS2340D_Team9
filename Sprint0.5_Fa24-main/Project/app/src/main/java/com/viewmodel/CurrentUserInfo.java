package com.viewmodel;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.DatabaseError;

import com.model.CommunityTravelEntriesData;
import com.model.Database;
import com.model.Destination;
import com.model.User;
import com.model.UserAccommodationData;
import com.model.UserDestinationData;
import com.model.UserDiningData;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.function.Consumer;

public class CurrentUserInfo {
    private static CurrentUserInfo instance;

    private User user;
    private UserDestinationData userDestinationData;
    private UserDiningData userDiningData;
    private UserAccommodationData userAccommodationData;
    private CommunityTravelEntriesData communityTravelEntriesData;

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

    public void setUser(User userToBe) {
        this.user = userToBe;
    
        final String USERS = "users";
        final String DESTINATIONS = "destinations";
        final String DINING = "dining";
        final String ACCOMMODATIONS = "accommodations";
    
        updateUserData(user.getUsername(), USERS, UserDestinationData.class);
        
        String listeningUser = user.isCollaborating() ? user.getCollaboratorUsername() : user.getUsername();
        
        updateUserData(listeningUser, DESTINATIONS, UserDestinationData.class);
        updateUserData(listeningUser, DINING, UserDiningData.class);
        updateUserData(listeningUser, ACCOMMODATIONS, UserAccommodationData.class);
    }
    
    private <T> void updateUserData(String username, String dataType, Class<T> dataClass) {
        dbRef.child(dataType).child(username).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                T userData = dataSnapshot.getValue(dataClass);
    
                if (userData == null) {
                    try {
                        userData = dataClass.getConstructor(String.class).newInstance(username);
                    } catch (Exception e) {
                        throw new RuntimeException("Error creating instance of " + dataClass.getName(), e);
                    }
                    dbRef.child(dataType).child(username).setValue(userData);
                }
    
                if (userData instanceof UserDestinationData) {
                    ((UserDestinationData) userData).setUsername(username);
                } else if (userData instanceof UserDiningData) {
                    ((UserDiningData) userData).setUsername(username);
                } else if (userData instanceof UserAccommodationData) {
                    ((UserAccommodationData) userData).setUsername(username);
                }
            }
    
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getContext(), "Failed to load " + dataType, Toast.LENGTH_SHORT).show();
                displayEmptyData(dataType);
            }
        });
    }
    
    private void displayEmptyData(String dataType) {
        switch (dataType) {
            case "destinations":
                displayEmptyPieChart();
                break;
            case "dining":
                break;
            case "accommodations":
                break;
            default:
                break;
        }
    }    

    public User getUser() {
        return user;
    }

    public void getDestinations(Consumer<List<Destination>> onLoad, Consumer<String> onFail) {
        if (user.isCollaborating()) {
            Database.getInstance()
                    .getUserDestinationData(user.getCollaboratorUsername(),
                        destinationData -> onLoad.accept(destinationData.getDestinations()),
                        onFail);
        } else {
            onLoad.accept(userDestinationData.getDestinations());
        }
    }

    public UserDestinationData getUserDestinationData() {
        return userDestinationData;
    }

    public UserDiningData getUserDiningData() {
        return userDiningData;
    }

    public UserAccommodationData getUserAccommodationData() {
        return userAccommodationData;
    }

    public CommunityTravelEntriesData getCommunityTravelEntriesData() {
        return communityTravelEntriesData;
    }

    public void getAllottedVacationDays(IntConsumer onLoad, Consumer<String> onFail) {
        if (user.isCollaborating()) {
            Database.getInstance().checkUser(user.getCollaboratorUsername(),
                    data -> {
                        if (data != null) {
                            onLoad.accept(data.getAllottedVacationDays());
                        } else {
                            onLoad.accept(0);
                        }
                    },
                    onFail);
        } else {
            onLoad.accept(user.getAllottedVacationDays());
        }
    }

    public void getNotes(Consumer<List<String>> onLoad, Consumer<String> onFail) {
        if (user.isCollaborating()) {
            Database.getInstance()
                    .getUserDestinationData(user.getCollaboratorUsername(),
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

    public void getPlannedDays(IntConsumer onLoad, Consumer<String> onFail) {
        getDestinations(destinations -> {
            int sum = 0;
            for (Destination d : destinations) {
                sum += (int) d.getDurationDays();
            }
            onLoad.accept(sum);
        }, onFail);
    }

    public Date getUserActualDateAndTime() {
        Instant now = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            now = Instant.now();
        }
        Date date = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            date = Date.from(now);
        }
        return date;
    }
}