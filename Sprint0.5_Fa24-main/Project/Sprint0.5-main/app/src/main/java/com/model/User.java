package com.model;

import java.util.ArrayList;
import java.util.List;

public class User {
    private String username;

    public User(String username) {
        this.username = username;
    }

    public static String formatEmail(String username) {
        return username + "@a.com";
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}