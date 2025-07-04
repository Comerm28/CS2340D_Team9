package com.model;

import java.util.ArrayList;
import java.util.List;

public class UserDiningData {
    private String username;
    private List<DiningReservation> reservations;

    public UserDiningData() {
        reservations = new ArrayList<>();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public UserDiningData(String username) {
        this.username = username;
        reservations = new ArrayList<>();
    }

    public List<DiningReservation> getReservations() {
        return reservations;
    }

    public void addReservation(DiningReservation reservation) {
        reservations.add(reservation);
    }


    public interface OnReservationChangeListener {
        void onReservationAdded(Reservation reservation);
    }

}