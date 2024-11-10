
package com.viewmodel;

import androidx.lifecycle.ViewModel;

import com.model.Database;
import com.model.User;
import com.model.UserDestinationData;

import java.util.function.Consumer;


public class LogisticsViewModel extends ViewModel {
    private final CurrentUserInfo currentInfo;

    public LogisticsViewModel() {
        currentInfo = CurrentUserInfo.getInstance();
    }

    public void getAllottedDays(Consumer<Integer> onLoad, Consumer<String> onFail) {
        currentInfo.getAllottedVacationDays(onLoad, onFail);
    }


    public void inviteUser(String username, Runnable onSuccess, Consumer<String> onFail) {
        Database.getInstance().checkUser(username,
                data -> {
                    if(data != null)
                    {
                        data.setCollaborating(true);
                        data.setCollaboratorUsername(currentInfo.getUser().getUsername());
                        Database.getInstance().updateUserData(data);
                    }
                },
                error -> {
                    //toast log maybe?
                });
    }

    public void addNoteToCurrentVacation(String note, Runnable onSuccess, Consumer<String> onFail) {
        Database db = Database.getInstance();
        UserDestinationData userdata = currentInfo.getUserDestinationData();
        userdata.addNote(note);
        db.updateDestinationData(currentInfo.getUser(), userdata);
    }
}
