package com.example.warmmeal.fragment_search.presenter;

import com.example.warmmeal.fragment_search.view.ListPurpose;
import com.example.warmmeal.fragment_search.view.OnGetListsResponse;
import com.example.warmmeal.fragment_search.view.OnSearchResponse;
import com.example.warmmeal.model.repository.Repository;

public class SearchPresenter {

    private Repository repository;
    private static SearchPresenter presenter;
    private SearchPresenter(Repository repository) {
        this.repository = repository;
    }

    public static SearchPresenter getInstance(Repository repository) {
        if (presenter == null) {
            presenter = new SearchPresenter(repository);
        }
        return presenter;
    }


    public void getMealByName(String name, OnSearchResponse response) {
        repository.getMealByName(name, response);
    }

    public void getMealByCategory(String category, OnSearchResponse response) {
        repository.getMealsByCategory(category, response);
    }

    public void getMealByCountry(String country, OnSearchResponse response) {
        repository.getMealsByCountry(country, response);
    }

    public void getMealByMainIngredient(String ingredient, OnSearchResponse response) {
        repository.getMealsByMainIngredient(ingredient, response);
    }

    public void getIngredients(OnGetListsResponse response, ListPurpose purpose) {
        repository.getIngredients(response, purpose);
    }

}
