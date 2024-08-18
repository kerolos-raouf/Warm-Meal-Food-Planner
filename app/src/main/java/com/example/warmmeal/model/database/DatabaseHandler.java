package com.example.warmmeal.model.database;

import android.content.Context;

import com.example.warmmeal.fragment_calender.view.OnAddCalendarMealResponse;
import com.example.warmmeal.fragment_calender.view.OnDeleteCalendarMealResponse;
import com.example.warmmeal.fragment_calender.view.OnGetCalendarMealsResponse;
import com.example.warmmeal.fragment_favourite.view.OnAddToFavouriteResponse;
import com.example.warmmeal.fragment_favourite.view.OnDeleteFromFavouriteResponse;
import com.example.warmmeal.fragment_favourite.view.OnGetFavouriteMealResponse;
import com.example.warmmeal.model.contracts.LocalDataSource;
import com.example.warmmeal.model.pojo.CalenderMeal;
import com.example.warmmeal.model.pojo.FavouriteMeal;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class DatabaseHandler implements LocalDataSource {

    Context context;
    private static DatabaseHandler databaseHandler;
    private MealDAO mealDAO;


    private final CompositeDisposable disposable;

    private DatabaseHandler(Context context) {
        this.context = context;
        mealDAO = MealDatabase.getInstance(context).mealDAO();
         disposable= new CompositeDisposable();
    }

    public static DatabaseHandler getInstance(Context context) {
        if (databaseHandler == null) {
            databaseHandler = new DatabaseHandler(context);
        }
        return databaseHandler;
    }


    @Override
    public void insertFavouriteMeal(FavouriteMeal meal, OnAddToFavouriteResponse response) {
        disposable.add(mealDAO.insertMeal(meal).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(
                response::onAddToFavouriteSuccess,
                throwable -> response.onAddToFavouriteFailure(throwable.getMessage())
        ));
    }

    @Override
    public void getAllFavouriteMeals(String userId, OnGetFavouriteMealResponse response) {
        disposable.add(mealDAO.getAllMeals(userId).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(
                response::onGetFavouriteMealSuccess,
                throwable -> response.onGetFavouriteMealFailure(throwable.getMessage())
        ));
    }

    @Override
    public void deleteFavouriteMeal(FavouriteMeal meal, OnDeleteFromFavouriteResponse response) {
        disposable.add(mealDAO.deleteFavouriteMeal(meal).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(
                response::onDeleteFromFavouriteSuccess,
                throwable -> response.onDeleteFromFavouriteFailure(throwable.getMessage())
        ));
    }

    @Override
    public void insertCalenderMeal(CalenderMeal meal, OnAddCalendarMealResponse response) {
        disposable.add(mealDAO.insertCalenderMeal(meal).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(
                response::onAddCalendarMealSuccess,
                throwable -> response.onAddCalendarMealFailure(throwable.getMessage())
        ));
    }

    @Override
    public void getAllCalenderMeals(String userId, OnGetCalendarMealsResponse response) {
        disposable.add(mealDAO.getAllCalenderMeals(userId).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(
                response::onGetCalendarMealsSuccess,
                throwable -> response.onGetCalendarMealsFailure(throwable.getMessage())
        ));
    }

    @Override
    public void deleteCalenderMeal(CalenderMeal meal, OnDeleteCalendarMealResponse response) {
        disposable.add(mealDAO.deleteCalenderMeal(meal).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(
                response::onDeleteCalendarMealSuccess,
                throwable -> response.onDeleteCalendarMealFailure(throwable.getMessage())
        ));
    }

    public void clearDisposable()
    {
        disposable.clear();
    }
}
