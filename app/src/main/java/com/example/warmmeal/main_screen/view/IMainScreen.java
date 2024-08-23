package com.example.warmmeal.main_screen.view;

import com.example.warmmeal.model.internet_connection.ConnectivityObserver;

public interface IMainScreen {

    void onNetworkCallResponse(ConnectivityObserver.Status status, String message);
    void onFail(String message);

}
