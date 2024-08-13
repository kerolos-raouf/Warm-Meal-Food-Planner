package com.example.warmmeal.loginways.view;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.warmmeal.R;
import com.example.warmmeal.login.view.Login;
import com.example.warmmeal.signup.view.SignUp;

public class LoginWays extends AppCompatActivity{

    ImageButton gmail,facebook;
    Button signUp;
    TextView login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_ways);
        initViews();
        setUp();
    }

    void initViews()
    {
        gmail = findViewById(R.id.loginWaysGmail);
        signUp = findViewById(R.id.loginWaysSignUp);
        login = findViewById(R.id.loginWaysLogin);
    }

    void setUp()
    {
        signUp.setOnClickListener((e)->
        {
            Intent intent = new Intent(this, SignUp.class);
            startActivity(intent);
        });

        login.setOnClickListener((e)->{
            Intent intent = new Intent(this, Login.class);
            startActivity(intent);
        });
    }

}