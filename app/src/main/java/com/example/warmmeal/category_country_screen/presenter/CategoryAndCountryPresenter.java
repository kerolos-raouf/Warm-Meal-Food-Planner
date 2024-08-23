package com.example.warmmeal.category_country_screen.presenter;

import com.example.warmmeal.fragment_search.view.OnSearchResponse;
import com.example.warmmeal.model.repository.Repository;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class CategoryAndCountryPresenter {

    private Repository repository;
    private static CategoryAndCountryPresenter presenter;
    private final CompositeDisposable compositeDisposable;
    private CategoryAndCountryPresenter(Repository repository) {
        this.repository = repository;
        compositeDisposable = new CompositeDisposable();
    }

    public static CategoryAndCountryPresenter getInstance(Repository repository) {
        if (presenter == null) {
            presenter = new CategoryAndCountryPresenter(repository);
        }
        return presenter;
    }


    public void getMealsByCategory(String category, OnSearchResponse response) {
        compositeDisposable.add(repository.getMealsByCategory(category)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        response::onSuccess,
                        throwable -> response.onFailure(throwable.getMessage()
                        )
                ));
    }

    public void getMealsByCountry(String country, OnSearchResponse response) {
        compositeDisposable.add(repository.getMealsByCountry(country)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        response::onSuccess,
                        throwable -> response.onFailure(throwable.getMessage()
                        )
                ));
    }


}
