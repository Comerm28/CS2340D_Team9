package com.model;

import java.util.ArrayList;
import java.util.List;

public class UserAccommodationData {
    private String username;
    private List<AccommodationReservation> reservations;

    public UserAccommodationData() {
        reservations = new ArrayList<>();
    }

    public UserAccommodationData(String username){
        username = username;
        reservations = new ArrayList<>();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void addReservation(AccommodationReservation reservation) {
        reservations.add(reservation);
    }

    public List<AccommodationReservation> getReservations() {
        return reservations;
    }
}