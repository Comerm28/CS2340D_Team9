package com.viewmodel;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.model.Post;
import java.util.ArrayList;
import java.util.List;

public class CommunityViewModel extends ViewModel {
    //temporary just to make view functional
    private final MutableLiveData<List<Post>> posts = new MutableLiveData<>(new ArrayList<>());

    public CommunityViewModel() {
        // Populate with default values
        initializeDefaultPosts();
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
}
