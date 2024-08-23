package com.example.warmmeal.fragment_profile.view;

import com.example.warmmeal.model.pojo.FavouriteMeal;
import com.example.warmmeal.model.pojo.PlanMeal;

import java.util.ArrayList;

public interface OnDownloadDataResponse {

    void onDownloadDataSuccess(ArrayList<FavouriteMeal> favouriteMeals, ArrayList<PlanMeal> planMeals);
    void onDownloadDataFail(String message);
}
