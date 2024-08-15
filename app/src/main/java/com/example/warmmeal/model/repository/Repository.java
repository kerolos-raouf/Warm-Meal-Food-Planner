package com.example.warmmeal.model.repository;

import com.example.warmmeal.login.view.OnLoginResponse;
import com.example.warmmeal.login_ways.view.OnLoginWithGmailResponse;
import com.example.warmmeal.model.contracts.RemoteDataSource;
import com.example.warmmeal.signup.view.OnCreatingAccountResponse;

public interface Repository extends RemoteDataSource {

    void createNewUser(String userName, String password, OnCreatingAccountResponse response);
    void loginUser(String userName, String password, OnLoginResponse response);
    void loginWithGmail(String idToken, OnLoginWithGmailResponse response);

}
