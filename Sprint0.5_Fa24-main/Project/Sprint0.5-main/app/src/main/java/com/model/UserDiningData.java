package com.model;

import java.util.ArrayList;
import java.util.List;

public class UserDiningData {
    private List<Reservation> reservations;
    private List<OnReservationChangeListener> listeners;

    public interface OnReservationChangeListener {
        void onReservationAdded(Reservation reservation);
    }

    public UserDiningData() {
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