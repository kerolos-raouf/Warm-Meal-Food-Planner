package com.example.warmmeal.fragment_home.presenter;

import com.example.warmmeal.fragment_favourite.view.OnAddToFavouriteResponse;
import com.example.warmmeal.fragment_favourite.view.OnDeleteFromFavouriteResponse;
import com.example.warmmeal.fragment_favourite.view.OnGetFavouriteMealResponse;
import com.example.warmmeal.fragment_home.view.DataPurpose;
import com.example.warmmeal.fragment_home.view.contracts.OnNetworkCallResponse;
import com.example.warmmeal.model.pojo.FavouriteMeal;
import com.example.warmmeal.model.repository.Repository;

public class HomeFragmentPresenter {

    Repository repository;

    private static HomeFragmentPresenter presenter;

    private HomeFragmentPresenter(Repository repository)
    {
        this.repository = repository;
    }

    public static HomeFragmentPresenter getInstance(Repository repository)
    {
        if(presenter == null)
        {
            presenter = new HomeFragmentPresenter(repository);
        }
        return presenter;
    }

    public void getRandomMeal(OnNetworkCallResponse response)
    {
        repository.getRandomMeal(response);
    }

    public void getMealsByFirstLetter(char letter, DataPurpose dataPurpose, OnNetworkCallResponse response)
    {
        repository.getMealsByFirstLetter(letter,dataPurpose,response);
    }

    public  void getAllCategories(OnNetworkCallResponse response)
    {
        repository.getAllCategories(response);
    }

    public void getAllCountries(OnNetworkCallResponse response)
    {
        repository.getAllCountries(response);
    }

    public void addFavouriteMeal(FavouriteMeal meal, OnAddToFavouriteResponse response)
    {
        repository.insertFavouriteMeal(meal,response);
    }

    public void deleteFromFavourite(FavouriteMeal meal, OnDeleteFromFavouriteResponse response)
    {
        repository.deleteFavouriteMeal(meal,response);
    }

    public void getAllFavouriteMeals(String userId, OnGetFavouriteMealResponse response)
    {
        repository.getAllFavouriteMeals(userId,response);
    }

}
