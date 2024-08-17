package com.example.warmmeal.fragment_profile.presenter;

import com.example.warmmeal.fragment_profile.view.OnLogOutResponse;
import com.example.warmmeal.login_ways.view.OnSetUserRegisterSateResponse;
import com.example.warmmeal.model.repository.Repository;

public class ProfilePresenter {

    Repository repository;

    private static ProfilePresenter presenter;

    public ProfilePresenter(Repository repository) {
        this.repository = repository;
    }

    public static ProfilePresenter getInstance(Repository repository) {
        if (presenter == null) {
            presenter = new ProfilePresenter(repository);
        }
        return presenter;
    }

    public void logOut(OnLogOutResponse response) {
        repository.signOutUser(response);
    }


    public void setUserRegisterState(boolean loggedIn, OnSetUserRegisterSateResponse response) {
        repository.setUserRegisterState(loggedIn, response);
    }

}
