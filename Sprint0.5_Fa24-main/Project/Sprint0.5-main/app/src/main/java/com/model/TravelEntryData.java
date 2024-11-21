package com.model;

import java.util.Date;

public class TravelEntryData {
    private Date startDate;
    private Date endDate;
    private Destination destination;
    private AccommodationReservation accomodation;
    private DiningReservation diningReservation;
    private int rating;

    public TravelEntryData(){
        startDate = new Date();
        endDate = new Date();
        destination = new Destination();
        accomodation = new AccommodationReservation();
        diningReservation = new DiningReservation();
        rating = 0;
    }

    public TravelEntryData(Date start, Date end, Destination dest, AccommodationReservation accom, DiningReservation dining, int rate) {
        startDate = start;
        endDate = end;
        destination = dest;
        accomodation = accom;
        diningReservation = dining;
        rating = rate;
    }

    public Destination getDestination() {
        return destination;
    }

    public void setDestination(Destination destination) {
        this.destination = destination;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public AccommodationReservation getAccomodation() {
        return accomodation;
    }

    public void setAccomodation(AccommodationReservation accomodation) {
        this.accomodation = accomodation;
    }

    public DiningReservation getDiningReservation() {
        return diningReservation;
    }

    public void setDiningReservation(DiningReservation diningReservation) {
        this.diningReservation = diningReservation;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
}
