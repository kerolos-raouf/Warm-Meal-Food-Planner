package com.example.warmmeal.fragment_profile.presenter;

import com.example.warmmeal.fragment_profile.view.IProfileFragment;
import com.example.warmmeal.fragment_profile.view.OnDownloadDataResponse;
import com.example.warmmeal.fragment_profile.view.OnLogOutResponse;
import com.example.warmmeal.fragment_profile.view.OnbBackupDataResponse;
import com.example.warmmeal.login_ways.view.OnSetUserRegisterSateResponse;
import com.example.warmmeal.model.firebase.FirebaseHandler;
import com.example.warmmeal.model.pojo.FavouriteMeal;
import com.example.warmmeal.model.pojo.PlanMeal;
import com.example.warmmeal.model.repository.Repository;

import java.util.ArrayList;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class ProfilePresenter {

    private final Repository repository;

    private static ProfilePresenter presenter;

    private IProfileFragment iProfileFragment;

    private CompositeDisposable compositeDisposable;

    public ProfilePresenter(Repository repository) {
        this.repository = repository;
        compositeDisposable = new CompositeDisposable();
    }

    public static ProfilePresenter getInstance(Repository repository , IProfileFragment iProfileFragment) {
        if (presenter == null) {
            presenter = new ProfilePresenter(repository);
        }
        presenter.iProfileFragment = iProfileFragment;
        return presenter;
    }

    public void logOut(OnLogOutResponse response) {
        repository.signOutUser(response);
    }


    public void setUserRegisterState(boolean loggedIn, OnSetUserRegisterSateResponse response) {
        repository.setUserRegisterState(loggedIn, response);
    }

    public void getFavouriteMeals() {
        if (FirebaseHandler.CURRENT_USER_ID != null)
        {
            compositeDisposable.add(repository.getAllFavouriteMeals(FirebaseHandler.CURRENT_USER_ID)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            favouriteMeals -> iProfileFragment.onGetFavouriteMealsSuccess((ArrayList<FavouriteMeal>) favouriteMeals),
                            throwable -> iProfileFragment.onFail(throwable.getMessage())
                    )
            );
        }
    }

    public void getPlanMeals() {
        if (FirebaseHandler.CURRENT_USER_ID != null)
        {
            compositeDisposable.add(repository.getAllCalenderMeals(FirebaseHandler.CURRENT_USER_ID)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            planMeals -> iProfileFragment.onGetPlanMealsSuccess((ArrayList<PlanMeal>) planMeals),
                            throwable -> iProfileFragment.onFail(throwable.getMessage())
                    )
            );
        }else
        {
            iProfileFragment.onFail("User is not logged in.");
        }
    }

    public void packUpData(ArrayList<FavouriteMeal> favouriteMeals, ArrayList<PlanMeal> planMeals, OnbBackupDataResponse response) {
        if(FirebaseHandler.CURRENT_USER_ID != null) {
            repository.backupData(favouriteMeals, planMeals, response);
        }else
        {
            response.onPackUpDataFail("User is not logged in.");
        }
    }

    public void downloadData(OnDownloadDataResponse response) {
        if(FirebaseHandler.CURRENT_USER_ID != null) {
            repository.downloadData(response);
        }else
        {
            response.onDownloadDataFail("User is not logged in.");
        }
    }

    public void insertFavouriteMeal(FavouriteMeal meal) {
        compositeDisposable.add(repository.insertFavouriteMeal(meal)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        () -> {},
                        throwable -> iProfileFragment.onFail(throwable.getMessage())
                )
        );
    }

    public void insertPlanMeal(PlanMeal meal) {
        compositeDisposable.add(repository.insertCalenderMeal(meal)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        () -> {},
                        throwable -> iProfileFragment.onFail(throwable.getMessage())
                )
        );
    }


}
