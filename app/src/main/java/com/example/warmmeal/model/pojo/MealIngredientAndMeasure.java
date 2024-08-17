package com.example.warmmeal.model.pojo;

import java.util.ArrayList;

public class MealIngredientAndMeasure {
    String ingredient;
    String measure;
    String imageUrl;
    public MealIngredientAndMeasure(String ingredient, String measure, String imageUrl) {
        this.ingredient = ingredient;
        this.measure = measure;
        this.imageUrl = imageUrl;
    }

    public MealIngredientAndMeasure(String ingredient, String measure) {
        this.ingredient = ingredient;
        this.measure = measure;
        this.imageUrl = "";
    }

    public String getIngredient() {
        return ingredient;
    }

    public void setIngredient(String ingredient) {
        this.ingredient = ingredient;
    }

    public String getMeasure() {
        return measure;
    }

    public void setMeasure(String measure) {
        this.measure = measure;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
