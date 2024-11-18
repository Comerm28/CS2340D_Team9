package com.model;

public class Post {
    public String username;
    public String destination;
    public String duration;
    public String accommodations;
    public String diningReservation;
    public String transportation;
    public String notes;

    public Post(String username, String destination, String duration, String accommodations, String diningReservation, String transportation, String notes) {
        this.username = username;
        this.destination = destination;
        this.duration = duration;
        this.accommodations = accommodations;
        this.diningReservation = diningReservation;
        this.transportation = transportation;
        this.notes = notes;
    }
}
