package com.example.warmmeal.model.network;

import com.example.warmmeal.model.pojo.Meals;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.Call;
import retrofit2.http.GET;

public interface MealDTO {

    @GET("random.php")
    Observable<Meals> getRandomMeal();
    /*@GET("random.php")
    Call<Meals> getRandomMeal();*/

}
