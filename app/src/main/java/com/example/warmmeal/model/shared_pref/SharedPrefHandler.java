package com.example.warmmeal.model.shared_pref;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.warmmeal.login_ways.view.OnSetUserRegisterSateResponse;
import com.example.warmmeal.model.contracts.ManagingAccountState;

public class SharedPrefHandler implements ManagingAccountState {

    public static final String SHARED_PREF_NAME = "WarmMeal";
    public static final String SHARED_PREF_IS_USER_REGISTERED = "isUserRegistered";
    private final SharedPreferences sharedPreferences;
    private static SharedPrefHandler sharedPrefHandler;
    private SharedPrefHandler(Context context) {
        sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
    }

    public static SharedPrefHandler getInstance(Context context) {
        if (sharedPrefHandler == null) {
            sharedPrefHandler = new SharedPrefHandler(context);
        }
        return sharedPrefHandler;
    }


    @Override
    public void setUserRegisterState(boolean loggedIn, OnSetUserRegisterSateResponse response) {
        sharedPreferences.edit().putBoolean(SHARED_PREF_IS_USER_REGISTERED, loggedIn).apply();
        response.setUserRegisterState();
    }

    @Override
    public boolean isUserLoggedIn() {
        return sharedPreferences.getBoolean(SHARED_PREF_IS_USER_REGISTERED, false);
    }
}
