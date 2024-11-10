package com.model;


import java.util.ArrayList;
import java.util.List;

public class UserDestinationData {
    private String username;
    private List<Destination> destinations;
    private List<String> notes;

    public UserDestinationData() {
        this.destinations = new ArrayList<>();
        this.notes = new ArrayList<>();
    }

    public UserDestinationData(String username, List<Destination> destinations) {
        this.username = username;
        this.destinations = destinations;
        this.notes = new ArrayList<>();
    }

    public UserDestinationData(String username) {
        this.username = username;
        this.destinations = new ArrayList<>();
        this.notes = new ArrayList<>();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<Destination> getDestinations() {
        return destinations;
    }

    public void setDestinations(List<Destination> destinations) {
        this.destinations = destinations;
    }

    public void addDestination(Destination destination) {
        if (destinations == null) {
            this.destinations = new ArrayList<>();
        }
        destinations.add(destination);
    }

    public List<String> getNotes() {
        return notes;
    }

    public void addNote(String note) {
        notes.add(note);
    }
}
