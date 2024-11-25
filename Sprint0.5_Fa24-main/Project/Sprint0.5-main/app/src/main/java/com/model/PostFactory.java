package com.model;

import android.widget.Toast;
import android.content.Context;

import java.util.Date;

public class PostFactory {
    public PostFactory() {

    }

    public Post createPost(Context context, String username,
                           String[] postDetails) {
        String destinationString = postDetails[0];
        Date startDate = Destination.parseDate(postDetails[1]);
        Date endDate = Destination.parseDate(postDetails[2]);
        if (startDate == null || endDate == null) {
            if (context != null) {
                Toast.makeText(
                        context, "Invalid date format", Toast.LENGTH_SHORT
                ).show();
            }
            return null;
        }
        if (startDate.compareTo(endDate) > 0) {
            if (context != null) {
                Toast.makeText(context, "End date cannot be before start date.",
                        Toast.LENGTH_SHORT).show();
            }
            return null;
        }
        String destination = destinationString.trim();
        if (destination.isEmpty()) {
            if (context != null) {
                Toast.makeText(context, "Destination cannot be blank.", Toast.LENGTH_SHORT).show();
            }
            return null;
        }
        int rating;
        try {
            rating = Integer.parseInt(postDetails[5].trim());
            if (rating < 0 || rating > 5) {
                if (context != null) {
                    Toast.makeText(context, "Rating must be between 0 and 5.",
                            Toast.LENGTH_SHORT).show();
                }
                return null;
            }
        } catch (Exception e) {
            if (context != null) {
                Toast.makeText(
                        context, "Failed to parse rating", Toast.LENGTH_SHORT
                ).show();
            }
            return null;
        }
        Post post = new Post();
        post.setUsername(username);
        post.setDestination(destination);
        post.setStartDate(startDate);
        post.setEndDate(endDate);
        post.setAccommodations(postDetails[3].trim());
        post.setDiningReservation(postDetails[4].trim());
        post.setRating(rating);
        post.setNotes(postDetails[6].trim());
        return post;
    }
}