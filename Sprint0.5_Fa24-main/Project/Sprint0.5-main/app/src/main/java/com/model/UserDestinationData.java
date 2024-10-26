package com.model;

import com.viewmodel.DestinationViewModel;

import java.util.ArrayList;
import java.util.List;

public class UserDestinationData {
    private String username;
    private List<Destination> destinations;
    private int allotedVacationDays;

    public UserDestinationData(){ }

    public UserDestinationData(String username, List<Destination> destinations)
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
}
