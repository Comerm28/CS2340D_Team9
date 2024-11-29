package com.example.sprintproject;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.model.DiningReservation;
import com.viewmodel.sortingalgos.DateSort;
import com.viewmodel.sortingalgos.TimeSort;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SortTest {
    @Test
    public void testSortReservationsByTime() {
        // Create reservations with different times
        DiningReservation reservation1 = new DiningReservation("", "", new Date(2024, 10, 10, 12, 30)); // 12:30
        DiningReservation reservation2 = new DiningReservation("", "",new Date(2024, 10, 10, 9, 15));  // 9:15
        DiningReservation reservation3 = new DiningReservation("", "",new Date(2024, 10, 10, 17, 45)); // 17:45

        List<DiningReservation> reservations = new ArrayList<>();
        reservations.add(reservation1);
        reservations.add(reservation2);
        reservations.add(reservation3);

        TimeSort timeSort = new TimeSort();
        List<DiningReservation> sortedReservations = timeSort.sort(reservations);

        // Check that reservations are sorted by check-in time in ascending order
        assertEquals(reservation2, sortedReservations.get(0)); // 9:15
        assertEquals(reservation1, sortedReservations.get(1)); // 12:30
        assertEquals(reservation3, sortedReservations.get(2)); // 17:45
    }

    @Test
    public void testSortEmptyList() {
        List<DiningReservation> emptyList = new ArrayList<>();
        TimeSort timeSort = new TimeSort();

        // Sorting an empty list should return an empty list
        List<DiningReservation> sortedList = timeSort.sort(emptyList);

        assertTrue(sortedList.isEmpty());
    }

    @Test
    public void testSortSingleElementList() {
        DiningReservation singleReservation = new DiningReservation("", "",new Date(2024, 10, 10, 12, 30));
        List<DiningReservation> singleReservationList = new ArrayList<>();
        singleReservationList.add(singleReservation);

        TimeSort timeSort = new TimeSort();

        // Sorting a list with one element should not change the list
        List<DiningReservation> sortedList = timeSort.sort(singleReservationList);

        assertEquals(1, sortedList.size());
        assertEquals(singleReservation, sortedList.get(0));
    }

    @Test
    public void testSortReservationsWithSameTime() {
        DiningReservation reservation1 = new DiningReservation("", "",new Date(2024, 10, 10, 12, 30)); // 12:30
        DiningReservation reservation2 = new DiningReservation("", "",new Date(2024, 10, 10, 12, 30)); // 12:30
        DiningReservation reservation3 = new DiningReservation("", "",new Date(2024, 10, 10, 12, 30)); // 12:30

        List<DiningReservation> reservations = new ArrayList<>();
        reservations.add(reservation1);
        reservations.add(reservation2);
        reservations.add(reservation3);

        TimeSort timeSort = new TimeSort();
        List<DiningReservation> sortedReservations = timeSort.sort(reservations);

        // If the times are the same, the order should remain the same as the original list
        assertEquals(reservation1, sortedReservations.get(0));
        assertEquals(reservation2, sortedReservations.get(1));
        assertEquals(reservation3, sortedReservations.get(2));
    }

    @Test
    public void testSortReservationsByDate() {
        // Create DiningReservations with different dates
        DiningReservation reservation1 = new DiningReservation("", "",new Date(2024, 10, 10, 12, 30)); // 12:30
        DiningReservation reservation2 = new DiningReservation("", "",new Date(2024, 10, 10, 10, 15)); // 10:15
        DiningReservation reservation3 = new DiningReservation("", "",new Date(2024, 10, 10, 14, 45)); // 14:45

        // Add reservations to the list
        List<DiningReservation> reservations = new ArrayList<>();
        reservations.add(reservation1);
        reservations.add(reservation2);
        reservations.add(reservation3);

        // Create the DateSort instance
        DateSort dateSort = new DateSort();

        // Sort the reservations
        dateSort.sort(reservations);

        // Verify that the list is sorted by date (ascending)
        assertEquals(reservation2, reservations.get(0)); // 10:15
        assertEquals(reservation1, reservations.get(1)); // 12:30
        assertEquals(reservation3, reservations.get(2)); // 14:45
    }

    @Test
    public void testSortReservationsEmptyList() {
        // Test sorting with an empty list
        List<DiningReservation> reservations = new ArrayList<>();

        // Create the DateSort instance
        DateSort dateSort = new DateSort();

        // Sort the empty list
        dateSort.sort(reservations);

        // Verify that the list is still empty
        assertTrue(reservations.isEmpty());
    }

    @Test
    public void testSortReservationsSingleElement() {
        // Test sorting with a list of one element
        DiningReservation reservation = new DiningReservation("", "",new Date(2024, 10, 10, 10, 15)); // 10:15
        List<DiningReservation> reservations = new ArrayList<>();
        reservations.add(reservation);

        // Create the DateSort instance
        DateSort dateSort = new DateSort();

        // Sort the list
        dateSort.sort(reservations);

        // Verify that the list still contains one reservation
        assertEquals(1, reservations.size());
        assertEquals(reservation, reservations.get(0));
    }

    @Test
    public void testSortReservationsEqualDate() {
        // Test sorting with two reservations having the same date
        DiningReservation reservation1 = new DiningReservation("", "",new Date(2024, 10, 10, 10, 15)); // 10:15
        DiningReservation reservation2 = new DiningReservation("", "",new Date(2024, 10, 10, 10, 15)); // 10:15

        List<DiningReservation> reservations = new ArrayList<>();
        reservations.add(reservation1);
        reservations.add(reservation2);

        // Create the DateSort instance
        DateSort dateSort = new DateSort();

        // Sort the list
        dateSort.sort(reservations);

        // Verify that both reservations are in the same order
        assertEquals(reservation1, reservations.get(0));
        assertEquals(reservation2, reservations.get(1));
    }
}
