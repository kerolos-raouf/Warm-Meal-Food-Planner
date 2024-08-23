package com.example.warmmeal.main_screen.presenter;

import android.net.ConnectivityManager;
import android.widget.Toast;

import com.example.warmmeal.main_screen.view.IMainScreen;
import com.example.warmmeal.model.internet_connection.ConnectivityObserver;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MainScreenPresenter {


    private IMainScreen iMainScreen;
    private ConnectivityObserver connectivityObserver;
    private CompositeDisposable compositeDisposable;

    public MainScreenPresenter(ConnectivityObserver connectivityObserver,IMainScreen iMainScreen) {
        this.iMainScreen = iMainScreen;
        this.connectivityObserver = connectivityObserver;
        this.compositeDisposable = new CompositeDisposable();
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
                                    iMainScreen.onNetworkCallResponse(ConnectivityObserver.Status.Available, "Connected");
                                    break;
                                case Unavailable:
                                    ConnectivityObserver.InternetStatus = ConnectivityObserver.Status.Unavailable;
                                    iMainScreen.onNetworkCallResponse(ConnectivityObserver.Status.Unavailable, "Disconnected");
                                    break;
                                case Losing:
                                    ConnectivityObserver.InternetStatus = ConnectivityObserver.Status.Losing;
                                    iMainScreen.onNetworkCallResponse(ConnectivityObserver.Status.Losing, "Losing Connection");
                                    break;
                                case Lost:
                                    ConnectivityObserver.InternetStatus = ConnectivityObserver.Status.Lost;
                                    iMainScreen.onNetworkCallResponse(ConnectivityObserver.Status.Lost, "Connection Lost");
                                    break;
                            }
                        },
                        throwable -> {
                            iMainScreen.onFail(throwable.getMessage());
                        }
                )
        );
    }

    public void clearDisposable()
    {
        compositeDisposable.clear();
    }

}
