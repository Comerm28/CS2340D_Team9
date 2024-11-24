package com.viewmodel;
import android.annotation.SuppressLint;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.model.Database;
import com.model.Destination;
import com.model.Post;

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
        currentUserInfo = CurrentUserInfo.getInstance();
    }

    public void loadPosts(Runnable onSuccess) {
        Database.getInstance().getCommunityPosts(loadedPosts -> {
            Post post = new Post();
            post.setUsername("User1");
            post.setDestination("Paris");
            post.setStartDate(Destination.parseDate("10-05-2024"));
            post.setEndDate(Destination.parseDate("10-06-2024"));
            post.setAccommodations("Hotel de France");
            post.setDiningReservation("Chez Marie");
            post.setRating(5);
            post.setNotes("Loved the Eiffel Tower!");
            loadedPosts.add(0, post);

            Post post2 = new Post();
            post2.setUsername("User2");
            post2.setDestination("Tokyo");
            post2.setStartDate(Destination.parseDate("11-05-2024"));
            post2.setEndDate(Destination.parseDate("11-16-2024"));
            post2.setAccommodations("Shinjuku Inn");
            post2.setDiningReservation("Sushi Saito");
            post2.setRating(5);
            post2.setNotes("Explored amazing temples and culture.");

            loadedPosts.add(1, post2);
            posts.setValue(loadedPosts);
            onSuccess.run();
        }, fail -> {
            ArrayList<Post> loadedPosts = new ArrayList<>();

            Post post = new Post();
            post.setUsername("User1");
            post.setDestination("Paris");
            post.setStartDate(Destination.parseDate("10-05-2024"));
            post.setEndDate(Destination.parseDate("10-06-2024"));
            post.setAccommodations("Hotel de France");
            post.setDiningReservation("Chez Marie");
            post.setRating(5);
            post.setNotes("Loved the Eiffel Tower!");
            loadedPosts.add(0, post);

            Post post2 = new Post();
            post2.setUsername("User2");
            post2.setDestination("Tokyo");
            post2.setStartDate(Destination.parseDate("11-05-2024"));
            post2.setEndDate(Destination.parseDate("11-16-2024"));
            post2.setAccommodations("Shinjuku Inn");
            post2.setDiningReservation("Sushi Saito");
            post2.setRating(5);
            post2.setNotes("Explored amazing temples and culture.");

            loadedPosts.add(1, post2);

            posts.setValue(loadedPosts);
            onSuccess.run();
        });
    }


    public List<Post> getTravelPosts() {
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
