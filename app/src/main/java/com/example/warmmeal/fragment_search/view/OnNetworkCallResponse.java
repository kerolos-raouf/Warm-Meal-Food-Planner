package com.example.warmmeal.fragment_search.view;

import com.example.warmmeal.model.contracts.RemoteDataSource;
import com.example.warmmeal.model.pojo.Meals;

public interface OnNetworkCallResponse  {

    void onGetRandomMealSuccess(Meals meals);
    void onGetRandomMealFailure(String message);

}
