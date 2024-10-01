package com.model;

import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DatabaseReference;

public class Authenticator {
    private DatabaseReference dbRef;

    public Authenticator(){
        dbRef = FirebaseDatabase.getInstance().getReference();
    }

    public boolean addAccount(String username, String password){
        dbRef.child(username).setValue(password);
        return true;
    }

    public boolean authenticate(String username, String password){
        //todo: authenticate?
        return true;
    }

    public int getFirebaseData(){
        //todo: implement
        //return: idk what this should return, but am using an int so I can write my code
        return 0;
    }
}
