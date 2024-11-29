package com.viewmodel;

import androidx.lifecycle.ViewModel;

import com.model.Database;
import com.model.DiningReservation;
import com.model.Reservation;
import com.model.UserDiningData;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import com.viewmodel.sortingalgos.DateSort;
import com.viewmodel.sortingalgos.TimeSort;
import com.viewmodel.sortingalgos.ViewSort;

public class DiningViewModel extends ViewModel {
    private CurrentUserInfo currentUserInfo;
    private ViewSort<DiningReservation> dateSort = new DateSort();
    private ViewSort<DiningReservation> timeSort = new TimeSort();
    public DiningViewModel() {
        currentUserInfo = CurrentUserInfo.getInstance();
        reservations = new ArrayList<>();
    }
    
    public boolean addDiningReservation(String location, String website, String time, String date) {
        date = date.replace('/', '-');
        if (!validReservation(location, website, time, date)) {
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
        if (!website.matches(".*\\..*")
                || !time.matches("^([01]?\\d|2\\d{1}):[0-5]\\d$")
                || !date.matches("^\\d{2}-\\d{2}-\\d{4}$")) {
            return false;
        }
        UserDiningData userDiningData = currentUserInfo.getUserDiningData();
        for (Reservation r : userDiningData.getReservations()) {
            if (r.getLocation().equals(location)) {
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
        List<DiningReservation> sortedReservations =
                dateSort.sort(new ArrayList<>(userDiningData.getReservations()));
        userDiningData.getReservations().clear();
        userDiningData.getReservations().addAll(sortedReservations);
    }

    public void sortReservationsByTime() {
        UserDiningData userDiningData = currentUserInfo.getUserDiningData();
        List<DiningReservation> sortedReservations =
                timeSort.sort(new ArrayList<>(userDiningData.getReservations()));
        userDiningData.getReservations().clear();
        userDiningData.getReservations().addAll(sortedReservations);
    }

    public boolean isPastReservation(DiningReservation reservation) {
        return reservation.getDateAndTime().before(new Date());
    }
}
