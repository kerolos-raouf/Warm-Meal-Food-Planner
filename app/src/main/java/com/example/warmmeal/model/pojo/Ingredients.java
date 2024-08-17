package com.example.warmmeal.model.pojo;

import java.util.ArrayList;

public class Ingredients {

    ArrayList<Ingredient> meals;

    public Ingredients() {
    }

    public Ingredients(ArrayList<Ingredient> meals) {
        this.meals = meals;
    }

    public ArrayList<Ingredient> getMeals() {
        return meals;
    }

    public void setMeals(ArrayList<Ingredient> meals) {
        this.meals = meals;
    }
}
