package com.model;

import java.util.List;

public class CommunityTravelEntriesData {
    List<TravelEntryData> travelEntries;

    public CommunityTravelEntriesData() {}

    public List<TravelEntryData> getTravelEntries() {
        return travelEntries;
    }

    public void addTravelEntry(TravelEntryData t)
    {
        travelEntries.add(t);
    }
}
