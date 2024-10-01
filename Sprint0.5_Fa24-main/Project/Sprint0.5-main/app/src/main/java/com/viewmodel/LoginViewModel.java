package com.viewmodel;

import androidx.lifecycle.ViewModel;


import com.model.Authenticator;

public class LoginViewModel extends ViewModel {
    private Authenticator authenticator;

    public LoginViewModel(){
        authenticator = new Authenticator();
    }

    // Logic for creating a user account can be handled here
    public boolean onCreateAccount(String username, String password){
        //todo: add error checking here to stop view from moving forward
        //if account already exists -> return false -> prohibits view from moving to app
        //or it will be based off firebase authentication, but that is models job, so this will not handle that
        //also handle trimming for whitespace and empty strings
        authenticator.addAccount(username, password);

        return true;
    }

    public boolean OnLogin(String username, String password){
        //if authenticator method works then move on return true, if not do something else
        //also handle trimming for whitespace and empty strings
        authenticator.authenticate(username, password);

        return true;
    }
}
