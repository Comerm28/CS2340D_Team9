import com.model.TravelLog;

import java.util.ArrayList;
import java.util.List;

public class TravelLogDatabase {
    private static TravelLogDatabase instance;
    private List<TravelLog> travelLogs;

    private TravelLogDatabase() {
        travelLogs = new ArrayList<>();
        // Prepopulate with two entries
        travelLogs.add(new TravelLog("Hawaii", 7));
        travelLogs.add(new TravelLog("New York", 5));
    }

    public static TravelLogDatabase getInstance() {
        if (instance == null) {
            instance = new TravelLogDatabase();
        }
        return instance;
    }

    public void addTravelLog(TravelLog travelLog) {
        travelLogs.add(travelLog);
    }

    public List<TravelLog> getTravelLogs() {
        return travelLogs;
    }
}
