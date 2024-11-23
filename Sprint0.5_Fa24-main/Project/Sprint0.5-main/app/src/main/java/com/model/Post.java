package com.model;

import java.util.Date;

public class Post {
    public String username;
    public String destination;
    public Date startDate;
    public Date endDate;
    public String accommodations;
    public String diningReservation;
    public String notes;
    public int rating;

    public Post() {}

    public Post(String username, String destination, Date startDate, Date endDate, String accommodations, String diningReservation, int rating, String notes) {
        this.username = username;
        this.destination = destination;
        this.startDate = startDate;
        this.endDate = endDate;
        this.accommodations = accommodations;
        this.diningReservation = diningReservation;
        this.rating = rating;
        this.notes = notes;
    }
}
