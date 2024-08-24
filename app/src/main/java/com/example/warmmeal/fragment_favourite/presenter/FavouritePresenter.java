package com.example.warmmeal.fragment_favourite.presenter;

import com.example.warmmeal.fragment_favourite.view.OnDeleteFromFavouriteResponse;
import com.example.warmmeal.fragment_favourite.view.OnGetFavouriteMealResponse;
import com.example.warmmeal.model.firebase.FirebaseHandler;
import com.example.warmmeal.model.pojo.FavouriteMeal;
import com.example.warmmeal.model.repository.Repository;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class FavouritePresenter {


    private Repository repository;

    static FavouritePresenter presenter;

    private final CompositeDisposable compositeDisposable;

    private FavouritePresenter(Repository repository)
    {
        this.repository = repository;
        compositeDisposable = new CompositeDisposable();
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
        if(FirebaseHandler.CURRENT_USER_ID != null)
        {
            compositeDisposable.add(repository.getAllFavouriteMeals(userId)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            response::onGetFavouriteMealSuccess,
                            throwable -> response.onGetFavouriteMealFailure(throwable.getMessage())
                    ));
        }else {
            response.onGetFavouriteMealFailure("You are in the guest mode, Please login first.");
        }
    }

    public void deleteFavouriteMeal(FavouriteMeal meal, OnDeleteFromFavouriteResponse response)
    {
        if(FirebaseHandler.CURRENT_USER_ID != null)
        {
            compositeDisposable.add(repository.deleteFavouriteMeal(meal)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            response::onDeleteFromFavouriteSuccess,
                            throwable -> response.onDeleteFromFavouriteFailure(throwable.getMessage())
                    ));
        }else {
            response.onDeleteFromFavouriteFailure("You are not logged in");
        }
    }

    public void clearDisposable()
    {
        compositeDisposable.clear();
    }

    /*<T> Observable<T> applyObservable(Observable<T> observable) {
        return observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }*/

}
