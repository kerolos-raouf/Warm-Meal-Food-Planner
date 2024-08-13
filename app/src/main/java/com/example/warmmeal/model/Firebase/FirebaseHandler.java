package com.example.warmmeal.model.Firebase;

import android.app.Activity;
import android.content.Context;

import com.example.warmmeal.signup.view.OnCreatingAccountResponse;
import com.example.warmmeal.model.contracts.ManagingAccount;
import com.google.firebase.auth.FirebaseAuth;

public class FirebaseHandler implements ManagingAccount {


    private FirebaseAuth mAuth;
    Context context;

    public static FirebaseHandler firebaseHandler;

    private FirebaseHandler(Context context)
    {
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
                .addOnCompleteListener((Activity) context, task -> {
                    if (task.isSuccessful()) {
                        response.onCreatingAccountSuccess();
                        // Sign in success, update UI with the signed-in user's information
                        /*Log.d(TAG, "createUserWithEmail:success");
                        FirebaseUser user = mAuth.getCurrentUser();
                        updateUI(user);*/
                    } else {
                        response.onCreatingAccountFail(task.getResult().toString());
                        // If sign in fails, display a message to the user.
                        /*Log.w(TAG, "createUserWithEmail:failure", task.getException());
                        Toast.makeText(EmailPasswordActivity.this, "Authentication failed.",
                                Toast.LENGTH_SHORT).show();
                        updateUI(null);*/
                    }
                });
    }

    @Override
    public void LoginWithUserNameAndPassword(String userName, String password, OnCreatingAccountResponse response) {

    }
}
