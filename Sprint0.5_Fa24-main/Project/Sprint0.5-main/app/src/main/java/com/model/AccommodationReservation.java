package com.model;

import java.text.SimpleDateFormat;
import java.util.Date;

public class AccommodationReservation extends Reservation {
    private Date checkInDate;
    private Date checkOutDate;
    private int numRooms;
    private RoomType roomType;

    public AccommodationReservation() {

    }

    public AccommodationReservation(String location, String website, Date checkInDate,
                                    Date checkOutDate, int numRooms, RoomType roomType) {
        super(location, website);
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.numRooms = numRooms;
        this.roomType = roomType;
    }

    public String parseCheckInDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
        return dateFormat.format(checkInDate);
    }

    public String parseCheckOutDate() {
        SimpleDateFormat timeFormat = new SimpleDateFormat("MM-dd-yyyy");
        return timeFormat.format(checkOutDate);
    }

    public Date getCheckInDate() {
        return checkInDate;
    }

    public void setCheckInDate(Date checkInDate) {
        this.checkInDate = checkInDate;
    }

    public Date getCheckOutDate() {
        return checkOutDate;
    }

    public void setCheckOutDate(Date checkOutDate) {
        this.checkOutDate = checkOutDate;
    }

    public int getNumRooms() {
        return numRooms;
    }

    public void setNumRooms(int numRooms) {
        this.numRooms = numRooms;
    }

    public RoomType getRoomType() {
        return roomType;
    }

    public void setRoomType(RoomType roomType) {
        this.roomType = roomType;
    }

    public enum RoomType {
        Single("Single"),
        KingSuite("King Suite"),
        Deluxe("Deluxe");

        public final String displayString;

        RoomType(String displayString) {
            this.displayString = displayString;
        }
    }
}
