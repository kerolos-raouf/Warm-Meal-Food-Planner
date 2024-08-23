package com.example.warmmeal.model.repository;

import com.example.warmmeal.fragment_profile.view.OnDownloadDataResponse;
import com.example.warmmeal.fragment_profile.view.OnLogOutResponse;
import com.example.warmmeal.fragment_profile.view.OnbBackupDataResponse;
import com.example.warmmeal.login.view.OnLoginResponse;
import com.example.warmmeal.login_ways.view.OnLoginWithGmailResponse;
import com.example.warmmeal.login_ways.view.OnSetUserRegisterSateResponse;
import com.example.warmmeal.model.contracts.LocalDataSource;
import com.example.warmmeal.model.contracts.ManagingAccountState;
import com.example.warmmeal.model.contracts.RemoteDataSource;
import com.example.warmmeal.model.pojo.PlanMeal;
import com.example.warmmeal.model.pojo.Categories;
import com.example.warmmeal.model.pojo.FavouriteMeal;
import com.example.warmmeal.model.pojo.Ingredients;
import com.example.warmmeal.model.pojo.Meals;
import com.example.warmmeal.signup.view.OnCreatingAccountResponse;
import com.example.warmmeal.model.contracts.ManagingAccount;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;

public class RepositoryImpl implements Repository{


    ManagingAccount managingAccount;
    RemoteDataSource remoteDataSource;
    LocalDataSource localDataSource;
    ManagingAccountState managingAccountState;

    private static RepositoryImpl repository;

    private RepositoryImpl(ManagingAccount managingAccount, RemoteDataSource remoteDataSource, LocalDataSource localDataSource, ManagingAccountState managingAccountState)
    {
        this.managingAccount = managingAccount;
        this.remoteDataSource = remoteDataSource;
        this.localDataSource = localDataSource;
        this.managingAccountState = managingAccountState;
    }


    public static RepositoryImpl getInstance(ManagingAccount managingAccount, RemoteDataSource remoteDataSource, LocalDataSource localDataSource, ManagingAccountState managingAccountState)
    {
        if(repository == null)
        {
            repository = new RepositoryImpl(managingAccount,remoteDataSource,localDataSource,managingAccountState);
        }
        return repository;
    }

    @Override
    public void signUpWithUserNameAndPassword(String userName, String password, OnCreatingAccountResponse response) {
        managingAccount.signUpWithUserNameAndPassword(userName,password,response);
    }

    @Override
    public void loginWithUserNameAndPassword(String userName, String password, OnLoginResponse response) {
        managingAccount.loginWithUserNameAndPassword(userName,password,response);
    }

    @Override
    public void signInUsingGmailAccount(String idToken, OnLoginWithGmailResponse response) {
        managingAccount.signInUsingGmailAccount(idToken,response);
    }

    @Override
    public FirebaseUser getCurrentUser() {
        return managingAccount.getCurrentUser();
    }

    @Override
    public void signOutUser(OnLogOutResponse response) {
        managingAccount.signOutUser(response);
    }

    @Override
    public void backupData(ArrayList<FavouriteMeal> favouriteMeals, ArrayList<PlanMeal> planMeals, OnbBackupDataResponse response) {
        managingAccount.backupData(favouriteMeals,planMeals,response);
    }

    @Override
    public void downloadData(OnDownloadDataResponse response) {
        managingAccount.downloadData(response);
    }

    @Override
    public Flowable<Meals> getMealsByFirstLetter(char letter) {
        return remoteDataSource.getMealsByFirstLetter(letter);
    }

    @Override
    public Flowable<Meals> getRandomMeal() {
        return remoteDataSource.getRandomMeal();
    }

    @Override
    public Flowable<Meals> getMealByName(String name) {
        return remoteDataSource.getMealByName(name);
    }

    @Override
    public Flowable<Categories> getAllCategories() {
         return remoteDataSource.getAllCategories();
    }

    @Override
    public Flowable<Meals> getAllCountries() {
        return remoteDataSource.getAllCountries();
    }

    @Override
    public Flowable<Meals> getMealsByMainIngredient(String ingredient) {
        return remoteDataSource.getMealsByMainIngredient(ingredient);
    }

    @Override
    public Flowable<Meals> getMealsByCategory(String category ) {
        return remoteDataSource.getMealsByCategory(category);
    }

    @Override
    public Flowable<Meals> getMealsByCountry(String country ) {
        return remoteDataSource.getMealsByCountry(country);
    }

    @Override
    public Flowable<Ingredients> getIngredients() {
        return remoteDataSource.getIngredients();
    }

    @Override
    public Flowable<Meals> getMealById(String id) {
        return remoteDataSource.getMealById(id);
    }

    @Override
    public void setUserRegisterState(boolean loggedIn, OnSetUserRegisterSateResponse response) {
        managingAccountState.setUserRegisterState(loggedIn,response);
    }

    @Override
    public boolean isUserLoggedIn() {
        return managingAccountState.isUserLoggedIn();
    }

    @Override
    public Completable insertFavouriteMeal(FavouriteMeal meal) {
        return localDataSource.insertFavouriteMeal(meal);
    }

    @Override
    public Flowable<List<FavouriteMeal>> getAllFavouriteMeals(String userId) {
        return localDataSource.getAllFavouriteMeals(userId);
    }

    @Override
    public Completable deleteFavouriteMeal(FavouriteMeal meal) {
        return localDataSource.deleteFavouriteMeal(meal);
    }

    @Override
    public Flowable<Integer> isFavouriteMealExists(String userId, String mealId) {
        return localDataSource.isFavouriteMealExists(userId,mealId);
    }

    @Override
    public Completable insertCalenderMeal(PlanMeal meal) {
        return localDataSource.insertCalenderMeal(meal);
    }

    @Override
    public Flowable<List<PlanMeal>> getAllCalenderMeals(String userId) {
        return localDataSource.getAllCalenderMeals(userId);
    }

    @Override
    public Completable deleteCalenderMeal(PlanMeal meal) {
        return localDataSource.deleteCalenderMeal(meal);
    }
}
