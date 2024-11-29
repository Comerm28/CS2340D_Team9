package com.example.sprintproject;
import org.junit.Test;
import static org.junit.Assert.*;

import com.model.Destination;

import java.util.Date;
import java.util.GregorianCalendar;

public class DestinationUnitTest {

    @Test
    public void testCalculateMissingStartDate() {
        Date newYearsEve = new GregorianCalendar(2024, 12 - 1, 31).getTime();
        Date halloween = new GregorianCalendar(2024, 10 - 1, 31).getTime();

        Date missing = Destination.calculateMissingStartDate(61, newYearsEve);
        assertEquals(halloween, missing);
    }

    @Test
    public void testCalculateMissingEndDate () {
        Date jan2000 = new GregorianCalendar(2000, 0, 1).getTime();
        Date jan2024 = new GregorianCalendar(2024, 0, 1).getTime();
        // from google calendar
        int daysUntil2024 = 8766;

        Date missing = Destination.calculateMissingEndDate(daysUntil2024, jan2000);
        assertEquals(jan2024, missing);
    }

    @Test
    public void testCalculateMissingDays () {
        Date rustReleaseDate = new GregorianCalendar(2015, 5 - 1, 15).getTime();
        Date gtStartDate2024 = new GregorianCalendar(2024, 8 - 1, 19).getTime();
        // from google calendar
        long daysBetween = 3384;

        long missing = Destination.calculateMissingDays(rustReleaseDate, gtStartDate2024);
        assertEquals(daysBetween, missing);
    }
}
