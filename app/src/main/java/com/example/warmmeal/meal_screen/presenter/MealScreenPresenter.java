package com.example.warmmeal.meal_screen.presenter;

import com.example.warmmeal.fragment_favourite.view.OnAddToFavouriteResponse;
import com.example.warmmeal.fragment_favourite.view.OnDeleteFromFavouriteResponse;
import com.example.warmmeal.meal_screen.view.OnMealScreenResponse;
import com.example.warmmeal.model.firebase.FirebaseHandler;
import com.example.warmmeal.model.pojo.PlanMeal;
import com.example.warmmeal.model.pojo.FavouriteMeal;
import com.example.warmmeal.model.repository.Repository;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MealScreenPresenter {

    private Repository repository;

    private static MealScreenPresenter mealScreenPresenter;

    private OnMealScreenResponse response;

    private final CompositeDisposable compositeDisposable;

    private  MealScreenPresenter(Repository repository, OnMealScreenResponse response) {
        this.repository = repository;
        compositeDisposable = new CompositeDisposable();
        this.response = response;
    }

    public static MealScreenPresenter getInstance(Repository repository, OnMealScreenResponse response) {
        if(mealScreenPresenter == null) {
            mealScreenPresenter = new MealScreenPresenter(repository, response);
        }
        return mealScreenPresenter;
    }

    public void getMealById(String mealId, OnMealScreenResponse response) {
        compositeDisposable.add(repository.getMealById(mealId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        response::onGetMealByIdSuccess,
                        throwable -> response.onFailure(throwable.getMessage()
                        )
                ));
    }

    public void addFavouriteMeal(FavouriteMeal favouriteMeal, OnAddToFavouriteResponse response) {

        if(FirebaseHandler.CURRENT_USER_ID != null)
        {
            compositeDisposable.add(repository.insertFavouriteMeal(favouriteMeal)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            response::onAddToFavouriteSuccess,
                            throwable -> response.onAddToFavouriteFailure(throwable.getMessage())
                    ));
        }else {
            response.onAddToFavouriteFailure("You are not logged in");
        }
    }

    public void deleteFromFavourite(FavouriteMeal favouriteMeal, OnDeleteFromFavouriteResponse response) {
        if(FirebaseHandler.CURRENT_USER_ID != null)
        {
            compositeDisposable.add(repository.deleteFavouriteMeal(favouriteMeal)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            response::onDeleteFromFavouriteSuccess,
                            throwable -> response.onDeleteFromFavouriteFailure(throwable.getMessage())
                    ));
        }else {
            response.onDeleteFromFavouriteFailure("You are not logged in");
        }
    }

    public void addMealToPlan(PlanMeal planMeal)
    {
        compositeDisposable.add(repository.insertCalenderMeal(planMeal)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        () -> {response.onAddMealToPlanSuccess(planMeal.day.toString());},
                        throwable -> {response.onFailure(throwable.getMessage());}
                ));
    }

    public void clearDisposable()
    {
        compositeDisposable.clear();
    }



}
