package com.viewmodel.SortingAlgos;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import com.model.AccommodationReservation;
import com.model.DiningReservation;

public class LodgingTypeSort implements ViewSort<AccommodationReservation>{

    @Override
    public List<AccommodationReservation> sort(List<AccommodationReservation> notSorted) {
        notSorted.sort(Comparator.comparing(AccommodationReservation::getRoomType));
        return notSorted;
    }
}
