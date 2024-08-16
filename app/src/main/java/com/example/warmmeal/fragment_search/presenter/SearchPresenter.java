package com.example.warmmeal.fragment_search.presenter;

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


}
