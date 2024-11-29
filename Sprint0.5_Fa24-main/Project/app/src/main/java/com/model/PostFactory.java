package com.model;

import android.widget.Toast;
import android.content.Context;

import java.util.Date;

public class PostFactory {
    public PostFactory() {
        //accomodate firebase
    }

    public Post createPost(Context context, String username, String[] postDetails) {
        if (postDetails == null || postDetails.length < 7) {
            showToast(context, "Invalid post details");
            return null;
        }
    
        Date startDate = parseDateWithToast(context, postDetails[1], "Invalid start date format");
        Date endDate = parseDateWithToast(context, postDetails[2], "Invalid end date format");
        if (startDate == null || endDate == null || startDate.compareTo(endDate) > 0) {
            showToast(context, "End date cannot be before start date.");
            return null;
        }
    
        String destination = postDetails[0].trim();
        if (destination.isEmpty()) {
            showToast(context, "Destination cannot be blank.");
            return null;
        }
    
        int rating = parseRatingWithToast(context, postDetails[5]);
        if (rating == -1) return null;
    
        return createPostObject(username, destination, startDate, endDate, postDetails, rating);
    }
    
    private void showToast(Context context, String message) {
        if (context != null) {
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
        }
    }
    
    private Date parseDateWithToast(Context context, String dateStr, String errorMessage) {
        Date date = Destination.parseDate(dateStr);
        if (date == null) {
            showToast(context, errorMessage);
        }
        return date;
    }
    
    private int parseRatingWithToast(Context context, String ratingStr) {
        try {
            int rating = Integer.parseInt(ratingStr.trim());
            if (rating < 0 || rating > 5) {
                showToast(context, "Rating must be between 0 and 5.");
                return -1;
            }
            return rating;
        } catch (Exception e) {
            showToast(context, "Failed to parse rating");
            return -1;
        }
    }
    
    private Post createPostObject(String username, String destination, Date startDate, Date endDate, String[] postDetails, int rating) {
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