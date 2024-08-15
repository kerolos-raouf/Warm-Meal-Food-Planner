package com.example.warmmeal.model.pojo;

import java.util.List;

public class Meals {

    List<Meal> meals;

    public Meals() {
    }

    public Meals(List<Meal> meals) {
        this.meals = meals;
    }

    public List<Meal> getMeals() {
        return meals;
    }

    public void setMeals(List<Meal> meals) {
        this.meals = meals;
    }
}
