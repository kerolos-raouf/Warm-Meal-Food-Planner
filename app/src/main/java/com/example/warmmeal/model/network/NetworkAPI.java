package com.example.warmmeal.model.network;

import com.example.warmmeal.fragment_search.view.OnNetworkCallResponse;
import com.example.warmmeal.model.contracts.RemoteDataSource;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkAPI implements RemoteDataSource {

    private static final String BASE_URL = "https://www.themealdb.com/api/json/v1/1/";


    Retrofit retrofit;
    MealDTO mealDTO;
    private static NetworkAPI networkAPI;

    private NetworkAPI(){
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
    public void getMealsByFirstLetter(char letter, OnNetworkCallResponse response) {
        mealDTO.getMealsByFirstLetter(letter).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe((meals) -> {
            response.onGetMealByCharacterSuccess(meals);
        }, (throwable) -> {
            response.onGetMealByCharacterFailure(throwable.getMessage());
        });
    }

    @Override
    public void getRandomMeal(OnNetworkCallResponse response) {
        mealDTO.getRandomMeal().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe((meals) -> {
            response.onGetMealByCharacterSuccess(meals);
        }, (throwable) -> {
            response.onGetMealByCharacterFailure(throwable.getMessage());
        });
    }

    @Override
    public void getMealByName(String name, OnNetworkCallResponse response) {

    }

    @Override
    public void getAllCategories(OnNetworkCallResponse response) {
        mealDTO.getAllCategories().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe((categories) -> {
            response.onGetCategorySuccess(categories);
        }, (throwable) -> {
            response.onGetMealByCharacterFailure(throwable.getMessage());
        });
    }

    @Override
    public void getAllCountries(OnNetworkCallResponse response) {

    }

    @Override
    public void getMealsByMainIngredient(String ingredient, OnNetworkCallResponse response) {

    }

    @Override
    public void getMealsByCategory(String category, OnNetworkCallResponse response) {

    }

    @Override
    public void getMealsByCountry(String country, OnNetworkCallResponse response) {

    }
}
