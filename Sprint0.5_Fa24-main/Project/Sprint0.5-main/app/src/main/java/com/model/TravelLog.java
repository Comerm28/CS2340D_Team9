package com.model;

public class TravelLog {
    private String destination;
    private int daysPlanned;

    public TravelLog(String destination, int daysPlanned) {
        this.destination = destination;
        this.daysPlanned = daysPlanned;
    }

    public String getDestination() {
        return destination;
    }

    public int getDaysPlanned() {
        return daysPlanned;
    }
}
