package com.model;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.viewmodel.CurrentUserInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class Database {
    private final String users;
    private final String destinations;
    private final String accommodations;
    private static Database instance;
    private final DatabaseReference dbRef;
    private final FirebaseAuth mAuth;

    private Database() {
        dbRef = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        this.users = "users";
        this.destinations = "destinations";
        this.accommodations = "accommodations";
    }

    public static Database getInstance() {
        if (instance == null) {
            instance = new Database();
        }

        return instance;
    }

    public void signUp(String username, String password, Runnable onSuccess,
                       Consumer<String> onFail) {
        // attempt to create the user, and if successful, call onSuccess,
        // else call onFail with the error message
        mAuth.createUserWithEmailAndPassword(User.formatEmail(username), password)
                .addOnSuccessListener(task -> {
                    CurrentUserInfo.getInstance().setUser(new User(username));
                    onSuccess.run();
                }).addOnFailureListener(fail ->
                        onFail.accept(fail.getMessage().replace("email address",
                                "username")));
    }

    public void logIn(String username, String password, Runnable onSuccess,
                      Consumer<String> onFail) {
        // attempt to create the user, and if successful, call onSuccess,
        // else call onFail with the error message
        mAuth.signInWithEmailAndPassword(User.formatEmail(username), password)
                .addOnSuccessListener(task -> {
                    CurrentUserInfo currentUserInfo = CurrentUserInfo.getInstance();
                    dbRef.child(users).child(username).addListenerForSingleValueEvent(
                            new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    if (!snapshot.exists()) {
                                        onFail.accept("Failed to log in.");
                                        return;
                                    }

                                    currentUserInfo.setUser(snapshot.getValue(User.class));
                                    onSuccess.run();
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {
                                    onFail.accept("Failed to log in.");
                                }
                            }
                    );
                })
                .addOnFailureListener(fail -> onFail.accept(fail.getMessage().
                        replace("email address", "username")));
    }

    public void updateUserData(User user) {
        dbRef.child(users).child(user.getUsername()).setValue(user);
    }

    public void updateDestinationData(User user, UserDestinationData userDestinationData) {
        if (user.isCollaborating()) {
            dbRef.child(destinations).child(
                user.getCollaboratorUsername()
            ).setValue(userDestinationData);
        } else {
            dbRef.child(destinations).child(user.getUsername()).setValue(userDestinationData);
        }
    }

    public void updateDiningData(User user, UserDiningData userDiningData) {
        if (user.isCollaborating()) {
            dbRef.child("dining").child(user.getCollaboratorUsername()).setValue(userDiningData);
        } else {
            dbRef.child("dining").child(user.getUsername()).setValue(userDiningData);
        }
    }

    public void updateUserAccommodationData(User user, UserAccommodationData data) {
        if (user.isCollaborating()) {
            dbRef.child(accommodations).child(user.getCollaboratorUsername()).setValue(data);
        } else {
            dbRef.child(accommodations).child(user.getUsername()).setValue(data);
        }
    }

    public void addCommunityPost(Post post) {
        dbRef.child("community").push().setValue(post);
    }

    public void checkUser(String username, Consumer<User> dataLoaded,
                          Consumer<String> onFail) {
        dbRef.child(users).child(username)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            dataLoaded.accept(dataSnapshot.getValue(User.class));
                        } else {
                            onFail.accept("User not present");
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        onFail.accept(databaseError.getMessage());
                    }
                });
    }

    public void getUserDestinationData(String username, Consumer<UserDestinationData> dataLoaded,
                                       Consumer<String> onFail) {
        dbRef.child(destinations).child(username)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            dataLoaded.accept(dataSnapshot.getValue(UserDestinationData.class));
                        } else {
                            onFail.accept("User not present.");
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        onFail.accept(databaseError.getMessage());
                    }
                });
    }

    public void getUserAccommodationData(
            String username, Consumer<UserAccommodationData> dataLoaded,
                                       Consumer<String> onFail) {
        dbRef.child(accommodations).child(username)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            dataLoaded.accept(dataSnapshot.getValue(UserAccommodationData.class));
                        } else {
                            onFail.accept("User not present.");
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        onFail.accept(databaseError.getMessage());
                    }
                });
    }

    public void getCommunityPosts(Consumer<List<Post>> dataLoaded, Consumer<String> onFail) {
        dbRef.child("community")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            List<Post> posts = new ArrayList<>();
                            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                posts.add(snapshot.getValue(Post.class));
                            }
                            dataLoaded.accept(posts);
                        } else {
                            onFail.accept("not present");
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        onFail.accept(databaseError.getMessage());
                    }
                });
    }
}
