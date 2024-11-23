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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.function.Consumer;

public class CommunityViewModel extends ViewModel {
    //temporary just to make view functional
    private final MutableLiveData<List<Post>> posts = new MutableLiveData<>(new ArrayList<>());
    private CurrentUserInfo currentUserInfo;

    public CommunityViewModel() {


        currentUserInfo = CurrentUserInfo.getInstance();
    }

    public void loadPosts(Runnable onSuccess) {
            Database.getInstance().getCommunityPosts(loadedPosts -> {
            loadedPosts.add(0, new Post("User1", "Paris", Destination.parseDate("10-05-2024"), Destination.parseDate("10-06-2024"), "Hotel de France", "Chez Marie", 5, "Loved the Eiffel Tower!"));
            loadedPosts.add(1, new Post("User2", "Tokyo", Destination.parseDate("11-05-2024"), Destination.parseDate("11-16-2024"), "Shinjuku Inn", "Sushi Saito", 5, "Explored amazing temples and culture."));
            posts.setValue(loadedPosts);
            onSuccess.run();
        }, fail -> {
            ArrayList<Post> loadedPosts = new ArrayList<>();
            loadedPosts.add(0, new Post("User1", "Paris", Destination.parseDate("10-05-2024"), Destination.parseDate("10-06-2024"), "Hotel de France", "Chez Marie", 5, "Loved the Eiffel Tower!"));
            loadedPosts.add(1, new Post("User2", "Tokyo", Destination.parseDate("11-05-2024"), Destination.parseDate("11-16-2024"), "Shinjuku Inn", "Sushi Saito", 5, "Explored amazing temples and culture."));
            posts.setValue(loadedPosts);
            onSuccess.run();
        });
    }

//    public boolean addPost(String startDate, String endDate, String location, String diningWebsite,
//                        String diningLocation, String diningTime, String accomodationLocation, int numRooms,
//                        AccommodationReservation.RoomType roomType, int rating)
//    {
//        Date st = isValidDate(startDate);
//        Date en = isValidDate(endDate);
//        Date dTime = isValidDate(diningTime);
//        if(st == null || en == null || dTime == null)
//        {
//            return false;
//        }
//        Destination dest = new Destination(location, st, en);
//        AccommodationReservation accom = new AccommodationReservation(accomodationLocation, st, en, numRooms, roomType);
//        DiningReservation dining = new DiningReservation(diningLocation, diningWebsite, dTime);
//        TravelEntryData travelEntry = new TravelEntryData(st, en, dest, accom, dining, rating);
//
//        CommunityTravelEntriesData communityTravelEntriesData = currentUserInfo.getCommunityTravelEntriesData();
//        communityTravelEntriesData.addTravelEntry(travelEntry);
//        Database.getInstance().updateCommunityTravelEntriesData(communityTravelEntriesData);
//
//        return true;
//    }

    public List<Post> getTravelPosts()
    {
        return currentUserInfo.getCommunityTravelEntriesData().getTravelEntries();
    }

    public LiveData<List<Post>> getPosts() {
        return posts;
    }

    public void addPost(Post post) {
        List<Post> currentPosts = posts.getValue();
        Database.getInstance().addCommunityPost(post);
        if (currentPosts != null) {
            currentPosts.add(post);
            posts.setValue(currentPosts);
        }
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
