package com.viewmodel;
import android.annotation.SuppressLint;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.model.Database;
import com.model.Post;
import com.model.PostFactory;
import android.content.Context;

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

    public void loadPosts(Context context, Runnable onSuccess) {
        Database.getInstance().getCommunityPosts(loadedPosts -> {
            PostFactory postFactory = new PostFactory();
            String[] postDetails = new String[7];
            postDetails[0] = "Paris";
            postDetails[1] = "10-05-2024";
            postDetails[2] = "10-06-2024";
            postDetails[3] = "Hotel de France";
            postDetails[4] = "Chez Marie";
            postDetails[5] = "5";
            postDetails[6] = "Loved the Eiffel Tower!";
            Post post = postFactory.createPost(context, "User1", postDetails);
            String[] post2Details = new String[7];
            post2Details[0] = "Tokyo";
            post2Details[1] = "11-05-2024";
            post2Details[2] = "11-06-2024";
            post2Details[3] = "Shinjuku Inn";
            post2Details[4] = "Sushi Saito";
            post2Details[5] = "5";
            post2Details[6] = "Explored amazing temples and culture.";
            Post post2 = postFactory.createPost(context, "User2", post2Details);
            loadedPosts.add(0, post);
            loadedPosts.add(1, post2);
            posts.setValue(loadedPosts);
            onSuccess.run();
        }, fail -> {
            ArrayList<Post> loadedPosts = new ArrayList<>();
            PostFactory postFactory = new PostFactory();
            String[] postDetails = new String[7];
            postDetails[0] = "Paris";
            postDetails[1] = "10-05-2024";
            postDetails[2] = "10-06-2024";
            postDetails[3] = "Hotel de France";
            postDetails[4] = "Chez Marie";
            postDetails[5] = "5";
            postDetails[6] = "Loved the Eiffel Tower!";
            Post post = postFactory.createPost(context, "User1", postDetails);
            String[] post2Details = new String[7];
            post2Details[0] = "Tokyo";
            post2Details[1] = "11-05-2024";
            post2Details[2] = "11-06-2024";
            post2Details[3] = "Shinjuku Inn";
            post2Details[4] = "Sushi Saito";
            post2Details[5] = "5";
            post2Details[6] = "Explored amazing temples and culture.";
            Post post2 = postFactory.createPost(context, "User2", post2Details);
            loadedPosts.add(0, post);
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
