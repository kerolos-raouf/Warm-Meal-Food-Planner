package com.example.warmmeal.model.contracts;

import com.example.warmmeal.fragment_calender.view.OnAddCalendarMealResponse;
import com.example.warmmeal.fragment_calender.view.OnDeleteCalendarMealResponse;
import com.example.warmmeal.fragment_calender.view.OnGetCalendarMealsResponse;
import com.example.warmmeal.fragment_favourite.view.OnAddToFavouriteResponse;
import com.example.warmmeal.fragment_favourite.view.OnDeleteFromFavouriteResponse;
import com.example.warmmeal.fragment_favourite.view.OnGetFavouriteMealResponse;
import com.example.warmmeal.model.pojo.CalenderMeal;
import com.example.warmmeal.model.pojo.FavouriteMeal;

public interface LocalDataSource
{

    void insertFavouriteMeal(FavouriteMeal meal, OnAddToFavouriteResponse response);
    void getAllFavouriteMeals(String userId, OnGetFavouriteMealResponse response);
    void deleteFavouriteMeal(FavouriteMeal meal, OnDeleteFromFavouriteResponse response);

    void insertCalenderMeal(CalenderMeal meal, OnAddCalendarMealResponse response);
    void getAllCalenderMeals(String userId, OnGetCalendarMealsResponse response);
    void deleteCalenderMeal(CalenderMeal meal, OnDeleteCalendarMealResponse response);

}
