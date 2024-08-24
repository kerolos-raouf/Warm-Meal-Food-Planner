package com.example.warmmeal.model.network;

import com.example.warmmeal.model.contracts.RemoteDataSource;
import com.example.warmmeal.model.pojo.Categories;
import com.example.warmmeal.model.pojo.Ingredients;
import com.example.warmmeal.model.pojo.Meals;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkAPI implements RemoteDataSource {

    private static final String BASE_URL = "https://www.themealdb.com/api/json/v1/1/";

    CompositeDisposable disposable;

    private static final int TIME_OUT_IN_MILLISECONDS = 5000;

    Retrofit retrofit;
    MealDTO mealDTO;
    private static NetworkAPI networkAPI;

    private NetworkAPI(){

        disposable = new CompositeDisposable();
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .build();
        mealDTO = retrofit.create(MealDTO.class);
    }

    public static NetworkAPI getInstance()
    {
        if(networkAPI == null)
        {
            networkAPI = new NetworkAPI();
        }
        return networkAPI;
    }

    @Override
    public Flowable<Meals> getMealsByFirstLetter(char letter) {
        return mealDTO.getMealsByFirstLetter(letter);
    }

    @Override
    public Flowable<Meals> getRandomMeal() {
        return mealDTO.getRandomMeal();
    }


    @Override
    public Flowable<Categories> getAllCategories() {
        return mealDTO.getAllCategories();
    }

    @Override
    public Flowable<Meals> getAllCountries() {
        return mealDTO.getAllCountries();
    }

    @Override
    public Flowable<Meals> getMealByName(String name) {
        return mealDTO.getMealByName(name);
    }


    @Override
    public Flowable<Meals> getMealsByMainIngredient(String ingredient) {
        return mealDTO.getMealByMainIngredient(ingredient);
    }

    @Override
    public Flowable<Meals> getMealsByCategory(String category) {
        return mealDTO.getMealByCategory(category);
    }

    @Override
    public Flowable<Meals> getMealsByCountry(String country) {
        return mealDTO.getMealByCountry(country);
    }

    @Override
    public Flowable<Ingredients> getIngredients() {
        return  mealDTO.getIngredients();
    }

    @Override
    public Flowable<Meals> getMealById(String id) {
        return mealDTO.getMealById(id);
    }

    public void clearDisposable()
    {
        disposable.clear();
    }
}
