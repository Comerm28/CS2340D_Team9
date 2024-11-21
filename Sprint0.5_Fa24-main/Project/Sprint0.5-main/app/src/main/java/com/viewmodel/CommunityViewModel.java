package com.viewmodel;
import android.annotation.SuppressLint;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.model.AccommodationReservation;
import com.model.CommunityTravelEntriesData;
import com.model.Database;
import com.model.Destination;
import com.model.DiningReservation;
import com.model.Post;
import com.model.TravelEntryData;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CommunityViewModel extends ViewModel {
    //temporary just to make view functional
    private final MutableLiveData<List<Post>> posts = new MutableLiveData<>(new ArrayList<>());
    private CurrentUserInfo currentUserInfo;

    public CommunityViewModel() {
        // Populate with default values
        initializeDefaultPosts();
        currentUserInfo = CurrentUserInfo.getInstance();
    }

    public boolean addPost(String startDate, String endDate, String location, String diningWebsite,
                        String diningLocation, String diningTime, String accomodationLocation, int numRooms,
                        AccommodationReservation.RoomType roomType, int rating)
    {
        Date st = isValidDate(startDate);
        Date en = isValidDate(endDate);
        Date dTime = isValidDate(diningTime);
        if(st == null || en == null || dTime == null)
        {
            return false;
        }
        Destination dest = new Destination(location, st, en);
        AccommodationReservation accom = new AccommodationReservation(accomodationLocation, st, en, numRooms, roomType);
        DiningReservation dining = new DiningReservation(diningLocation, diningWebsite, dTime);
        TravelEntryData travelEntry = new TravelEntryData(st, en, dest, accom, dining, rating);

        CommunityTravelEntriesData communityTravelEntriesData = currentUserInfo.getCommunityTravelEntriesData();
        communityTravelEntriesData.addTravelEntry(travelEntry);
        Database.getInstance().updateCommunityTravelEntriesData(communityTravelEntriesData);

        return true;
    }

    public List<TravelEntryData> getTravelPosts()
    {
        return currentUserInfo.getCommunityTravelEntriesData().getTravelEntries();
    }

    public LiveData<List<Post>> getPosts() {
        return posts;
    }

    public void addPost(Post post) {
        List<Post> currentPosts = posts.getValue();
        if (currentPosts != null) {
            currentPosts.add(post);
            posts.setValue(currentPosts);
        }
    }

    private void initializeDefaultPosts() {
        List<Post> defaultPosts = new ArrayList<>();
        defaultPosts.add(new Post("User1", "Paris", "5 days", "Hotel de France", "Chez Marie", "Airplane", "Loved the Eiffel Tower!"));
        defaultPosts.add(new Post("User2", "Tokyo", "7 days", "Shinjuku Inn", "Sushi Saito", "Train", "Explored amazing temples and culture."));

        posts.setValue(defaultPosts);
    }

    private Date isValidDate(String date) {
        if (date == null || date.trim().isEmpty()) {
            return null;
        }

        if (date.matches("^\\d{2}-\\d{2}-\\d{4}$")) {
            @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter =
                    new SimpleDateFormat("MM-dd-yyyy");
            formatter.setLenient(false);
            try {
                return formatter.parse(date);
            } catch (ParseException e) {
                return null;
            }
        }
        return null;
    }


}
