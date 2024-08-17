package com.example.warmmeal.meal_screen.presenter;

import com.example.warmmeal.meal_screen.view.OnMealScreenResponse;
import com.example.warmmeal.model.repository.Repository;

public class MealScreenPresenter {

    private Repository repository;

    private static MealScreenPresenter mealScreenPresenter;

    private  MealScreenPresenter(Repository repository) {
        this.repository = repository;
    }

    public static MealScreenPresenter getInstance(Repository repository) {
        if(mealScreenPresenter == null) {
            mealScreenPresenter = new MealScreenPresenter(repository);
        }
        return mealScreenPresenter;
    }

    public void getMealById(String mealId, OnMealScreenResponse response) {
        repository.getMealById(mealId, response);
    }



}
