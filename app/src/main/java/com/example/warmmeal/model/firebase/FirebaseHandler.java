package com.example.warmmeal.model.firebase;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.warmmeal.fragment_profile.view.OnDownloadDataResponse;
import com.example.warmmeal.fragment_profile.view.OnLogOutResponse;
import com.example.warmmeal.fragment_profile.view.OnPackUpDataResponse;
import com.example.warmmeal.login.view.OnLoginResponse;
import com.example.warmmeal.login_ways.view.OnLoginWithGmailResponse;
import com.example.warmmeal.model.pojo.FavouriteMeal;
import com.example.warmmeal.model.pojo.PlanMeal;
import com.example.warmmeal.signup.view.OnCreatingAccountResponse;
import com.example.warmmeal.model.contracts.ManagingAccount;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;

public class FirebaseHandler implements ManagingAccount {

    public static final String USER_KEY = "Users";
    private static final String FAVOURITE_MEALS_KEY = "favouriteMeals";
    private static final String PLAN_MEALS_KEY = "planMeals";


    public static String CURRENT_USER_ID;
    private final FirebaseAuth mAuth;
    private final FirebaseFirestore firestore;

    private static FirebaseHandler firebaseHandler;

    private FirebaseHandler()
    {
        CURRENT_USER_ID = null;
        mAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
    }


    public static FirebaseHandler getInstance()
    {
        if(firebaseHandler == null)
        {
            firebaseHandler = new FirebaseHandler();
        }

        return firebaseHandler;
    }


    @Override
    public void signUpWithUserNameAndPassword(String userName, String password, OnCreatingAccountResponse response) {
        mAuth.createUserWithEmailAndPassword(userName, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        response.onCreatingAccountSuccess();
                    } else {
                        response.onCreatingAccountFail(Objects.requireNonNull(task.getException()).toString());
                    }
                });
    }

    @Override
    public void loginWithUserNameAndPassword(String userName, String password, OnLoginResponse response) {
        mAuth.signInWithEmailAndPassword(userName, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        response.onLoginSuccess();
                    } else {
                        response.onLoginFail(Objects.requireNonNull(task.getException()).toString());
                    }
                });
    }

    @Override
    public void signInUsingGmailAccount(String idToken, OnLoginWithGmailResponse response) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken,null);
        FirebaseAuth.getInstance().signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful())
                {
                    response.onLoginWithGmailSuccess();
                }
                else
                {
                    response.onLoginWithGmailFail(Objects.requireNonNull(task.getException()).toString());
                }
            }
        });
    }

    @Override
    public FirebaseUser getCurrentUser() {
        return mAuth.getCurrentUser();
    }

    @Override
    public void signOutUser(OnLogOutResponse response) {
        mAuth.signOut();
        response.onLogOutSuccess();
    }



    @Override
    public void packUpData(ArrayList<FavouriteMeal> favouriteMeals, ArrayList<PlanMeal> planMeals, OnPackUpDataResponse response)
    {
        Map<String, Object> allMealsMap = Map.of(FAVOURITE_MEALS_KEY, favouriteMeals, PLAN_MEALS_KEY, planMeals);
        firestore.collection(USER_KEY).document(CURRENT_USER_ID)
                .set(allMealsMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    response.onPackUpDataSuccess();
                }else
                {
                    response.onPackUpDataFail(Objects.requireNonNull(task.getException()).toString());
                }
            }
        });

    }

    @Override
    public void downloadData(OnDownloadDataResponse response) {
        firestore.collection(USER_KEY).document(CURRENT_USER_ID)
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                Map<String, Object> data = document.getData();
                                ArrayList<FavouriteMeal> favouriteMeals = (ArrayList<FavouriteMeal>) data.get(FAVOURITE_MEALS_KEY);
                                ArrayList<PlanMeal> planMeals = (ArrayList<PlanMeal>) data.get(PLAN_MEALS_KEY);
                                response.onDownloadDataSuccess(favouriteMeals, planMeals);
                            }else
                                {
                                response.onDownloadDataFail("There is no data.");
                            }
                        }else
                        {
                            response.onDownloadDataFail(Objects.requireNonNull(task.getException()).toString());
                        }
                    }
                });
    }
}
