package com.example.sprintproject;

import static org.junit.Assert.*;

import com.viewmodel.DestinationViewModel;

import org.junit.Before;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DestinationViewModelTest {

    private DestinationViewModel viewModel;

    @Before
    public void setUp() {
        viewModel = new DestinationViewModel();
    }

    @Test
    public void testLogTravel_InvalidDestination_ReturnsFalse() {
        String start = "01-01-2023";
        String end = "01-10-2023";
        String destination = "";

        boolean result = viewModel.logTravel(end, start, destination);
        assertFalse(result);
    }

    @Test
    public void testLogTravel_InvalidDateRange_ReturnsFalse() {
        String start = "01-10-2023";
        String end = "01-01-2023";
        String destination = "Beach";

        boolean result = viewModel.logTravel(end, start, destination);
        assertFalse(result);
    }

    @Test
    public void testIsValidDestination_ValidDestination_ReturnsTrue() {
        assertTrue(viewModel.isValidDestination("Beach_1"));
    }

    @Test
    public void testIsValidDestination_InvalidDestination_ReturnsFalse() {
        assertFalse(viewModel.isValidDestination(""));
        assertFalse(viewModel.isValidDestination("!Invalid"));
    }

    @Test
    public void testGetDurationFromStrings_ValidDates_ReturnsDuration() {
        String start = "01-01-2023";
        String end = "01-10-2023";

        int duration = viewModel.getDurationFromStrings(start, end);
        assertEquals(9, duration);
    }

    @Test
    public void testGetDurationFromStrings_InvalidDates_ReturnsMinusOne() {
        String start = "invalid-date";
        String end = "01-10-2023";

        int duration = viewModel.getDurationFromStrings(start, end);
        assertEquals(-1, duration);
    }

    @Test
    public void testGetEndDateFromInfo_ValidInputs_ReturnsCalculatedEndDate() {
        String duration = "10";
        String startDate = "01-01-2023";

        String endDate = viewModel.getEndDateFromInfo(duration, startDate);
        assertNotNull(endDate);
    }

    @Test
    public void testGetStartDateFromInfo_ValidInputs_ReturnsCalculatedStartDate() {
        String duration = "10";
        String endDate = "01-11-2023";

        assertNotNull(viewModel.getStartDateFromInfo(duration, endDate));
    }

    @Test
    public void testGetStartDateFromInfo_InvalidDuration_ReturnsNull() {
        String duration = "-1";
        String endDate = "01-11-2023";

        String startDate = viewModel.getStartDateFromInfo(duration, endDate);
        assertNull(startDate);
    }

    @Test
    public void testGetStartDateFromInfo_InvalidEndDate_ReturnsNull() {
        String duration = "10";
        String endDate = "invalid-date";

        String startDate = viewModel.getStartDateFromInfo(duration, endDate);
        assertNull(startDate);
    }
}
