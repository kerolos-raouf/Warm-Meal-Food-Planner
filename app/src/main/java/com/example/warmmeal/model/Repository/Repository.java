package com.example.warmmeal.model.Repository;

import com.example.warmmeal.signup.view.OnCreatingAccountResponse;

public interface Repository {

    void createNewUser(String userName, String password, OnCreatingAccountResponse response);
    void loginWithUsernameAndPassword(String userName, String password, OnCreatingAccountResponse response);

}
