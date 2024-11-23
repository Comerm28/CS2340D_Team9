package com.model;

import java.util.List;

public class CommunityTravelEntriesData {
    List<Post> travelEntries;

    public CommunityTravelEntriesData() {}

    public List<Post> getTravelEntries() {
        return travelEntries;
    }

    public void addTravelEntry(Post t)
    {
        travelEntries.add(t);
    }
}
