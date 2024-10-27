package com.model;

import com.viewmodel.DestinationViewModel;

import java.util.ArrayList;
import java.util.List;

public class UserDestinationData {
    private String username;
    private List<Destination> destinations;
    private List<String> notes;
    private int allotedVacationDays;
    private boolean isCollaborating;
    private String collaboratorUsername;

    public UserDestinationData(){ }

    public UserDestinationData(String username, List<Destination> destinations)
    {
        this.username = username;
        this.destinations = destinations;
        this.allotedVacationDays = 0;
        isCollaborating = false;
        collaboratorUsername = "";
        notes = new ArrayList<>();
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

    public List<Destination> getDestinations() {
        return destinations;
    }

    public void setDestinations(List<Destination> destinations) {
        this.destinations = destinations;
    }

    public void addDestination(Destination destination)
    {
        if(destinations == null)
        {
            this.destinations = new ArrayList<>();
        }
        destinations.add(destination);
    }

    public int getAllotedVacationDays() {
        return allotedVacationDays;
    }

    public void setAllotedVacationDays(int allotedVacationDays) {
        this.allotedVacationDays = allotedVacationDays;
    }

    public boolean isCollaborating() {
        return isCollaborating;
    }

    public void setCollaborating(boolean collaborating) {
        isCollaborating = collaborating;
    }

    public String getCollaboratorUsername() {
        return collaboratorUsername;
    }

    public void setCollaboratorUsername(String collaboratorUsername) {
        this.collaboratorUsername = collaboratorUsername;
    }

    public List<String> getNotes() {
        return notes;
    }

    public void addNote(String note) {
        notes.add(note);
    }
}
