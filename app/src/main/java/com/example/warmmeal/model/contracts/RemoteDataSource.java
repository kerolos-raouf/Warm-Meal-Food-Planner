package com.example.warmmeal.model.contracts;

import com.example.warmmeal.model.pojo.Categories;
import com.example.warmmeal.model.pojo.Ingredients;
import com.example.warmmeal.model.pojo.Meals;

import io.reactivex.rxjava3.core.Flowable;

public interface RemoteDataSource {

    Flowable<Meals> getMealsByFirstLetter(char letter);
    Flowable<Meals> getRandomMeal();
    Flowable<Categories> getAllCategories();
    Flowable<Meals> getAllCountries();
    Flowable<Meals> getMealByName(String name);
    Flowable<Meals> getMealsByMainIngredient(String ingredient);
    Flowable<Meals> getMealsByCategory(String category);
    Flowable<Meals> getMealsByCountry(String country);
    Flowable<Ingredients> getIngredients();
    Flowable<Meals> getMealById(String id);
}
