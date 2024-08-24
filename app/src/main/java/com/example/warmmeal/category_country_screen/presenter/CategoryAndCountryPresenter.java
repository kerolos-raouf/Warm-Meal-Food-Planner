package com.example.warmmeal.category_country_screen.presenter;

import com.example.warmmeal.fragment_search.view.OnSearchResponse;
import com.example.warmmeal.model.internet_connection.ConnectivityObserver;
import com.example.warmmeal.model.repository.Repository;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class CategoryAndCountryPresenter {

    private Repository repository;
    private static CategoryAndCountryPresenter presenter;
    private final CompositeDisposable compositeDisposable;
    ConnectivityObserver connectivityObserver;

    private CategoryAndCountryPresenter(Repository repository,ConnectivityObserver connectivityObserver) {
        this.repository = repository;
        compositeDisposable = new CompositeDisposable();
        this.connectivityObserver = connectivityObserver;
    }

    public static CategoryAndCountryPresenter getInstance(Repository repository,ConnectivityObserver connectivityObserver) {
        if (presenter == null) {
            presenter = new CategoryAndCountryPresenter(repository,connectivityObserver);
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

    public void checkInternetStatus() {
        compositeDisposable.add(connectivityObserver.observeConnectivity()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        status -> {
                            switch (status)
                            {
                                case Available:
                                    ConnectivityObserver.InternetStatus = ConnectivityObserver.Status.Available;
                                    break;
                                case Unavailable:
                                    ConnectivityObserver.InternetStatus = ConnectivityObserver.Status.Unavailable;
                                    break;
                                case Losing:
                                    ConnectivityObserver.InternetStatus = ConnectivityObserver.Status.Losing;
                                    break;
                                case Lost:
                                    ConnectivityObserver.InternetStatus = ConnectivityObserver.Status.Lost;
                                    break;
                            }
                        },
                        throwable -> {
                        }
                )
        );
    }


}
