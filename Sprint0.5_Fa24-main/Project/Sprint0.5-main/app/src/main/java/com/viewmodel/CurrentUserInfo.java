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
import com.model.TravelEntryData;
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
        dbRef.child("users").child(user.getUsername())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            userDestinationData = dataSnapshot.getValue(UserDestinationData.class);

                            if (userDestinationData == null) {
                                user = new User(user.getUsername());
                                dbRef.child("users").child(user.getUsername())
                                        .setValue(user);
                            }

                            user.setUsername(user.getUsername());

                        } else {
                            user = new User(user.getUsername());
                            dbRef.child("users")
                                    .child(user.getUsername()).setValue(user);
                            user.setUsername(user.getUsername());
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        // Handle possible errors perchance
                    }
                });
        String listeningUser;
        if (user.isCollaborating()) {
            listeningUser = user.getCollaboratorUsername();
        } else {
            listeningUser = user.getUsername();
        }
        dbRef.child("destinations").child(listeningUser)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            userDestinationData = dataSnapshot.getValue(UserDestinationData.class);

                            if (userDestinationData == null) {
                                userDestinationData = new UserDestinationData(listeningUser);
                                dbRef.child("destinations").child(listeningUser)
                                        .setValue(userDestinationData);
                            }

                            userDestinationData.setUsername(listeningUser);

                        } else {
                            userDestinationData = new UserDestinationData(listeningUser);
                            dbRef.child("destinations")
                                    .child(listeningUser).setValue(userDestinationData);
                            userDestinationData.setUsername(listeningUser);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        // Handle possible errors perchance
                    }
                });
        dbRef.child("dining").child(listeningUser)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            userDiningData = dataSnapshot.getValue(UserDiningData.class);

                            if (userDiningData == null) {
                                userDiningData = new UserDiningData(listeningUser);
                                dbRef.child("dining").child(listeningUser)
                                        .setValue(userDiningData);
                            }

                            userDiningData.setUsername(listeningUser);

                        } else {
                            userDiningData = new UserDiningData(listeningUser);
                            dbRef.child("dining")
                                    .child(listeningUser).setValue(userDiningData);
                            userDiningData.setUsername(listeningUser);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        // Handle possible errors perchance
                    }
                });
        dbRef.child("accommodations").child(listeningUser)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            userAccommodationData =
                                    dataSnapshot.getValue(UserAccommodationData.class);

                            if (userAccommodationData == null) {
                                userAccommodationData = new UserAccommodationData(listeningUser);
                                dbRef.child("accommodations").child(listeningUser)
                                        .setValue(userAccommodationData);
                            }

                            userAccommodationData.setUsername(listeningUser);

                        } else {
                            userAccommodationData = new UserAccommodationData(listeningUser);
                            dbRef.child("accommodations")
                                    .child(listeningUser).setValue(userAccommodationData);
                            userAccommodationData.setUsername(listeningUser);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        // Handle possible errors perchance
                    }
                });
        dbRef.child("community").child(listeningUser)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            communityTravelEntriesData =
                                    dataSnapshot.getValue(CommunityTravelEntriesData.class);

                            if (communityTravelEntriesData == null) {
                                communityTravelEntriesData = new CommunityTravelEntriesData();
                                dbRef.child("community")
                                        .setValue(communityTravelEntriesData);
                            }
                        } else {
                            communityTravelEntriesData = new CommunityTravelEntriesData();
                            dbRef.child("community")
                                    .setValue(communityTravelEntriesData);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        // Handle possible errors perchance
                    }
                });
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

    public CommunityTravelEntriesData getCommunityTravelEntriesData() { return communityTravelEntriesData; }

    public void getAllottedVacationDays(Consumer<Integer> onLoad, Consumer<String> onFail) {
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

    public void getPlannedDays(Consumer<Integer> onLoad, Consumer<String> onFail) {
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