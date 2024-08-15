package com.example.warmmeal.login.view;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.warmmeal.R;
import com.example.warmmeal.login.presenter.LoginPresenter;
import com.example.warmmeal.model.database.DatabaseHandler;
import com.example.warmmeal.model.firebase.FirebaseHandler;
import com.example.warmmeal.model.repository.RepositoryImpl;
import com.example.warmmeal.model.network.NetworkAPI;
import com.example.warmmeal.model.shared_pref.SharedPrefHandler;
import com.example.warmmeal.model.util.CustomProgressBar;
import com.example.warmmeal.model.util.Navigator;
import com.example.warmmeal.main_screen.view.MainScreen;

public class Login extends AppCompatActivity implements OnLoginResponse{


    Button login,back;
    EditText email,password;

    LoginPresenter presenter;

    CustomProgressBar customProgressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();
        setUp();
    }

    void init()
    {
        login = findViewById(R.id.loginBtn);
        back = findViewById(R.id.loginBackBtn);
        email = findViewById(R.id.loginEmailEditText);
        password = findViewById(R.id.loginPasswordEditText);

        customProgressBar = new CustomProgressBar(this);

        presenter = LoginPresenter.getInstance(RepositoryImpl.getInstance(FirebaseHandler.getInstance(), NetworkAPI.getInstance(), DatabaseHandler.getInstance(this), SharedPrefHandler.getInstance()));
    }

    void setUp()
    {

        login.setOnClickListener((e)->{
            if(email.getText().toString().trim().isEmpty())
            {
                email.setError("This field can not be empty.");
            }else if (password.getText().toString().trim().isEmpty())
            {
                password.setError("This field can not be empty.");
            }else
            {
                customProgressBar.startProgressBar();
                presenter.login(email.getText().toString(),password.getText().toString(),this);
            }
        });

        back.setOnClickListener((e)->{
            finish();
        });

    }



    @Override
    public void onLoginSuccess() {
        customProgressBar.dismissProgressBar();
        Navigator.navigate(this, MainScreen.class);
    }

    @Override
    public void onLoginFail(String msg) {
        customProgressBar.dismissProgressBar();
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}