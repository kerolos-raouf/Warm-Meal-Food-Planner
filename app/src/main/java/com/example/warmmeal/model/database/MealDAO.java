package com.example.warmmeal.model.database;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.warmmeal.model.pojo.CalenderMeal;
import com.example.warmmeal.model.pojo.FavouriteMeal;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Observable;

@Dao
public interface MealDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    Completable insertMeal(FavouriteMeal meal);

    @Query("SELECT * FROM FavouriteMeal WHERE userId = :userId")
    Observable<List<FavouriteMeal>> getAllMeals(String userId);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    Completable insertCalenderMeal(CalenderMeal meal);

    @Query("SELECT * FROM CalenderMeal WHERE userId = :userId")
    Observable<List<CalenderMeal>> getAllCalenderMeals(String userId);

    @Delete
    Completable deleteFavouriteMeal(FavouriteMeal meal);

    @Delete
    Completable deleteCalenderMeal(CalenderMeal meal);

}
