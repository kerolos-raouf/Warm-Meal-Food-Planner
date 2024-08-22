package com.example.warmmeal.meal_screen.view;

import com.example.warmmeal.model.pojo.Meals;

public interface OnMealScreenResponse {

    void onGetMealByIdSuccess(Meals meals);
    void onAddMealToPlanSuccess(String day);
    void onFailure(String message);

}
