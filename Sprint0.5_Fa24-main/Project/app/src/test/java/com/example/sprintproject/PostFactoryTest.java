package com.example.sprintproject;

import android.content.Context;
import org.junit.Before;
import org.junit.Test;
import com.model.PostFactory;
import com.model.Post;

import static org.junit.Assert.*;

public class PostFactoryTest {

    private PostFactory postFactory;
    private Context context;

    @Before
    public void setUp() {
        context = null;  // Setting context as null for all tests
        postFactory = new PostFactory();
    }

    @Test
    public void testCreatePost_ValidInputs() {
        String username = "testUser";
        String[] details = {
                "Paris", // destination
                "01-01-2024", // startDate
                "01-10-2024", // endDate
                "Hotel", // accommodations
                "5-star restaurant", // diningReservation
                "5", // rating
                "Great vacation" // notes
        };

        Post post = postFactory.createPost(context, username, details);

        // Validate that a post object was returned and check its values
        assertNotNull(post);
        assertEquals(username, post.getUsername());
        assertEquals(details[0], post.getDestination()); // destination
        assertEquals(5, post.getRating()); // Ensure rating is correctly set
    }

    @Test
    public void testCreatePost_InvalidDateFormat() {
        // Invalid date format
        String[] details = {
                "Paris", // destination
                "invalid-date", // startDate
                "invalid-date", // endDate
                "Hotel", // accommodations
                "5-star restaurant", // diningReservation
                "4", // rating
                "Nice trip" // notes
        };

        Post post = postFactory.createPost(context, "testUser", details);

        // The post should be null as the date format is invalid
        assertNull(post);
    }

    @Test
    public void testCreatePost_EndDateBeforeStartDate() {
        // Invalid end date (before start date)
        String[] details = {
                "Paris", // destination
                "01-01-2024", // startDate
                "12-31-2023", // endDate (invalid: End date is before start date)
                "Hotel", // accommodations
                "5-star restaurant", // diningReservation
                "5", // rating
                "Wonderful trip" // notes
        };

        Post post = postFactory.createPost(context, "testUser", details);

        // The post should be null because the end date is before the start date
        assertNull(post);
    }

    @Test
    public void testCreatePost_EmptyDestination() {
        // Empty destination string
        String[] details = {
                "", // destination (empty)
                "01-01-2024", // startDate
                "01-10-2024", // endDate
                "Hotel", // accommodations
                "5-star restaurant", // diningReservation
                "5", // rating
                "Nice trip" // notes
        };

        Post post = postFactory.createPost(context, "testUser", details);

        // The post should be null because the destination is empty
        assertNull(post);
    }

    @Test
    public void testCreatePost_WhitespaceDestination() {
        String[] details = {
                "          ", // destination (blank spaces)
                "01-01-2024", // startDate
                "01-10-2024", // endDate
                "Hotel", // accommodations
                "5-star restaurant", // diningReservation
                "5", // rating
                "Nice trip" // notes
        };

        Post post = postFactory.createPost(context, "testUser", details);

        // The post should be null because the destination is blank
        assertNull(post);
    }

    @Test
    public void testCreatePost_InvalidRating() {
        // Invalid rating value (greater than 5)
        String[] details = {
                "Paris", // destination
                "01-01-2024", // startDate
                "01-10-2024", // endDate
                "Hotel", // accommodations
                "5-star restaurant", // diningReservation
                "6", // rating (invalid)
                "Nice trip" // notes
        };

        Post post = postFactory.createPost(context, "testUser", details);

        // The post should be null because the rating is out of the valid range
        assertNull(post);
    }

    @Test
    public void testCreatePost_NonIntegerRating() {
        // Invalid non-integer rating
        String[] details = {
                "Paris", // destination
                "01-01-2024", // startDate
                "01-10-2024", // endDate
                "Hotel", // accommodations
                "5-star restaurant", // diningReservation
                "invalid-rating", // rating (non-integer)
                "Nice trip" // notes
        };

        Post post = postFactory.createPost(context, "testUser", details);

        // The post should be null because the rating is not an integer
        assertNull(post);
    }

    @Test
    public void testCreatePost_EmptyRating() {
        // Invalid empty rating
        String[] details = {
                "Paris", // destination
                "01-01-2024", // startDate
                "01-10-2024", // endDate
                "Hotel", // accommodations
                "5-star restaurant", // diningReservation
                "", // rating (empty)
                "Nice trip" // notes
        };

        Post post = postFactory.createPost(context, "testUser", details);

        // The post should be null because the rating is empty
        assertNull(post);
    }

    @Test
    public void testCreatePost_WhitespaceRating() {
        // Invalid blank rating
        String[] details = {
                "Paris", // destination
                "01-01-2024", // startDate
                "01-10-2024", // endDate
                "Hotel", // accommodations
                "5-star restaurant", // diningReservation
                "              ", // rating (blank spaces)
                "Nice trip" // notes
        };

        Post post = postFactory.createPost(context, "testUser", details);

        // The post should be null because the rating is blank
        assertNull(post);
    }

    @Test
    public void testCreatePost_LargeNumberOfPosts() {
        for (int i = 0; i < 1000; i++) {
            String username = "testUser" + i;
            String[] details = {
                    "Paris", // destination
                    "01-01-2024", // startDate
                    "01-10-2024", // endDate
                    "Hotel", // accommodations
                    "5-star restaurant", // diningReservation
                    "5", // rating
                    "Nice trip" // notes
            };

            Post post = postFactory.createPost(context, username, details);
            assertNotNull(post);  // Ensure the post is created correctly for each iteration
        }
    }
}
