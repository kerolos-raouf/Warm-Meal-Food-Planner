package com.example.warmmeal.model.network;

import android.util.Log;

import com.example.warmmeal.fragment_home.view.DataPurpose;
import com.example.warmmeal.fragment_home.view.OnNetworkCallResponse;
import com.example.warmmeal.fragment_search.view.ListPurpose;
import com.example.warmmeal.fragment_search.view.OnGetListsResponse;
import com.example.warmmeal.fragment_search.view.OnSearchResponse;
import com.example.warmmeal.meal_screen.view.OnMealScreenResponse;
import com.example.warmmeal.model.contracts.RemoteDataSource;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkAPI implements RemoteDataSource {

    private static final String BASE_URL = "https://www.themealdb.com/api/json/v1/1/";

    CompositeDisposable disposable;


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
    public void getMealsByFirstLetter(char letter, DataPurpose dataPurpose, OnNetworkCallResponse response) {
        disposable.add(mealDTO.getMealsByFirstLetter(letter).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe((meals) -> {
            if(dataPurpose == DataPurpose.INSPIRATION)
            {
                response.onGetMealByCharacterForMoreYouLikeSuccess(meals);
            }
            else
            {
                response.onGetMealByCharacterForInspirationSuccess(meals);
            }
        }, (throwable) -> {
            response.onFailure("get Meal By First Letter : " + dataPurpose.toString() +throwable.getMessage());
        }));
    }

    @Override
    public void getRandomMeal(OnNetworkCallResponse response) {
        disposable.add(mealDTO.getRandomMeal().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe((meals) -> {
            response.onGetMealByCharacterForMoreYouLikeSuccess(meals);
        }, (throwable) -> {
            response.onFailure("get Random Meal: "+throwable.getMessage());
        }));
    }


    @Override
    public void getAllCategories(OnNetworkCallResponse response) {
        disposable.add(mealDTO.getAllCategories().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe((categories) -> {
            response.onGetCategorySuccess(categories);
        }, (throwable) -> {
            response.onFailure("get Meal all Categories: "+throwable.getMessage());
        }));
    }

    @Override
    public void getAllCountries(OnNetworkCallResponse response) {
        disposable.add(mealDTO.getAllCountries().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe((meals) -> {
            response.onGetAllCountriesSuccess(meals);
        }, (throwable) -> {
            response.onFailure("get Meal all Countries: "+throwable.getMessage());
        }));
    }

    @Override
    public void getMealByName(String name, OnSearchResponse response) {
        disposable.add(mealDTO.getMealByName(name).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe((meals) -> {
            response.onSuccess(meals);
        }, (throwable) -> {
            response.onFailure(throwable.getMessage());
        }));
    }


    @Override
    public void getMealsByMainIngredient(String ingredient, OnSearchResponse response) {
        disposable.add(mealDTO.getMealByMainIngredient(ingredient).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe((meals) -> {
            response.onSuccess(meals);
        }, (throwable) -> {
            response.onFailure(throwable.getMessage());
        }));
    }

    @Override
    public void getMealsByCategory(String category, OnSearchResponse response) {
        disposable.add(mealDTO.getMealByCategory(category).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe((meals) -> {
            response.onSuccess(meals);
        }, (throwable) -> {
            response.onFailure(throwable.getMessage());
        }));
    }

    @Override
    public void getMealsByCountry(String country, OnSearchResponse response) {
        disposable.add(mealDTO.getMealByCountry(country).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe((meals) -> {
            response.onSuccess(meals);
        }, (throwable) -> {
            response.onFailure(throwable.getMessage());
        }));
    }

    @Override
    public void getIngredients(OnGetListsResponse response, ListPurpose purpose) {
        disposable.add(mealDTO.getIngredients().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe((ingredients) -> {
            if(purpose == ListPurpose.INGREDIENTS)
            {
                response.onGetIngredientsSuccess(ingredients);
            }
        }, (throwable) -> {
            response.onFailure(throwable.getMessage());
        }));
    }

    @Override
    public void getMealById(String id, OnMealScreenResponse response) {
        disposable.add(mealDTO.getMealById(id).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(response::onGetMealByIdSuccess, (throwable) -> {
            response.onFailure(throwable.getMessage());
        }));
    }

    public void clearDisposable()
    {
        disposable.clear();
    }
}
