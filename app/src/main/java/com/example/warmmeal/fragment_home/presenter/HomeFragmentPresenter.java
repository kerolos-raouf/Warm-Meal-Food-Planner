package com.example.warmmeal.fragment_home.presenter;

import com.example.warmmeal.fragment_search.view.OnNetworkCallResponse;
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

}
