package com.model;

import java.util.ArrayList;
import java.util.List;

public class User {
    private String username;
    private List<Destination> destinations;

    public User(String username) {
        this.username = username;
        this.destinations = new ArrayList<>();
    }

    public static String formatEmail(String username) {
        return username + "@a.com";
    }

    public String getUsername() {
        return username;
    }
<<<<<<< HEAD
=======

    public List<Destination> getDestinations() {
        return destinations;
    }

>>>>>>> main
    public void setUsername(String username) {
        this.username = username;
    }

}