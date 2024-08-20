package com.example.warmmeal.model.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.warmmeal.model.database.type_converter.MealConverter;
import com.example.warmmeal.model.pojo.CalenderMeal;
import com.example.warmmeal.model.pojo.FavouriteMeal;

@Database(entities = {FavouriteMeal.class, CalenderMeal.class}, version = 1)
@TypeConverters(MealConverter.class)
public abstract class MealDatabase extends RoomDatabase {

    abstract MealDAO mealDAO();

    public static final String DATABASE_NAME = "meal_database";

    private static MealDatabase instance;

    public MealDatabase() {
    }

    public synchronized static MealDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(), MealDatabase.class, DATABASE_NAME).build();
        }
        return instance;
    }

}
