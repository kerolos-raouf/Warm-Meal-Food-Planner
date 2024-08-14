package com.example.warmmeal.signup.presenter;

import com.example.warmmeal.model.Repository.Repository;
import com.example.warmmeal.signup.view.ISignUpView;
import com.example.warmmeal.signup.view.OnCreatingAccountResponse;

public class SignUpPresenter {


    private ISignUpView iSignUpView;
    private final Repository repository;
    private static SignUpPresenter presenter;

    private SignUpPresenter(Repository repository,ISignUpView iSignUpView)
    {
        this.repository = repository;
        this.iSignUpView = iSignUpView;
    }

    public static SignUpPresenter getInstance(Repository repository,ISignUpView iSignUpView)
    {
        if(presenter == null)
        {
            presenter = new SignUpPresenter(repository,iSignUpView);
        }

        return presenter;
    }


    public void createNewAccount(String userName, String password, OnCreatingAccountResponse response)
    {
        repository.createNewUser(userName,password,response);
    }

}
