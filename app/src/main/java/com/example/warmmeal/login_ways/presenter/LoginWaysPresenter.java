package com.example.warmmeal.login_ways.presenter;

import com.example.warmmeal.login_ways.view.ILoginWays;
import com.example.warmmeal.login_ways.view.OnLoginWithGmailResponse;
import com.example.warmmeal.login_ways.view.OnSetUserRegisterSateResponse;
import com.example.warmmeal.model.internet_connection.ConnectivityObserver;
import com.example.warmmeal.model.repository.Repository;
import com.google.firebase.auth.FirebaseUser;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class LoginWaysPresenter {


    private final Repository repository;
    private static LoginWaysPresenter presenter;
    private CompositeDisposable compositeDisposable;
    private ConnectivityObserver connectivityObserver;
    private ILoginWays iLoginWays;

    private LoginWaysPresenter(Repository repository, ConnectivityObserver connectivityObserver)
    {
        this.repository = repository;
        compositeDisposable = new CompositeDisposable();
        this.connectivityObserver = connectivityObserver;
    }

    public static LoginWaysPresenter getInstance(Repository repository, ConnectivityObserver connectivityObserver, ILoginWays iLoginWays)
    {
        if(presenter == null)
        {
            presenter = new LoginWaysPresenter(repository,connectivityObserver);
        }

        presenter.iLoginWays = iLoginWays;

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


    public void checkInternetStatus() {
        compositeDisposable.add(connectivityObserver.observeConnectivity()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        status -> {
                            switch (status)
                            {
                                case Available:
                                    ConnectivityObserver.InternetStatus = ConnectivityObserver.Status.Available;
                                    iLoginWays.onGetNetWorkState(ConnectivityObserver.Status.Available, "Connected");
                                    break;
                                case Unavailable:
                                    ConnectivityObserver.InternetStatus = ConnectivityObserver.Status.Unavailable;
                                    iLoginWays.onGetNetWorkState(ConnectivityObserver.Status.Unavailable, "Disconnected");
                                    break;
                                case Losing:
                                    ConnectivityObserver.InternetStatus = ConnectivityObserver.Status.Losing;
                                    iLoginWays.onGetNetWorkState(ConnectivityObserver.Status.Losing, "Losing Connection");
                                    break;
                                case Lost:
                                    ConnectivityObserver.InternetStatus = ConnectivityObserver.Status.Lost;
                                    iLoginWays.onGetNetWorkState(ConnectivityObserver.Status.Lost, "Connection Lost");
                                    break;
                            }
                        },
                        throwable -> {
                            iLoginWays.onFail(throwable.getMessage());
                        }
                )
        );
    }

}
