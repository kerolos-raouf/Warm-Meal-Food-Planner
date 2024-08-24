package com.example.warmmeal.fragment_home.presenter;

import android.util.Log;

import com.example.warmmeal.fragment_favourite.view.OnAddToFavouriteResponse;
import com.example.warmmeal.fragment_favourite.view.OnDeleteFromFavouriteResponse;
import com.example.warmmeal.fragment_favourite.view.OnGetFavouriteMealResponse;
import com.example.warmmeal.fragment_home.view.DataPurpose;
import com.example.warmmeal.fragment_home.view.contracts.IHomeFragment;
import com.example.warmmeal.fragment_home.view.contracts.OnNetworkCallResponse;
import com.example.warmmeal.model.firebase.FirebaseHandler;
import com.example.warmmeal.model.pojo.FavouriteMeal;
import com.example.warmmeal.model.pojo.Meal;
import com.example.warmmeal.model.repository.Repository;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class HomeFragmentPresenter {

    Repository repository;

    private static HomeFragmentPresenter presenter;

    private final CompositeDisposable compositeDisposable;
    private IHomeFragment iHomeFragment;

    private HomeFragmentPresenter(Repository repository)
    {
        this.repository = repository;
        compositeDisposable = new CompositeDisposable();
    }

    public static HomeFragmentPresenter getInstance(Repository repository,IHomeFragment iHomeFragment)
    {
        if(presenter == null)
        {
            presenter = new HomeFragmentPresenter(repository);
        }
        presenter.iHomeFragment = iHomeFragment;
        return presenter;
    }

    public void getRandomMeal()
    {
        compositeDisposable.add(repository.getRandomMeal()
                .subscribeOn(Schedulers.io())
                .repeat(5)
                .timeout(10000, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        iHomeFragment::onGetRandomMealSuccess,
                        throwable -> iHomeFragment.onFailure(throwable.getMessage()),
                        ()->iHomeFragment.onGetRandomMealComplete()
                ));
    }

    public void getMealsByFirstLetter(char letter, DataPurpose dataPurpose, OnNetworkCallResponse response)
    {
        compositeDisposable.add(repository.getMealsByFirstLetter(letter)
                .subscribeOn(Schedulers.io())
                        .map(meals->{
                            for(Meal meal : meals.getMeals())
                            {
                                compositeDisposable.add(repository.isFavouriteMealExists(FirebaseHandler.CURRENT_USER_ID, meal.getIdMeal())
                                        .subscribeOn(Schedulers.io())
                                        .subscribe(favourite -> meal.setFavourite(favourite!=0)));
                            }
                            return meals;
                        })
                .timeout(15000, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(

                        (meals -> {
                            if(dataPurpose == DataPurpose.MORE_YOU_LIKE)
                            {
                                response.onGetMealByCharacterForMoreYouLikeSuccess(meals);
                            }else
                            {
                                response.onGetMealByCharacterForInspirationSuccess(meals);
                            }
                        }),
                        throwable -> response.onFailure(throwable.getMessage()
                        )
                ));
    }

    public  void getAllCategories(OnNetworkCallResponse response)
    {
        compositeDisposable.add(repository.getAllCategories()
                .subscribeOn(Schedulers.io())
                .timeout(10000, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        response::onGetCategorySuccess,
                        throwable -> response.onFailure(throwable.getMessage()
                        )
                ));
    }

    public void getAllCountries(OnNetworkCallResponse response)
    {
        compositeDisposable.add(repository.getAllCountries()
                .subscribeOn(Schedulers.io())
                .timeout(10000, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        response::onGetAllCountriesSuccess,
                        throwable -> response.onFailure(throwable.getMessage()
                        )
                ));
    }

    public void addFavouriteMeal(FavouriteMeal favouriteMeal, OnAddToFavouriteResponse response)
    {
        if(FirebaseHandler.CURRENT_USER_ID != null)
        {
            compositeDisposable.add(repository.insertFavouriteMeal(favouriteMeal)
                    .subscribeOn(Schedulers.io())
                    .timeout(10000, TimeUnit.MILLISECONDS)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            response::onAddToFavouriteSuccess,
                            throwable -> response.onAddToFavouriteFailure(throwable.getMessage())
                    ));
        }else {
            response.onAddToFavouriteFailure("You are not logged in");
        }
    }

    public void deleteFromFavourite(FavouriteMeal meal, OnDeleteFromFavouriteResponse response)
    {
        if(FirebaseHandler.CURRENT_USER_ID != null)
        {
            compositeDisposable.add(repository.deleteFavouriteMeal(meal)
                    .subscribeOn(Schedulers.io())
                    .timeout(10000, TimeUnit.MILLISECONDS)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            response::onDeleteFromFavouriteSuccess,
                            throwable -> response.onDeleteFromFavouriteFailure(throwable.getMessage())
                    ));
        }else {
            response.onDeleteFromFavouriteFailure("You are not logged in");
        }
    }

    public void getAllFavouriteMeals(String userId, OnGetFavouriteMealResponse response)
    {
        if(FirebaseHandler.CURRENT_USER_ID != null)
        {
            compositeDisposable.add(repository.getAllFavouriteMeals(userId)
                    .subscribeOn(Schedulers.io())
                    .timeout(10000, TimeUnit.MILLISECONDS)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            response::onGetFavouriteMealSuccess,
                            throwable -> response.onGetFavouriteMealFailure(throwable.getMessage())
                    ));
        }else {
            response.onGetFavouriteMealFailure("You are not logged in");
        }
    }

    public void clearDisposable()
    {
        compositeDisposable.clear();
    }

}
