
package com.viewmodel;

import androidx.lifecycle.ViewModel;

import com.model.Database;
import com.model.User;

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
        Database db = Database.getInstance();
        db.getUserDestinationData(username, destinationData -> {
            destinationData.setCollaborating(true);
            destinationData.setCollaboratorUsername(CurrentUserInfo.getInstance()
                    .getUser().getUsername());
            db.updateDestinationData(new User(username), destinationData);
            onSuccess.run();
        }, onFail);
    }

    public void addNoteToCurrentVacation(String note, Runnable onSuccess, Consumer<String> onFail) {
        Database db = Database.getInstance();
        db.getUserDestinationData(currentInfo.getUser().getUsername(),
                destinationData -> {
                    if (destinationData.isCollaborating()) {
                        db.getUserDestinationData(destinationData.getCollaboratorUsername(),
                                   destinationData2 -> {
                                destinationData.addNote(note);
                                db.updateDestinationData(new User(destinationData
                                        .getCollaboratorUsername()), destinationData);
                                onSuccess.run(); }, onFail);
                    } else {
                        destinationData.addNote(note);
                        db.updateDestinationData(currentInfo.getUser(), destinationData);
                    }
                },
                onFail);
    }
}
