package com.example.warmmeal.model.pojo;


import androidx.room.Entity;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

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
    @NotNull
    public  boolean isFavourite;


    public FavouriteMeal() {
    }


    public FavouriteMeal(@NotNull String userId, @NotNull String idMeal, @NotNull String strMeal, @NotNull String strMealThumb, @NotNull boolean isFavourite) {
        this.userId = userId;
        this.idMeal = idMeal;
        this.strMeal = strMeal;
        this.strMealThumb = strMealThumb;
        this.isFavourite = isFavourite;
    }


    public static void getFavouriteMealsList(ArrayList<FavouriteMeal> favouriteMeals, ArrayList<Meal> allMeals)
    {

        for(FavouriteMeal favouriteMeal : favouriteMeals)
        {
            for(Meal meal : allMeals)
            {
                if(favouriteMeal.idMeal.equals(meal.getIdMeal()))
                {
                    meal.setFavourite(true);
                }
            }
        }
    }
}
