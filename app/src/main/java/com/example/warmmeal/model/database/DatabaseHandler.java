package com.example.warmmeal.model.database;

import android.content.Context;
import android.util.Log;

import com.example.warmmeal.fragment_calender.view.OnAddCalendarMealResponse;
import com.example.warmmeal.fragment_calender.view.OnDeleteCalendarMealResponse;
import com.example.warmmeal.fragment_calender.view.OnGetCalendarMealsResponse;
import com.example.warmmeal.fragment_favourite.view.OnAddToFavouriteResponse;
import com.example.warmmeal.fragment_favourite.view.OnDeleteFromFavouriteResponse;
import com.example.warmmeal.fragment_favourite.view.OnGetFavouriteMealResponse;
import com.example.warmmeal.model.contracts.LocalDataSource;
import com.example.warmmeal.model.firebase.FirebaseHandler;
import com.example.warmmeal.model.pojo.CalenderMeal;
import com.example.warmmeal.model.pojo.FavouriteMeal;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class DatabaseHandler implements LocalDataSource {

    Context context;
    private static DatabaseHandler databaseHandler;
    private MealDAO mealDAO;

    private DatabaseHandler(Context context) {
        this.context = context;
        mealDAO = MealDatabase.getInstance(context).mealDAO();
    }

    public static DatabaseHandler getInstance(Context context) {
        if (databaseHandler == null) {
            databaseHandler = new DatabaseHandler(context);
        }
        return databaseHandler;
    }


    @Override
    public Completable insertFavouriteMeal(FavouriteMeal meal) {
        return mealDAO.insertMeal(meal);
    }

    @Override
    public Flowable<List<FavouriteMeal>> getAllFavouriteMeals(String userId) {

        return mealDAO.getAllMeals(userId);
    }

    @Override
    public Completable deleteFavouriteMeal(FavouriteMeal meal) {
        return mealDAO.deleteFavouriteMeal(meal);
    }

    @Override
    public Completable insertCalenderMeal(CalenderMeal meal) {
        return mealDAO.insertCalenderMeal(meal);
    }

    @Override
    public Flowable<List<CalenderMeal>> getAllCalenderMeals(String userId) {
        return mealDAO.getAllCalenderMeals(userId);
    }

    @Override
    public Completable deleteCalenderMeal(CalenderMeal meal) {
        return mealDAO.deleteCalenderMeal(meal);

    }

}
