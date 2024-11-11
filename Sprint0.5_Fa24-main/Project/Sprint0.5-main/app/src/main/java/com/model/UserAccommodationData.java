package com.model;

import java.util.List;
import java.util.ArrayList;

public class UserAccommodationData {
    private String username;
    private List<AccommodationReservation> reservations;

    public UserAccommodationData() {
        reservations = new ArrayList<>();
    }

    public UserAccommodationData(String username) {
        this.username = username;
        reservations = new ArrayList<>();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String newName) {
        this.username = newName;
    }

    public void addReservation(AccommodationReservation reservation) {
        reservations.add(reservation);
    }

    public List<AccommodationReservation> getReservations() {
        return reservations;
    }
}