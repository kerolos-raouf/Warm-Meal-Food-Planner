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
import com.example.warmmeal.model.util.Day;
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
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Handler;

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



    private static final String MEAL_ID = "mealId";
    private static final String MEAL_NAME = "mealName";
    private static final String MEAL_IMAGE = "mealImage";
    private static final String USER_ID = "userId";
    private static final String DAY = "day";


    @Override
    public void packUpData(ArrayList<FavouriteMeal> favouriteMeals, ArrayList<PlanMeal> planMeals, OnPackUpDataResponse response)
    {
        for(FavouriteMeal meal : favouriteMeals)
        {
            Map<String, Object> singleMealMap = new HashMap<>();
            singleMealMap.put(MEAL_ID, meal.idMeal);
            singleMealMap.put(MEAL_NAME, meal.strMeal);
            singleMealMap.put(MEAL_IMAGE, meal.strMealThumb);
            singleMealMap.put(USER_ID, meal.userId);

            firestore.collection(USER_KEY).document(CURRENT_USER_ID)
                    .collection(FAVOURITE_MEALS_KEY)
                    .document(meal.idMeal)
                    .set(singleMealMap).addOnCompleteListener(new OnCompleteListener<Void>() {
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
        for(PlanMeal meal : planMeals)
        {
            Map<String, Object> singleMealMap = new HashMap<>();
            singleMealMap.put(MEAL_ID, meal.mealId);
            singleMealMap.put(MEAL_NAME, meal.mealName);
            singleMealMap.put(MEAL_IMAGE, meal.mealImage);
            singleMealMap.put(DAY, meal.day);
            singleMealMap.put(USER_ID, meal.userId);

            firestore.collection(USER_KEY).document(CURRENT_USER_ID)
                    .collection(PLAN_MEALS_KEY)
                    .document(meal.day + meal.mealId)
                    .set(singleMealMap).addOnCompleteListener(new OnCompleteListener<Void>() {
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


    }

    @Override
    public void downloadData(OnDownloadDataResponse response)
    {
        getFavouriteMeals(response);
        getPlanMeals(response);
    }

        private void getFavouriteMeals(OnDownloadDataResponse response)
        {
            firestore.collection(USER_KEY).document(CURRENT_USER_ID)
                    .collection(FAVOURITE_MEALS_KEY).
                    get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                ArrayList<FavouriteMeal> favouriteMeals = new ArrayList<>();
                                for (DocumentSnapshot document : Objects.requireNonNull(task.getResult())) {

                                    FavouriteMeal favouriteMeal = new FavouriteMeal(
                                            Objects.requireNonNull(document.getString(USER_ID)),
                                            Objects.requireNonNull(document.getString(MEAL_ID)),
                                            Objects.requireNonNull(document.getString(MEAL_NAME)),
                                            Objects.requireNonNull(document.getString(MEAL_IMAGE)),

                                            true);
                                    favouriteMeals.add(favouriteMeal);
                                }
                                response.onDownloadFavouritesSuccess(favouriteMeals);
                            }else
                            {
                                response.onDownloadDataFail(Objects.requireNonNull(task.getException()).toString());
                            }

                        }

                    });
        }


        private void getPlanMeals(OnDownloadDataResponse response)
        {
            firestore.collection(USER_KEY).document(CURRENT_USER_ID)
                    .collection(PLAN_MEALS_KEY)
                    .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                ArrayList<PlanMeal> planMeals = new ArrayList<>();
                                for (DocumentSnapshot document : Objects.requireNonNull(task.getResult())) {

                                    PlanMeal planMeal = new PlanMeal(
                                            Objects.requireNonNull(document.getString(USER_ID)),
                                            Day.valueOf(Objects.requireNonNull(document.getString(DAY))),
                                            Objects.requireNonNull(document.getString(MEAL_ID)),
                                            Objects.requireNonNull(document.getString(MEAL_NAME)),
                                            Objects.requireNonNull(document.getString(MEAL_IMAGE))
                                    );
                                    planMeals.add(planMeal);
                                }
                                response.onDownloadPlanMealsSuccess(planMeals);
                            }
                            else
                            {
                                response.onDownloadDataFail(Objects.requireNonNull(task.getException()).toString());
                            }
                        }
                    });
        }
}
