package com.viewmodel;

import android.annotation.SuppressLint;

import androidx.lifecycle.ViewModel;

import com.model.Database;
import com.model.DiningReservation;
import com.model.Reservation;
import com.model.UserDiningData;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import com.viewmodel.SortingAlgos.DateSort;
import com.viewmodel.SortingAlgos.TimeSort;
import com.viewmodel.SortingAlgos.ViewSort;

public class DiningViewModel extends ViewModel {
    private CurrentUserInfo currentUserInfo;
    private List<DiningReservation> reservations; // Placeholder for reservations
    private ViewSort<DiningReservation> dateSort = new DateSort();
    private ViewSort<DiningReservation> timeSort = new TimeSort();
    public DiningViewModel() {
        currentUserInfo = CurrentUserInfo.getInstance();
        reservations = new ArrayList<>();
    }
    
    public boolean addDiningReservation(String location, String website, String time, String date)
    {
        date = date.replace('/', '-');
        if(!validReservation(location, website, time, date))
        {
            return false;
        }
        Date dateAndTime = parseDateTime(date, time);
        UserDiningData userDiningData = currentUserInfo.getUserDiningData();
        userDiningData.addReservation(new DiningReservation(location, website, dateAndTime));
        Database.getInstance().updateDiningData(currentUserInfo.getUser(), userDiningData);
        return true;
    }

    public List<DiningReservation> getReservations() {
        UserDiningData userDiningData = currentUserInfo.getUserDiningData();
        return userDiningData.getReservations();
    }

    private boolean validReservation(String location, String website, String time, String date) {
        if(!website.matches(".*\\..*") || !time.matches("^([01]?[0-9]|2[0-3]):[0-5][0-9]$") || !date.matches("^\\d{2}-\\d{2}-\\d{4}$"))
        {
            return false;
        }
        UserDiningData userDiningData = currentUserInfo.getUserDiningData();
        for(Reservation r : userDiningData.getReservations())
        {
            if(r.getLocation().equals(location))
            {
                return false;
            }
        }
        return true;
    }

    public static Date parseDateTime(String date, String time) {
        try {
            String dateTimeString = date + " " + time;
            SimpleDateFormat dateTimeFormat = new SimpleDateFormat("MM-dd-yyyy hh:mm");
            return dateTimeFormat.parse(dateTimeString);
        } catch (Exception e) {
            return null;
        }
    }

    public void sortReservationsByDate() {
        UserDiningData userDiningData = currentUserInfo.getUserDiningData();
        List<DiningReservation> sortedReservations = dateSort.sort(new ArrayList<>(userDiningData.getReservations()));
        userDiningData.getReservations().clear();
        userDiningData.getReservations().addAll(sortedReservations);
    }

    public void sortReservationsByTime() {
        UserDiningData userDiningData = currentUserInfo.getUserDiningData();
        List<DiningReservation> sortedReservations = timeSort.sort(new ArrayList<>(userDiningData.getReservations()));
        userDiningData.getReservations().clear();
        userDiningData.getReservations().addAll(sortedReservations);
    }

    public boolean isPastReservation(DiningReservation reservation) {
        return reservation.getDateAndTime().before(new Date());
    }


//    private Date isValidDate(String date) {
//        if (date == null || date.trim().isEmpty()) {
//            return null;
//        }
//
//        if (date.matches("^\\d{2}-\\d{2}-\\d{4}$")) {
//            @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter =
//                    new SimpleDateFormat("MM-dd-yyyy");
//            formatter.setLenient(false);
//            try {
//                return formatter.parse(date);
//            } catch (ParseException e) {
//                return null;
//            }
//        }
//        return null;
//    }
}
