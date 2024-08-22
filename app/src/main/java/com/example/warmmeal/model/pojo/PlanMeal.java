package com.example.warmmeal.model.pojo;

import androidx.room.Entity;

import com.example.warmmeal.model.util.Day;

import org.jetbrains.annotations.NotNull;

@Entity(primaryKeys = {"userId", "mealId","day"})
public class PlanMeal {


    @NotNull
    public String userId;
    @NotNull
    public Day day;
    @NotNull
    public String mealId;
    @NotNull
    public String mealName;
    @NotNull
    public String mealImage;

    public PlanMeal() {
    }

    public PlanMeal(@NotNull String userId, @NotNull Day day, @NotNull String mealId, @NotNull String mealName, @NotNull String mealImage) {
        this.userId = userId;
        this.day = day;
        this.mealId = mealId;
        this.mealName = mealName;
        this.mealImage = mealImage;
    }

}
