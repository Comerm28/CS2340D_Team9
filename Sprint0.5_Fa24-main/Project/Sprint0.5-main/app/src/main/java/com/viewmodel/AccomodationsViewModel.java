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
                                   int numRooms, AccommodationReservation.RoomType roomType) {
        UserAccommodationData userAccommodationData = currentUserInfo.getUserAccommodationData();

        if (isValidAccomodation(checkIn, checkOut, location)) {
            SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
            try {
                Date checkInDate = sdf.parse(checkIn);
                Date checkOutDate = sdf.parse(checkOut);

                AccommodationReservation newAccommodationReservation = new AccommodationReservation(
                        location, checkInDate, checkOutDate, numRooms, roomType);
                userAccommodationData.addReservation(newAccommodationReservation);
            } catch (Exception e) {
                // Return false if date parsing fails or room type code is out of bounds
                return false;
            }
        }
        Database db = Database.getInstance();
        db.updateUserAccommodationData(currentUserInfo.getUser(), userAccommodationData);

        return true;
    }

    private boolean isValidAccomodation(String checkIn, String checkOut, String location) {
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        Date st;
        Date en;
        try {
            st = sdf.parse(checkIn);
            en = sdf.parse(checkOut);
        } catch (Exception e) {
            return false;
        }

        if (st.compareTo(en) > 0) {
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
        List<AccommodationReservation> sortedAccommodations = new CheckInSort().sort(
            new ArrayList<>(userAccommodationData.getReservations()
        ));
        userAccommodationData.getReservations().clear();
        userAccommodationData.getReservations().addAll(sortedAccommodations);
    }

    public void sortAccommodationsByCheckOut() {
        UserAccommodationData userAccommodationData = currentUserInfo.getUserAccommodationData();
        List<AccommodationReservation> sortedAccommodations = new CheckOutSort().sort(
            new ArrayList<>(userAccommodationData.getReservations())
        );
        userAccommodationData.getReservations().clear();
        userAccommodationData.getReservations().addAll(sortedAccommodations);
    }

}
