package com.viewmodel;

import androidx.lifecycle.ViewModel;


import com.model.Authenticator;

public class LoginViewModel extends ViewModel {
    private Authenticator authenticator;

    public LoginViewModel(){
        authenticator = new Authenticator();
    }

    public boolean onNewAccount(String username, String password){
        //I am going to add error checking here to know if I need to stop anything in the view
        //if account already exists -> return false -> prohibits view from moving to app
        //or it will be based off firebase authentication, but that is models job, so this will not handle that
        authenticator.addAccount(username, password);

        return true;
    }
}
