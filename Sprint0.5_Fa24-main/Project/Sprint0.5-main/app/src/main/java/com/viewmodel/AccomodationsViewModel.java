package com.viewmodel;

import android.annotation.SuppressLint;

import androidx.lifecycle.ViewModel;

import com.model.AccommodationReservation;
import com.model.Database;
import com.model.Destination;
import com.model.User;
import com.model.UserAccommodationData;
import com.viewmodel.SortingAlgos.CheckInSort;
import com.viewmodel.SortingAlgos.CheckOutSort;
import com.viewmodel.SortingAlgos.ViewSort;

import java.text.ParseException;
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

    public boolean addAccommodation(String check_in, String check_out, String location,
                                   String num_rooms, AccommodationReservation.RoomType roomType)
    {
        UserAccommodationData userAccommodationData = currentUserInfo.getUserAccommodationData();
        boolean worked = false;
        if(isValidAccomodation(check_in, check_out, location, num_rooms)) {
            SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
            try {
                Date checkInDate = sdf.parse(check_in);
                Date checkOutDate = sdf.parse(check_out);
                int rooms = Integer.parseInt(num_rooms);
                AccommodationReservation newAccommodationReservation = new AccommodationReservation(location, checkInDate, checkOutDate, rooms, roomType);
                userAccommodationData.addReservation(newAccommodationReservation);
                worked = true;
            } catch (Exception e) {
                return false; // Return false if date parsing fails or room type code is out of bounds
            }
        }
        Database db = Database.getInstance();
        db.updateUserAccommodationData(currentUserInfo.getUser(), userAccommodationData);
        return worked;
    }

    private boolean isValidAccomodation(String check_in, String check_out, String location, String numRooms)
    {
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        Date st, en;
        int rooms;
        try {
            st = sdf.parse(check_in);
            en = sdf.parse(check_out);
            rooms = Integer.parseInt(numRooms);
        } catch (Exception e) {
            return false;
        }

        if(st.compareTo(en) > 0)
        {
            return false;
        }

        if (rooms <= 0) {
            return false;
        }

        for(AccommodationReservation a : currentUserInfo.getUserAccommodationData().getReservations())
        {
            if(location.equals(a.getLocation()))
            {
                return false;
            }
        }

        return true;
    }

    public boolean isPastAccommodation(AccommodationReservation lodging)
    {
        return lodging.getCheckOutDate().before(new Date());
    }

    public void sortAccommodationsByCheckIn() {
        UserAccommodationData userAccommodationData = currentUserInfo.getUserAccommodationData();
        List<AccommodationReservation> sortedAccommodations = new CheckInSort().sort(new ArrayList<>(userAccommodationData.getReservations()));
        userAccommodationData.getReservations().clear();
        userAccommodationData.getReservations().addAll(sortedAccommodations);
    }

    public void sortAccommodationsByCheckOut() {
        UserAccommodationData userAccommodationData = currentUserInfo.getUserAccommodationData();
        List<AccommodationReservation> sortedAccommodations = new CheckOutSort().sort(new ArrayList<>(userAccommodationData.getReservations()));
        userAccommodationData.getReservations().clear();
        userAccommodationData.getReservations().addAll(sortedAccommodations);
    }

}
