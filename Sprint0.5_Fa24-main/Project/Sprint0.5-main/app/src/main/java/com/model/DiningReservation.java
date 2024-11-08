package com.model;

import java.util.Date;
import java.text.SimpleDateFormat;

public class DiningReservation extends Reservation {
    private Date dateAndTime;
    public DiningReservation() {

    }

    public DiningReservation(String location, String website, Date dateAndTime) {
        super(location, website);
        this.dateAndTime = dateAndTime;
    }

    public Date getDateAndTime() {
        return dateAndTime;
    }

    public void setDateAndTime(Date dateAndTime) {
        this.dateAndTime = dateAndTime;
    }

    public String parseDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
        return dateFormat.format(dateAndTime);
    }

    public String parseTime() {
        SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm");
        return timeFormat.format(dateAndTime);
    }
}
