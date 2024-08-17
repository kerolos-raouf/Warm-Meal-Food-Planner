package com.example.warmmeal.category_country_screen.presenter;

import com.example.warmmeal.fragment_search.view.OnSearchResponse;
import com.example.warmmeal.model.repository.Repository;

public class CategoryAndCountryPresenter {

    private Repository repository;
    private static CategoryAndCountryPresenter presenter;
    private CategoryAndCountryPresenter(Repository repository) {
        this.repository = repository;
    }
    public static CategoryAndCountryPresenter getInstance(Repository repository) {
        if (presenter == null) {
            presenter = new CategoryAndCountryPresenter(repository);
        }
        return presenter;
    }


    public void getMealsByCategory(String category, OnSearchResponse onSearchResponse) {
        repository.getMealsByCategory(category, onSearchResponse);
    }

    public void getMealsByCountry(String country, OnSearchResponse onSearchResponse) {
        repository.getMealsByCountry(country, onSearchResponse);
    }


}
