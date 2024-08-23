package com.example.warmmeal.model.network;

import com.example.warmmeal.model.pojo.Categories;
import com.example.warmmeal.model.pojo.Ingredients;
import com.example.warmmeal.model.pojo.Meals;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MealDTO {

    @GET("random.php")
    Flowable<Meals> getRandomMeal();


    @GET("search.php")
    Flowable<Meals> getMealsByFirstLetter(@Query("f") char letter);

    @GET("categories.php")
    Flowable<Categories> getAllCategories();

    @GET("list.php?a=list")
    Flowable<Meals> getAllCountries();

    @GET("search.php")
    Flowable<Meals> getMealByName(@Query("s") String name);

    @GET("filter.php")
    Flowable<Meals> getMealByCategory(@Query("c") String category);

    @GET("filter.php")
    Flowable<Meals> getMealByCountry(@Query("a") String country);

    @GET("filter.php")
    Flowable<Meals> getMealByMainIngredient(@Query("i") String ingredient);


    @GET("list.php?i=list")
    Flowable<Ingredients> getIngredients();

    @GET("lookup.php")
    Flowable<Meals> getMealById(@Query("i") String mealId);

}
