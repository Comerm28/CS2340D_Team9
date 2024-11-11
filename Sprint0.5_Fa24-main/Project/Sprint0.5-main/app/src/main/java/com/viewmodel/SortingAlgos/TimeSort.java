package com.viewmodel.SortingAlgos;

import com.model.DiningReservation;

import java.util.Calendar;
import java.util.Comparator;
import java.util.List;

public class TimeSort implements ViewSort<DiningReservation> {

    @Override
    public List<DiningReservation> sort(List<DiningReservation> notSorted) {
        notSorted.sort(Comparator.comparingInt(this::getCheckInHourMinute));
        return notSorted;
    }

    private int getCheckInHourMinute(DiningReservation reservation) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(reservation.getDateAndTime());
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        return hour * 60 + minute;
    }
}