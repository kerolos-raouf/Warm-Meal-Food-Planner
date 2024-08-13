package com.example.warmmeal.model.Repository;

import com.example.warmmeal.login.view.OnLoginResponse;
import com.example.warmmeal.signup.view.OnCreatingAccountResponse;

public interface Repository {

    void createNewUser(String userName, String password, OnCreatingAccountResponse response);
    void loginUser(String userName, String password, OnLoginResponse response);

}
