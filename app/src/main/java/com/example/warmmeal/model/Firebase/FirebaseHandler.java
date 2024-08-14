package com.example.warmmeal.model.Firebase;

import android.app.Activity;
import android.content.Context;

import com.example.warmmeal.login.view.OnLoginResponse;
import com.example.warmmeal.signup.view.OnCreatingAccountResponse;
import com.example.warmmeal.model.contracts.ManagingAccount;
import com.google.android.gms.auth.api.Auth;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class FirebaseHandler implements ManagingAccount {


    private final FirebaseAuth mAuth;
    Context context;

    private static FirebaseHandler firebaseHandler;

    private FirebaseHandler(Context context)
    {
        mAuth = FirebaseAuth.getInstance();
        this.context = context;
    }


    public static FirebaseHandler getInstance(Context context)
    {
        if(firebaseHandler == null)
        {
            firebaseHandler = new FirebaseHandler(context);
        }

        return firebaseHandler;
    }


    @Override
    public void signUpWithUserNameAndPassword(String userName, String password, OnCreatingAccountResponse response) {
        mAuth.createUserWithEmailAndPassword(userName, password)
                .addOnCompleteListener((Activity) context,task -> {
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
                .addOnCompleteListener((Activity) context,task -> {
                    if (task.isSuccessful()) {
                        response.onLoginSuccess();
                    } else {
                        response.onLoginFail(Objects.requireNonNull(task.getException()).toString());
                    }
                });
    }

    @Override
    public void signUsingGmailAccount() {

    }
}
