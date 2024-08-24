package com.example.warmmeal.meal_screen.view;

import com.example.warmmeal.model.internet_connection.ConnectivityObserver;
import com.example.warmmeal.model.pojo.Meals;

public interface IMealScreen {

    void onNetworkStateResponse(ConnectivityObserver.Status status, String message);
    void onFail(String message);
    void onGetMealByIdSuccess(Meals meals);
    void onAddMealToPlanSuccess(String day);
    void onFailure(String message);

}
