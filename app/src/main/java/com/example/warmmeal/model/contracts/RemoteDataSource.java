package com.example.warmmeal.model.contracts;

import com.example.warmmeal.fragment_search.view.OnNetworkCallResponse;

public interface RemoteDataSource {

    void getMealsByFirstLetter(char letter, OnNetworkCallResponse response);
    void getRandomMeal(OnNetworkCallResponse response);
    void getMealByName(String name, OnNetworkCallResponse response);
    void getAllCategories(OnNetworkCallResponse response);
    void getAllCountries(OnNetworkCallResponse response);
    void getMealsByMainIngredient(String ingredient, OnNetworkCallResponse response);
    void getMealsByCategory(String category, OnNetworkCallResponse response);
    void getMealsByCountry(String country, OnNetworkCallResponse response);
}
