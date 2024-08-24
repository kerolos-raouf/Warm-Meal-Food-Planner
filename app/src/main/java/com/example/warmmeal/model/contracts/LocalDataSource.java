package com.example.warmmeal.model.contracts;

import com.example.warmmeal.model.pojo.PlanMeal;
import com.example.warmmeal.model.pojo.FavouriteMeal;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;

public interface LocalDataSource
{

    Completable insertFavouriteMeal(FavouriteMeal meal);
    Flowable<List<FavouriteMeal>> getAllFavouriteMeals(String userId);
    Completable deleteFavouriteMeal(FavouriteMeal meal);
    Flowable<Integer> isFavouriteMealExists(String userId, String mealId);

    Completable insertCalenderMeal(PlanMeal meal);
    Flowable<List<PlanMeal>> getAllCalenderMeals(String userId );
    Completable deleteCalenderMeal(PlanMeal meal);

}
