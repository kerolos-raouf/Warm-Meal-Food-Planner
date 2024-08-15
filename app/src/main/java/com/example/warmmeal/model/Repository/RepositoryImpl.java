package com.example.warmmeal.model.Repository;

import com.example.warmmeal.login.view.OnLoginResponse;
import com.example.warmmeal.login_ways.view.OnLoginWithGmailResponse;
import com.example.warmmeal.signup.view.OnCreatingAccountResponse;
import com.example.warmmeal.model.contracts.ManagingAccount;

public class RepositoryImpl implements Repository{


    ManagingAccount managingAccount;

    private static RepositoryImpl repository;

    private RepositoryImpl(ManagingAccount managingAccount)
    {
        this.managingAccount = managingAccount;
    }

    public static RepositoryImpl getInstance(ManagingAccount managingAccount)
    {
        if(repository == null)
        {
            repository = new RepositoryImpl(managingAccount);
        }
        return repository;
    }

    @Override
    public void createNewUser(String userName, String password, OnCreatingAccountResponse response) {
        managingAccount.signUpWithUserNameAndPassword(userName,password,response);
    }

    @Override
    public void loginUser(String userName, String password, OnLoginResponse response) {
        managingAccount.loginWithUserNameAndPassword(userName,password,response);
    }

    @Override
    public void loginWithGmail(String idToken, OnLoginWithGmailResponse response) {
        managingAccount.signInUsingGmailAccount(idToken,response);
    }
}
