package com.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import com.example.sprintproject.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.model.Post;
import com.viewmodel.CommunityViewModel;
import java.util.ArrayList;
import java.util.List;

public class CommunityFragment extends Fragment {
    private CommunityViewModel communityViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_community, container, false);

        // ViewModel initialization
        communityViewModel = new ViewModelProvider(this).get(CommunityViewModel.class);

        // Set up RecyclerView
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        final PostAdapter adapter = new PostAdapter();
        recyclerView.setAdapter(adapter);

        // Observe posts data
        communityViewModel.getPosts().observe(getViewLifecycleOwner(), adapter::submitList);

        // Set up FloatingActionButton
        FloatingActionButton fabAddButton = view.findViewById(R.id.fabAddButton);
        fabAddButton.setOnClickListener(v -> openCreatePostDialog());

        return view;
    }

    private void openCreatePostDialog() {
        CreatePostDialog dialog = new CreatePostDialog(post -> communityViewModel.addPost(post));
        dialog.show(getParentFragmentManager(), "CreatePostDialog");
    }

    private class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostViewHolder> {

        private final List<Post> postList = new ArrayList<>();

        public void submitList(List<Post> posts) {
            postList.clear();
            postList.addAll(posts);
            notifyDataSetChanged();
        }

        @NonNull
        @Override
        public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_post, parent, false);
            return new PostViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {
            Post post = postList.get(position);
            holder.usernameTextView.setText(post.username);
            holder.destinationTextView.setText(post.destination);
            holder.durationTextView.setText(post.duration);
            holder.accommodationsTextView.setText(post.accommodations);
            holder.diningReservationTextView.setText(post.diningReservation);
            holder.transportationTextView.setText(post.transportation);
            holder.notesTextView.setText(post.notes);
        }

        @Override
        public int getItemCount() {
            return postList.size();
        }

        class PostViewHolder extends RecyclerView.ViewHolder {
            TextView usernameTextView, destinationTextView, durationTextView, accommodationsTextView, diningReservationTextView, transportationTextView, notesTextView;

            public PostViewHolder(@NonNull View itemView) {
                super(itemView);
                usernameTextView = itemView.findViewById(R.id.usernameTextView);
                destinationTextView = itemView.findViewById(R.id.destinationTextView);
                durationTextView = itemView.findViewById(R.id.durationTextView);
                accommodationsTextView = itemView.findViewById(R.id.accommodationsTextView);
                diningReservationTextView = itemView.findViewById(R.id.diningReservationTextView);
                transportationTextView = itemView.findViewById(R.id.transportationTextView);
                notesTextView = itemView.findViewById(R.id.notesTextView);
            }
        }
    }
}