package com.example.warmmeal.loginways.presenter;

import com.example.warmmeal.signup.view.OnCreatingAccountResponse;
import com.example.warmmeal.model.Repository.RepositoryImpl;

public class LoginWaysPresenter {


    private RepositoryImpl repository;
    private static LoginWaysPresenter presenter;

    private LoginWaysPresenter(RepositoryImpl repository)
    {
        this.repository = repository;
    }

    public static LoginWaysPresenter getInstance(RepositoryImpl repository)
    {
        if(presenter == null)
        {
            presenter = new LoginWaysPresenter(repository);
        }

        return presenter;
    }


    void createNewAccount(String userName, String password, OnCreatingAccountResponse response)
    {
        //repository.createNewUser();
    }

}
