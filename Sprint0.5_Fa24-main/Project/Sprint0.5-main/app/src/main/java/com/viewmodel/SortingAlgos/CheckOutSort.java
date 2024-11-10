package com.viewmodel.SortingAlgos;

import java.util.Comparator;
import java.util.List;
import com.model.AccommodationReservation;

public class CheckOutSort implements ViewSort<AccommodationReservation> {

    @Override
    public List<AccommodationReservation> sort(List<AccommodationReservation> notSorted) {
        notSorted.sort(Comparator.comparing(AccommodationReservation::getCheckOutDate));
        return notSorted;
    }
}