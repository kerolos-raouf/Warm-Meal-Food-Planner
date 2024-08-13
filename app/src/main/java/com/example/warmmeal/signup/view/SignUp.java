package com.example.warmmeal.signup.view;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.warmmeal.R;

public class SignUp extends AppCompatActivity implements OnCreatingAccountResponse{



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

    }

    @Override
    public void onCreatingAccountSuccess() {

    }

    @Override
    public void onCreatingAccountFail(String msg) {

    }
}