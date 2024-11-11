package com.example.sprintproject;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

import com.model.AccommodationReservation;
import com.model.UserAccommodationData;

import org.junit.Test;

import java.util.Date;

public class UserAccommodationDataTest {

    @Test
    public void testUsername() {
        UserAccommodationData userAccommodationData = new UserAccommodationData("testUser");
        assertEquals("testUser", userAccommodationData.getUsername());

        userAccommodationData.setUsername("newTestUser");
        assertEquals("newTestUser", userAccommodationData.getUsername());
    }

    @Test
    public void testAddReservation() {
        UserAccommodationData userAccommodationData = new UserAccommodationData("testUser");
        AccommodationReservation reservation = new AccommodationReservation("Hotel1", new Date(), new Date(), 3, AccommodationReservation.RoomType.Deluxe);

        assertEquals(0, userAccommodationData.getReservations().size());

        userAccommodationData.addReservation(reservation);
        assertEquals(1, userAccommodationData.getReservations().size());
        assertEquals(reservation, userAccommodationData.getReservations().get(0));
    }

    @Test
    public void testGetReservationsEmpty() {
        UserAccommodationData emptyUserAccommodationData = new UserAccommodationData("emptyUser");
        assertTrue(emptyUserAccommodationData.getReservations().isEmpty());
    }

    @Test
    public void testGetReservations() {
        UserAccommodationData userAccommodationData = new UserAccommodationData("testUser");
        AccommodationReservation reservation = new AccommodationReservation("Hotel1", new Date(), new Date(), 3, AccommodationReservation.RoomType.Deluxe);

        userAccommodationData.addReservation(reservation);
        assertFalse(userAccommodationData.getReservations().isEmpty());
        assertEquals(1, userAccommodationData.getReservations().size());
    }

    @Test
    public void testMultipleReservations() {
        UserAccommodationData userAccommodationData = new UserAccommodationData("testUser");
        AccommodationReservation reservation = new AccommodationReservation("Hotel1", new Date(), new Date(), 3, AccommodationReservation.RoomType.Deluxe);
        AccommodationReservation reservation2 = new AccommodationReservation("Hotel2", new Date(), new Date(), 3, AccommodationReservation.RoomType.Deluxe);

        userAccommodationData.addReservation(reservation);
        userAccommodationData.addReservation(reservation2);

        assertEquals(2, userAccommodationData.getReservations().size());
        assertEquals(reservation, userAccommodationData.getReservations().get(0));
        assertEquals(reservation2, userAccommodationData.getReservations().get(1));
    }
}