package com.example.warmmeal.fragment_profile.view;

import com.example.warmmeal.model.pojo.FavouriteMeal;
import com.example.warmmeal.model.pojo.PlanMeal;

import java.util.ArrayList;

public interface IProfileFragment {

    void onGetFavouriteMealsSuccess(ArrayList<FavouriteMeal> favouriteMeals);
    void onGetPlanMealsSuccess(ArrayList<PlanMeal> planMeals);
    void onFail(String message);

}
