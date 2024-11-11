package com.viewmodel;

import androidx.lifecycle.ViewModel;

import com.model.AccommodationReservation;
import com.model.Database;
import com.model.UserAccommodationData;
import com.viewmodel.SortingAlgos.CheckInSort;
import com.viewmodel.SortingAlgos.CheckOutSort;
import com.viewmodel.SortingAlgos.ViewSort;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import java.text.SimpleDateFormat;

public class AccomodationsViewModel extends ViewModel {
    private CurrentUserInfo currentUserInfo;
    private ViewSort<AccommodationReservation> checkInSort = new CheckInSort();
    private ViewSort<AccommodationReservation> checkOutSort = new CheckOutSort();

    public AccomodationsViewModel() {
        currentUserInfo = CurrentUserInfo.getInstance();
    }

    public List<AccommodationReservation> getAccommodations() {
        return currentUserInfo.getUserAccommodationData().getReservations();
    }

    public boolean addAccommodation(String checkIn, String checkOut, String location,
                                   String numRooms, AccommodationReservation.RoomType roomType) {
        UserAccommodationData userAccommodationData = currentUserInfo.getUserAccommodationData();
        boolean worked = false;
        if (isValidAccomodation(checkIn, checkOut, location, numRooms)) {
            SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
            try {
                Date checkInDate = sdf.parse(checkIn);
                Date checkOutDate = sdf.parse(checkOut);
                int rooms = Integer.parseInt(numRooms);
                AccommodationReservation newAccommodationReservation =
                        new AccommodationReservation(location, checkInDate,
                                checkOutDate, rooms, roomType);
                userAccommodationData.addReservation(newAccommodationReservation);
                worked = true;
            } catch (Exception e) {
                return false;
            }
        }
        Database db = Database.getInstance();
        db.updateUserAccommodationData(currentUserInfo.getUser(), userAccommodationData);
        return worked;
    }

    private boolean isValidAccomodation(String checkIn, String checkOut,
                                        String location, String numRooms) {
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        Date st;
        Date en;
        int rooms;
        try {
            st = sdf.parse(checkIn);
            en = sdf.parse(checkOut);
            rooms = Integer.parseInt(numRooms);
        } catch (Exception e) {
            return false;
        }

        if (st.compareTo(en) > 0) {
            return false;
        }

        if (rooms <= 0) {
            return false;
        }

        for (AccommodationReservation a : currentUserInfo.getUserAccommodationData()
                .getReservations()) {
            if (location.equals(a.getLocation())) {
                return false;
            }
        }

        return true;
    }

    public boolean isPastAccommodation(AccommodationReservation lodging) {
        return lodging.getCheckOutDate().before(new Date());
    }

    public void sortAccommodationsByCheckIn() {
        UserAccommodationData userAccommodationData = currentUserInfo.getUserAccommodationData();
        List<AccommodationReservation> sortedAccommodations = new CheckInSort()
                .sort(new ArrayList<>(userAccommodationData.getReservations()));
        userAccommodationData.getReservations().clear();
        userAccommodationData.getReservations().addAll(sortedAccommodations);
    }

    public void sortAccommodationsByCheckOut() {
        UserAccommodationData userAccommodationData = currentUserInfo.getUserAccommodationData();
        List<AccommodationReservation> sortedAccommodations = new CheckOutSort()
                .sort(new ArrayList<>(userAccommodationData.getReservations()));
        userAccommodationData.getReservations().clear();
        userAccommodationData.getReservations().addAll(sortedAccommodations);
    }

}
