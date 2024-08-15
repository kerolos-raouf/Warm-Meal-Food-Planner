package com.example.warmmeal.model.repository;

import com.example.warmmeal.fragment_search.view.OnNetworkCallResponse;
import com.example.warmmeal.login.view.OnLoginResponse;
import com.example.warmmeal.login_ways.view.OnLoginWithGmailResponse;
import com.example.warmmeal.model.contracts.LocalDataSource;
import com.example.warmmeal.model.contracts.ManagingAccountState;
import com.example.warmmeal.model.contracts.RemoteDataSource;
import com.example.warmmeal.signup.view.OnCreatingAccountResponse;
import com.example.warmmeal.model.contracts.ManagingAccount;

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
    public void createNewUser(String userName, String password, OnCreatingAccountResponse response) {
        managingAccount.signUpWithUserNameAndPassword(userName,password,response);
    }

    @Override
    public void loginUser(String userName, String password, OnLoginResponse response) {
        managingAccount.loginWithUserNameAndPassword(userName,password,response);
    }

    @Override
    public void loginWithGmail(String idToken, OnLoginWithGmailResponse response) {
        managingAccount.signInUsingGmailAccount(idToken,response);
    }

    @Override
    public void getMealsByFirstLetter(char letter, OnNetworkCallResponse response) {
        remoteDataSource.getMealsByFirstLetter(letter,response);
    }

    @Override
    public void getRandomMeal(OnNetworkCallResponse response) {
        remoteDataSource.getRandomMeal(response);
    }

    @Override
    public void getMealByName(String name, OnNetworkCallResponse response) {

    }

    @Override
    public void getAllCategories(OnNetworkCallResponse response) {
        remoteDataSource.getAllCategories(response);
    }

    @Override
    public void getAllCountries(OnNetworkCallResponse response) {

    }

    @Override
    public void getMealsByMainIngredient(String ingredient, OnNetworkCallResponse response) {

    }

    @Override
    public void getMealsByCategory(String category, OnNetworkCallResponse response) {

    }

    @Override
    public void getMealsByCountry(String country, OnNetworkCallResponse response) {

    }
}
