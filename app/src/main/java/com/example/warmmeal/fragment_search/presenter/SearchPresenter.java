package com.example.warmmeal.fragment_search.presenter;

import com.example.warmmeal.fragment_search.view.ListPurpose;
import com.example.warmmeal.fragment_search.view.OnGetListsResponse;
import com.example.warmmeal.fragment_search.view.OnSearchResponse;
import com.example.warmmeal.model.repository.Repository;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class SearchPresenter {

    private Repository repository;
    private static SearchPresenter presenter;

    private final CompositeDisposable compositeDisposable;
    private SearchPresenter(Repository repository) {
        this.repository = repository;
        compositeDisposable = new CompositeDisposable();
    }

    public static SearchPresenter getInstance(Repository repository) {
        if (presenter == null) {
            presenter = new SearchPresenter(repository);
        }
        return presenter;
    }


    public void getMealByName(String name, OnSearchResponse response) {
        compositeDisposable.add(repository.getMealByName(name)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        response::onSuccess,
                        throwable -> response.onFailure(throwable.getMessage()
                        )
                ));
    }

    public void getMealByCategory(String category, OnSearchResponse response) {
        compositeDisposable.add(repository.getMealsByCategory(category)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        response::onSuccess,
                        throwable -> response.onFailure(throwable.getMessage()
                        )
                ));
    }

    public void getMealByCountry(String country, OnSearchResponse response) {
        compositeDisposable.add(repository.getMealsByCountry(country)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        response::onSuccess,
                        throwable -> response.onFailure(throwable.getMessage()
                        )
                ));
    }

    public void getMealByMainIngredient(String ingredient, OnSearchResponse response) {
        compositeDisposable.add(repository.getMealsByMainIngredient(ingredient)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        response::onSuccess,
                        throwable -> response.onFailure(throwable.getMessage()
                        )
                ));
    }

    public void getIngredients(OnGetListsResponse response, ListPurpose purpose) {
        compositeDisposable.add(repository.getIngredients()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        response::onGetIngredientsSuccess,
                        throwable -> response.onFailure(throwable.getMessage()
                        )
                ));
    }


    public void clearDisposable()
    {
        compositeDisposable.clear();
    }

}
