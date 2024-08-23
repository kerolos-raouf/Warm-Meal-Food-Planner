package com.example.warmmeal.model.database;

import android.content.Context;

import com.example.warmmeal.model.contracts.LocalDataSource;
import com.example.warmmeal.model.pojo.PlanMeal;
import com.example.warmmeal.model.pojo.FavouriteMeal;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;

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
    public Completable insertCalenderMeal(PlanMeal meal) {
        return mealDAO.insertCalenderMeal(meal);
    }

    @Override
    public Flowable<List<PlanMeal>> getAllCalenderMeals(String userId) {
        return mealDAO.getAllCalenderMeals(userId);
    }

    @Override
    public Completable deleteCalenderMeal(PlanMeal meal) {
        return mealDAO.deleteCalenderMeal(meal);

    }

}
