package com.model;

import java.util.List;

public class CommunityTravelEntriesData {
    private List<Post> travelEntries;

    public CommunityTravelEntriesData() {
        //empty to accomodate for firebase
    }

    public List<Post> getTravelEntries() {
        return travelEntries;
    }

    public void addTravelEntry(Post t) {
        travelEntries.add(t);
    }
}