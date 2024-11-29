package com.viewmodel.sortingalgos;

import java.util.Comparator;
import java.util.List;
import com.model.AccommodationReservation;

public class LodgingTypeSort implements ViewSort<AccommodationReservation> {

    @Override
    public List<AccommodationReservation> sort(List<AccommodationReservation> notSorted) {
        notSorted.sort(Comparator.comparing(AccommodationReservation::getRoomType));
        return notSorted;
    }
}
