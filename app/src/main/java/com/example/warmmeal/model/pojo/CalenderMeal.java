package com.example.warmmeal.model.pojo;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.warmmeal.model.util.Day;

@Entity
public class CalenderMeal {

    @PrimaryKey(autoGenerate = true)
    public int id;
    String userId;
    Day day;
    Meal meal;

    public CalenderMeal() {
    }

    public CalenderMeal(String userId, Day day, Meal meal) {
        this.userId = userId;
        this.day = day;
        this.meal = meal;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
