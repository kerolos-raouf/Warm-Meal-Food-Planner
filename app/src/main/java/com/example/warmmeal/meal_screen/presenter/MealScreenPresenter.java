package com.example.warmmeal.meal_screen.presenter;

import com.example.warmmeal.fragment_favourite.view.OnAddToFavouriteResponse;
import com.example.warmmeal.fragment_favourite.view.OnDeleteFromFavouriteResponse;
import com.example.warmmeal.meal_screen.view.IMealScreen;
import com.example.warmmeal.model.firebase.FirebaseHandler;
import com.example.warmmeal.model.internet_connection.ConnectivityObserver;
import com.example.warmmeal.model.pojo.PlanMeal;
import com.example.warmmeal.model.pojo.FavouriteMeal;
import com.example.warmmeal.model.repository.Repository;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MealScreenPresenter {

    private final Repository repository;

    private static MealScreenPresenter mealScreenPresenter;



    private final CompositeDisposable compositeDisposable;

    private final ConnectivityObserver connectivityObserver;

    private IMealScreen iMealScreen;

    private  MealScreenPresenter(Repository repository, IMealScreen response, ConnectivityObserver connectivityObserver) {
        this.repository = repository;
        compositeDisposable = new CompositeDisposable();
        this.connectivityObserver = connectivityObserver;
    }

    public static MealScreenPresenter getInstance(Repository repository, IMealScreen iMealScreen, ConnectivityObserver connectivityObserver) {
        if(mealScreenPresenter == null) {
            mealScreenPresenter = new MealScreenPresenter(repository, iMealScreen, connectivityObserver);
        }
        mealScreenPresenter.iMealScreen = iMealScreen;
        return mealScreenPresenter;
    }

    public void getMealById(String mealId, IMealScreen response) {
        compositeDisposable.add(repository.getMealById(mealId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        response::onGetMealByIdSuccess,
                        throwable -> response.onFailure(throwable.getMessage()
                        )
                ));
    }

    public void addFavouriteMeal(FavouriteMeal favouriteMeal, OnAddToFavouriteResponse response) {

        if(FirebaseHandler.CURRENT_USER_ID != null)
        {
            compositeDisposable.add(repository.insertFavouriteMeal(favouriteMeal)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            response::onAddToFavouriteSuccess,
                            throwable -> response.onAddToFavouriteFailure(throwable.getMessage())
                    ));
        }else {
            response.onAddToFavouriteFailure("You are not logged in");
        }
    }

    public void deleteFromFavourite(FavouriteMeal favouriteMeal, OnDeleteFromFavouriteResponse response) {
        if(FirebaseHandler.CURRENT_USER_ID != null)
        {
            compositeDisposable.add(repository.deleteFavouriteMeal(favouriteMeal)
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

    public void addMealToPlan(PlanMeal planMeal)
    {
        compositeDisposable.add(repository.insertCalenderMeal(planMeal)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        () -> {iMealScreen.onAddMealToPlanSuccess(planMeal.day.toString());},
                        throwable -> {iMealScreen.onFailure(throwable.getMessage());}
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
                                    iMealScreen.onNetworkCallResponse(ConnectivityObserver.Status.Available, "Connected");
                                    break;
                                case Unavailable:
                                    ConnectivityObserver.InternetStatus = ConnectivityObserver.Status.Unavailable;
                                    iMealScreen.onNetworkCallResponse(ConnectivityObserver.Status.Unavailable, "Disconnected");
                                    break;
                                case Losing:
                                    ConnectivityObserver.InternetStatus = ConnectivityObserver.Status.Losing;
                                    iMealScreen.onNetworkCallResponse(ConnectivityObserver.Status.Losing, "Losing Connection");
                                    break;
                                case Lost:
                                    ConnectivityObserver.InternetStatus = ConnectivityObserver.Status.Lost;
                                    iMealScreen.onNetworkCallResponse(ConnectivityObserver.Status.Lost, "Connection Lost");
                                    break;
                            }
                        },
                        throwable -> {
                            iMealScreen.onFail(throwable.getMessage());
                        }
                )
        );
    }

    public void clearDisposable()
    {
        compositeDisposable.clear();
    }



}
