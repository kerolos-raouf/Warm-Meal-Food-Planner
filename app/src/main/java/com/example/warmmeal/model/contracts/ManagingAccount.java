package com.example.warmmeal.model.contracts;

import com.example.warmmeal.fragment_profile.view.OnDownloadDataResponse;
import com.example.warmmeal.fragment_profile.view.OnLogOutResponse;
import com.example.warmmeal.fragment_profile.view.OnPackUpDataResponse;
import com.example.warmmeal.login.view.OnLoginResponse;
import com.example.warmmeal.login_ways.view.OnLoginWithGmailResponse;
import com.example.warmmeal.model.pojo.FavouriteMeal;
import com.example.warmmeal.model.pojo.PlanMeal;
import com.example.warmmeal.signup.view.OnCreatingAccountResponse;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public interface ManagingAccount {

    void signUpWithUserNameAndPassword(String userName, String password, OnCreatingAccountResponse response);
    void loginWithUserNameAndPassword(String userName, String password, OnLoginResponse response);
    void signInUsingGmailAccount(String idToken, OnLoginWithGmailResponse response);
    FirebaseUser getCurrentUser();
    void signOutUser(OnLogOutResponse response);
    void packUpData(ArrayList<FavouriteMeal> favouriteMeals, ArrayList<PlanMeal> planMeals, OnPackUpDataResponse response);
    void downloadData(OnDownloadDataResponse response);

}
