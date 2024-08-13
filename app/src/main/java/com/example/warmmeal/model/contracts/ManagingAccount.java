package com.example.warmmeal.model.contracts;

import com.example.warmmeal.signup.view.OnCreatingAccountResponse;

public interface ManagingAccount {

    void signUpWithUserNameAndPassword(String userName, String password, OnCreatingAccountResponse response);
    void LoginWithUserNameAndPassword(String userName,String password, OnCreatingAccountResponse response);

}
