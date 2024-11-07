package com.model;

import java.util.ArrayList;
import java.util.List;

public class UserDiningData {
    private String username;
    private List<Reservation> reservations;

    public UserDiningData() {
        reservations = new ArrayList<>();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public interface OnReservationChangeListener {
        void onReservationAdded(Reservation reservation);
    }

    public UserDiningData(String username) {
        username = username;
        reservations = new ArrayList<>();
    }

    public void addReservation(Reservation reservation) {
        reservations.add(reservation);
    }

    public List<Reservation> getReservations() {
        return reservations;
    }
}