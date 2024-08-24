package com.example.warmmeal.model.database;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.warmmeal.model.pojo.PlanMeal;
import com.example.warmmeal.model.pojo.FavouriteMeal;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;

@Dao
public interface MealDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    Completable insertMeal(FavouriteMeal meal);

    @Query("SELECT * FROM FavouriteMeal WHERE userId = :userId")
    Flowable<List<FavouriteMeal>> getAllMeals(String userId);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    Completable insertCalenderMeal(PlanMeal meal);

    @Query("SELECT * FROM PlanMeal WHERE userId = :userId")
    Flowable<List<PlanMeal>> getAllCalenderMeals(String userId);

    @Delete
    Completable deleteFavouriteMeal(FavouriteMeal meal);

    @Delete
    Completable deleteCalenderMeal(PlanMeal meal);

    @Query("SELECT COUNT() FROM favouritemeal WHERE userId = :userId and idMeal = :mealId")
    Flowable<Integer> isFavouriteMealExists(String userId,String mealId);

}
