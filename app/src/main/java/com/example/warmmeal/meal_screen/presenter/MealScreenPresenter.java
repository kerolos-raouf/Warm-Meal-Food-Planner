package com.example.warmmeal.meal_screen.presenter;

import com.example.warmmeal.fragment_favourite.view.OnAddToFavouriteResponse;
import com.example.warmmeal.fragment_favourite.view.OnDeleteFromFavouriteResponse;
import com.example.warmmeal.fragment_favourite.view.OnGetFavouriteMealResponse;
import com.example.warmmeal.meal_screen.view.OnMealScreenResponse;
import com.example.warmmeal.model.pojo.FavouriteMeal;
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

    public void addFavouriteMeal(FavouriteMeal favouriteMeal, OnAddToFavouriteResponse response) {
        repository.insertFavouriteMeal(favouriteMeal, response);
    }

    public void deleteFromFavourite(FavouriteMeal favouriteMeal, OnDeleteFromFavouriteResponse response) {
        repository.deleteFavouriteMeal(favouriteMeal, response);
    }



}
