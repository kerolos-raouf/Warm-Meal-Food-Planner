package com.example.warmmeal.signup.presenter;

import com.example.warmmeal.loginways.presenter.LoginWaysPresenter;
import com.example.warmmeal.model.Repository.RepositoryImpl;
import com.example.warmmeal.signup.view.OnCreatingAccountResponse;

public class SignUpPresenter {


    private RepositoryImpl repository;
    private static SignUpPresenter presenter;

    private SignUpPresenter(RepositoryImpl repository)
    {
        this.repository = repository;
    }

    public static SignUpPresenter getInstance(RepositoryImpl repository)
    {
        if(presenter == null)
        {
            presenter = new SignUpPresenter(repository);
        }

        return presenter;
    }


    void createNewAccount(String userName, String password, OnCreatingAccountResponse response)
    {
        repository.createNewUser(userName,password,response);
    }

}
