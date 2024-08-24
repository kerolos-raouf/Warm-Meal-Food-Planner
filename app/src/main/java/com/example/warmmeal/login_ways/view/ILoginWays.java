package com.example.warmmeal.login_ways.view;

import com.example.warmmeal.model.internet_connection.ConnectivityObserver;

public interface ILoginWays {

    void onGetNetWorkState(ConnectivityObserver.Status status, String message);
    void onFail(String message);

}
