package com.example.warmmeal.login_ways.presenter;

import com.example.warmmeal.login_ways.view.OnLoginWithGmailResponse;
import com.example.warmmeal.model.Repository.Repository;
import com.example.warmmeal.signup.view.OnCreatingAccountResponse;
import com.example.warmmeal.model.Repository.RepositoryImpl;

public class LoginWaysPresenter {


    private Repository repository;
    private static LoginWaysPresenter presenter;

    private LoginWaysPresenter(Repository repository)
    {
        this.repository = repository;
    }

    public static LoginWaysPresenter getInstance(Repository repository)
    {
        if(presenter == null)
        {
            presenter = new LoginWaysPresenter(repository);
        }

        return presenter;
    }


  public void loginWithGmail(String idToken, OnLoginWithGmailResponse response)
  {
      repository.loginWithGmail(idToken,response);
  }

}
