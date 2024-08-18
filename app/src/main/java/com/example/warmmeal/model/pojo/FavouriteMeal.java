package com.example.warmmeal.model.pojo;


import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class FavouriteMeal {

    @PrimaryKey(autoGenerate = true)
    public int id;
    String userId;
    Meal meal;

    public FavouriteMeal() {
    }

    public FavouriteMeal(String userId, Meal meal) {
        this.userId = userId;
        this.meal = meal;
    }

    public int getId() {
        return id;
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
}
