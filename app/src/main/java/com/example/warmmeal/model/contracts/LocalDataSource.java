package com.example.warmmeal.model.contracts;

import com.example.warmmeal.fragment_calender.view.OnAddCalendarMealResponse;
import com.example.warmmeal.fragment_calender.view.OnDeleteCalendarMealResponse;
import com.example.warmmeal.fragment_calender.view.OnGetCalendarMealsResponse;
import com.example.warmmeal.fragment_favourite.view.OnAddToFavouriteResponse;
import com.example.warmmeal.fragment_favourite.view.OnDeleteFromFavouriteResponse;
import com.example.warmmeal.fragment_favourite.view.OnGetFavouriteMealResponse;
import com.example.warmmeal.model.pojo.CalenderMeal;
import com.example.warmmeal.model.pojo.FavouriteMeal;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Observable;

public interface LocalDataSource
{

    Completable insertFavouriteMeal(FavouriteMeal meal);
    Flowable<List<FavouriteMeal>> getAllFavouriteMeals(String userId);
    Completable deleteFavouriteMeal(FavouriteMeal meal);

    Completable insertCalenderMeal(CalenderMeal meal);
    Flowable<List<CalenderMeal>> getAllCalenderMeals(String userId );
    Completable deleteCalenderMeal(CalenderMeal meal);

}
