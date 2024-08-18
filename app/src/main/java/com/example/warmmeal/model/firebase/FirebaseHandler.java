package com.example.warmmeal.model.firebase;

import android.app.Activity;
import android.content.Context;

import androidx.annotation.NonNull;

import com.example.warmmeal.fragment_profile.view.OnLogOutResponse;
import com.example.warmmeal.login.view.OnLoginResponse;
import com.example.warmmeal.login_ways.view.OnLoginWithGmailResponse;
import com.example.warmmeal.signup.view.OnCreatingAccountResponse;
import com.example.warmmeal.model.contracts.ManagingAccount;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import java.util.Objects;

public class FirebaseHandler implements ManagingAccount {


    public static String CURRENT_USER_ID;
    private final FirebaseAuth mAuth;


    private static FirebaseHandler firebaseHandler;

    private FirebaseHandler()
    {
        mAuth = FirebaseAuth.getInstance();
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
}
