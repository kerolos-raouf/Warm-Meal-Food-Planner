package com.example.warmmeal.login.presenter;

import com.example.warmmeal.login.view.OnLoginResponse;
import com.example.warmmeal.model.repository.Repository;

public class LoginPresenter {


    private Repository repository;
    private static LoginPresenter presenter;

    private LoginPresenter(Repository repository){
        this.repository = repository;
    }

    public static LoginPresenter getInstance(Repository repository)
    {
        if(presenter == null)
        {
            presenter = new LoginPresenter(repository);
        }

        return presenter;
    }

    public void login(String userName, String password, OnLoginResponse response)
    {
        repository.loginWithUserNameAndPassword(userName,password,response);
    }




}
