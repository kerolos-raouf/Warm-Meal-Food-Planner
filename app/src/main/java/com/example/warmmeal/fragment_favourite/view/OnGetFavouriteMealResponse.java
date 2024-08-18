package com.example.warmmeal.fragment_favourite.view;

import com.example.warmmeal.model.pojo.FavouriteMeal;

import java.util.List;

public interface OnGetFavouriteMealResponse {


    void onGetFavouriteMealSuccess(List<FavouriteMeal> favouriteMeals);
    void onGetFavouriteMealFailure(String message);
}
