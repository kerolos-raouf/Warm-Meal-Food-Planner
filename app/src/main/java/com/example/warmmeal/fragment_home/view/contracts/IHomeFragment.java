package com.example.warmmeal.fragment_home.view.contracts;

import com.example.warmmeal.model.pojo.Meals;

public interface IHomeFragment {

    void onGetRandomMealSuccess(Meals meals);
    void onGetRandomMealComplete();
    void onFailure(String message);

}
