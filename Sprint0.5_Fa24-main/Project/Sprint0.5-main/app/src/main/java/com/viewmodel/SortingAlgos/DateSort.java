package com.viewmodel.SortingAlgos;

import com.model.AccommodationReservation;
import com.model.DiningReservation;
import com.model.Reservation;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class DateSort implements ViewSort<DiningReservation>{

    @Override
    public List<DiningReservation> sort(List<DiningReservation> notSorted) {
        notSorted.sort(Comparator.comparing(DiningReservation::getDateAndTime));
        return notSorted;
    }
}
