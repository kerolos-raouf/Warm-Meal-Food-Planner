package com.example.warmmeal.model.contracts;

import com.example.warmmeal.fragment_home.view.DataPurpose;
import com.example.warmmeal.fragment_home.view.OnNetworkCallResponse;
import com.example.warmmeal.fragment_search.view.ListPurpose;
import com.example.warmmeal.fragment_search.view.OnGetListsResponse;
import com.example.warmmeal.fragment_search.view.OnSearchResponse;

public interface RemoteDataSource {

    void getMealsByFirstLetter(char letter, DataPurpose dataPurpose, OnNetworkCallResponse response);
    void getRandomMeal(OnNetworkCallResponse response);
    void getAllCategories(OnNetworkCallResponse response);
    void getAllCountries(OnNetworkCallResponse response);
    void getMealByName(String name, OnSearchResponse response);
    void getMealsByMainIngredient(String ingredient, OnSearchResponse response);
    void getMealsByCategory(String category, OnSearchResponse response);
    void getMealsByCountry(String country, OnSearchResponse response);
    void getIngredients(OnGetListsResponse response, ListPurpose purpose);
}
