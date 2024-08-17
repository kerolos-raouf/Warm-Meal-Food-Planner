package com.example.warmmeal.model.contracts;

import com.example.warmmeal.login_ways.view.OnSetUserRegisterSateResponse;

public interface ManagingAccountState {

    void setUserRegisterState(boolean loggedIn, OnSetUserRegisterSateResponse response);
    boolean isUserLoggedIn();

}
