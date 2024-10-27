package com.viewmodel;

import androidx.lifecycle.ViewModel;

import com.model.Database;
import com.model.Destination;
import com.model.User;
import com.model.UserDestinationData;


public class LogisticsViewModel extends ViewModel {
    private final CurrentUserInfo currentInfo;

    public LogisticsViewModel()
    {
        currentInfo = CurrentUserInfo.getInstance();
    }

    public int getAllottedDays()
    {
        return currentInfo.getAllottedVacationDays();
    }

    public int getPlannedDays()
    {
        int sum = 0;
        for(Destination d : currentInfo.getDestinations())
        {
            sum += (int) d.getDurationDays();
        }
        return sum;
    }

    public boolean inviteUser(String username)
    {
        Database db = Database.getInstance();
        String user = db.checkUser(username);
        if(user != null)
        {
            UserDestinationData userDestinationData = db.getUserDestinationData(user);
            if (userDestinationData != null) {
                userDestinationData.setCollaborating(true);
                userDestinationData.setCollaboratorUsername(CurrentUserInfo.getInstance().getUser().getUsername());
                db.updateDestinationData(new User(user), userDestinationData);
                return true;
            }
        }
        return false;
    }

    public void addNoteToCurrentVacation(String note)
    {
        Database db = Database.getInstance();
        UserDestinationData userDestinationData = db.getUserDestinationData(currentInfo.getUser().getUsername());

        if (userDestinationData == null){
            return;
        }
        if(userDestinationData.isCollaborating())
        {
            if (userDestinationData != null) {
                userDestinationData = db.getUserDestinationData(userDestinationData.getCollaboratorUsername());
                userDestinationData.addNote(note);
                db.updateDestinationData(new User(userDestinationData.getCollaboratorUsername()), userDestinationData);
            }
        } else{
            userDestinationData.addNote(note);
            db.updateDestinationData(currentInfo.getUser(), userDestinationData);
        }
    }
}
