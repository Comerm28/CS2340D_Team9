package com.model;

import java.util.ArrayList;
import java.util.List;

public class UserDiningData {
    private String username;
    private List<Reservation> reservations;
    private List<OnReservationChangeListener> listeners;

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
        listeners = new ArrayList<>();
    }

    public void addReservation(Reservation reservation) {
        reservations.add(reservation);
        for (OnReservationChangeListener listener : listeners) {
            listener.onReservationAdded(reservation);
        }
    }

    public void addListener(OnReservationChangeListener listener) {
        listeners.add(listener);
    }

    public List<Reservation> getReservations() {
        return reservations;
    }
}