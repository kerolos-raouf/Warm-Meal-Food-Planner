package com.example.warmmeal.fragment_favourite.presenter;

import com.example.warmmeal.fragment_favourite.view.OnDeleteFromFavouriteResponse;
import com.example.warmmeal.fragment_favourite.view.OnGetFavouriteMealResponse;
import com.example.warmmeal.model.pojo.FavouriteMeal;
import com.example.warmmeal.model.repository.Repository;

public class FavouritePresenter {


    private Repository repository;

    static FavouritePresenter presenter;

    private FavouritePresenter(Repository repository)
    {
        this.repository = repository;
    }

    public static FavouritePresenter getInstance(Repository repository)
    {
        if(presenter == null)
        {
            presenter = new FavouritePresenter(repository);
        }
        return presenter;
    }

    public void getAllFavouriteMeals(String userId, OnGetFavouriteMealResponse response)
    {
        repository.getAllFavouriteMeals(userId,response);
    }

    public void deleteFavouriteMeal(FavouriteMeal meal, OnDeleteFromFavouriteResponse response)
    {
        repository.deleteFavouriteMeal(meal,response);
    }

}
