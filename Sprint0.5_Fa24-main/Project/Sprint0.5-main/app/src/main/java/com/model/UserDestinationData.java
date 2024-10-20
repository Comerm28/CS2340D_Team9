package com.model;

import java.util.ArrayList;

public class UserDestinationData {
    private String username;
    private ArrayList<Destination> destinations;

    public UserDestinationData(String username, ArrayList<Destination> destinations)
    {
        this.username = username;
        this.destinations = destinations;
    }

    public UserDestinationData(String username)
    {
        this.username = username;
        this.destinations = new ArrayList<>();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public ArrayList<Destination> getDestinations() {
        return destinations;
    }

    public void setDestinations(ArrayList<Destination> destinations) {
        this.destinations = destinations;
    }
}
