package com.example.warmmeal.login_ways.presenter;

import com.example.warmmeal.login_ways.view.OnLoginWithGmailResponse;
import com.example.warmmeal.login_ways.view.OnSetUserRegisterSateResponse;
import com.example.warmmeal.model.repository.Repository;
import com.google.firebase.auth.FirebaseUser;

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
      repository.signInUsingGmailAccount(idToken,response);
  }

  public FirebaseUser getCurrentUser()
  {
      return repository.getCurrentUser();
  }

  public boolean isUserLoggedIn()
  {
      return repository.isUserLoggedIn();
  }

  public void setUserRegisterState(boolean loggedIn, OnSetUserRegisterSateResponse response)
  {
      repository.setUserRegisterState(loggedIn,response);
  }

}
