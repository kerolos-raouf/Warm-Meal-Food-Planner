package com.example.warmmeal.model.database.type_converter;

import androidx.room.TypeConverter;

import com.example.warmmeal.model.pojo.Meal;
import com.google.gson.Gson;

public class MealConverter {

    @TypeConverter
    public String fromMealToString(Meal meal)
    {
        return new Gson().toJson(meal);
    }

    @TypeConverter
    public Meal fromStringToMeal(String mealString)
    {
        return new Gson().fromJson(mealString,Meal.class);
    }

}
