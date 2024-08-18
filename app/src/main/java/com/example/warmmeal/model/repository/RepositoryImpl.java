package com.example.warmmeal.model.repository;

import com.example.warmmeal.fragment_calender.view.OnAddCalendarMealResponse;
import com.example.warmmeal.fragment_calender.view.OnDeleteCalendarMealResponse;
import com.example.warmmeal.fragment_calender.view.OnGetCalendarMealsResponse;
import com.example.warmmeal.fragment_favourite.view.OnAddToFavouriteResponse;
import com.example.warmmeal.fragment_favourite.view.OnDeleteFromFavouriteResponse;
import com.example.warmmeal.fragment_favourite.view.OnGetFavouriteMealResponse;
import com.example.warmmeal.fragment_home.view.DataPurpose;
import com.example.warmmeal.fragment_profile.view.OnLogOutResponse;
import com.example.warmmeal.fragment_home.view.contracts.OnNetworkCallResponse;
import com.example.warmmeal.fragment_search.view.ListPurpose;
import com.example.warmmeal.fragment_search.view.OnGetListsResponse;
import com.example.warmmeal.fragment_search.view.OnSearchResponse;
import com.example.warmmeal.login.view.OnLoginResponse;
import com.example.warmmeal.login_ways.view.OnLoginWithGmailResponse;
import com.example.warmmeal.login_ways.view.OnSetUserRegisterSateResponse;
import com.example.warmmeal.meal_screen.view.OnMealScreenResponse;
import com.example.warmmeal.model.contracts.LocalDataSource;
import com.example.warmmeal.model.contracts.ManagingAccountState;
import com.example.warmmeal.model.contracts.RemoteDataSource;
import com.example.warmmeal.model.pojo.CalenderMeal;
import com.example.warmmeal.model.pojo.FavouriteMeal;
import com.example.warmmeal.signup.view.OnCreatingAccountResponse;
import com.example.warmmeal.model.contracts.ManagingAccount;
import com.google.firebase.auth.FirebaseUser;

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
    public void getMealsByFirstLetter(char letter, DataPurpose dataPurpose, OnNetworkCallResponse response) {
        remoteDataSource.getMealsByFirstLetter(letter, dataPurpose,response);
    }

    @Override
    public void getRandomMeal(OnNetworkCallResponse response) {
        remoteDataSource.getRandomMeal(response);
    }

    @Override
    public void getMealByName(String name, OnSearchResponse response) {
        remoteDataSource.getMealByName(name, response);
    }

    @Override
    public void getAllCategories(OnNetworkCallResponse response) {
        remoteDataSource.getAllCategories(response);
    }

    @Override
    public void getAllCountries(OnNetworkCallResponse response) {
        remoteDataSource.getAllCountries(response);
    }

    @Override
    public void getMealsByMainIngredient(String ingredient, OnSearchResponse response) {
        remoteDataSource.getMealsByMainIngredient(ingredient, response);
    }

    @Override
    public void getMealsByCategory(String category, OnSearchResponse response) {
        remoteDataSource.getMealsByCategory(category, response);
    }

    @Override
    public void getMealsByCountry(String country, OnSearchResponse response) {
        remoteDataSource.getMealsByCountry(country, response);
    }

    @Override
    public void getIngredients(OnGetListsResponse response, ListPurpose purpose) {
        remoteDataSource.getIngredients(response, purpose);
    }

    @Override
    public void getMealById(String id, OnMealScreenResponse response) {
        remoteDataSource.getMealById(id, response);
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
    public void insertFavouriteMeal(FavouriteMeal meal, OnAddToFavouriteResponse response) {
        localDataSource.insertFavouriteMeal(meal,response);
    }

    @Override
    public void getAllFavouriteMeals(String userId, OnGetFavouriteMealResponse response) {
        localDataSource.getAllFavouriteMeals(userId,response);
    }

    @Override
    public void deleteFavouriteMeal(FavouriteMeal meal, OnDeleteFromFavouriteResponse response) {
        localDataSource.deleteFavouriteMeal(meal,response);
    }

    @Override
    public void insertCalenderMeal(CalenderMeal meal, OnAddCalendarMealResponse response) {
        localDataSource.insertCalenderMeal(meal,response);
    }

    @Override
    public void getAllCalenderMeals(String userId, OnGetCalendarMealsResponse response) {
        localDataSource.getAllCalenderMeals(userId,response);
    }

    @Override
    public void deleteCalenderMeal(CalenderMeal meal, OnDeleteCalendarMealResponse response) {
        localDataSource.deleteCalenderMeal(meal,response);
    }
}
