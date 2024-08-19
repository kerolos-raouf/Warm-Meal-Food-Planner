package com.example.warmmeal.model.pojo;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.warmmeal.model.util.Day;

import org.jetbrains.annotations.NotNull;

@Entity(primaryKeys = {"userId","day","meal"})
public class CalenderMeal {



    @NotNull
    public String userId;
    @NotNull
    public Day day;
    @NotNull
    public Meal meal;

    public CalenderMeal() {
    }

    public CalenderMeal(@NonNull String userId, @NonNull Day day, @NonNull Meal meal) {
        this.userId = userId;
        this.day = day;
        this.meal = meal;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Meal getMeal() {
        return meal;
    }

    public void setMeal(Meal meal) {
        this.meal = meal;
    }

    public Day getDay() {
        return day;
    }

    public void setDay(Day day) {
        this.day = day;
    }
}
