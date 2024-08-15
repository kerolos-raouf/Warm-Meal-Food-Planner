package com.example.warmmeal.model.network;

import com.example.warmmeal.model.pojo.Categories;
import com.example.warmmeal.model.pojo.Meals;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MealDTO {

    @GET("random.php")
    Observable<Meals> getRandomMeal();


    @GET("search.php")
    Observable<Meals> getMealsByFirstLetter(@Query("f") char letter);

    @GET("categories.php")
    Observable<Categories> getAllCategories();

}
