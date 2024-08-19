package com.example.warmmeal.model.pojo;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import org.jetbrains.annotations.NotNull;

@Entity(primaryKeys = {"userId","idMeal"})
public class FavouriteMeal {


    @NotNull
    public String userId;
    @NotNull
    public String idMeal;
    @NotNull
    public String strMeal;
    @NotNull
    public String strMealThumb;


    public FavouriteMeal() {
    }


    public FavouriteMeal(@NotNull String userId, @NotNull String idMeal, @NotNull String strMeal, @NotNull String strMealThumb) {
        this.userId = userId;
        this.idMeal = idMeal;
        this.strMeal = strMeal;
        this.strMealThumb = strMealThumb;
    }
}
