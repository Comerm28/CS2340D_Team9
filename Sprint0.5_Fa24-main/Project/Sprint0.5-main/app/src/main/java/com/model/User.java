package com.model;

public class User {
    private String username;

    public User(String username) {
        this.username = username;
    }

    public static boolean isValidUsername(String username) {
        return !username.matches("^.*[^a-zA-Z0-9 ].*$");
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
