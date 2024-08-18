package com.example.warmmeal.login_ways.view;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.warmmeal.R;
import com.example.warmmeal.login.view.Login;
import com.example.warmmeal.login_ways.presenter.LoginWaysPresenter;
import com.example.warmmeal.main_screen.view.MainScreen;
import com.example.warmmeal.model.database.DatabaseHandler;
import com.example.warmmeal.model.firebase.FirebaseHandler;
import com.example.warmmeal.model.repository.RepositoryImpl;
import com.example.warmmeal.model.network.NetworkAPI;
import com.example.warmmeal.model.shared_pref.SharedPrefHandler;
import com.example.warmmeal.model.util.CustomProgressBar;
import com.example.warmmeal.model.util.ISkipAlertDialog;
import com.example.warmmeal.model.util.Navigator;
import com.example.warmmeal.model.util.SkipAlertDialog;
import com.example.warmmeal.signup.view.SignUp;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

public class LoginWays extends AppCompatActivity implements OnLoginWithGmailResponse, OnSetUserRegisterSateResponse {

    ImageButton gmail;
    Button signUp,skip;
    TextView login;

    //google auth
    GoogleSignInClient mGoogleSignInClient;
    private final int RC_SIGN = 20;

    private LoginWaysPresenter presenter;

    CustomProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_ways);
        initViews();
        setUp();
    }

    void initViews()
    {
        progressBar = new CustomProgressBar(LoginWays.this);
        gmail = findViewById(R.id.loginWaysGmail);
        signUp = findViewById(R.id.loginWaysSignUp);
        login = findViewById(R.id.loginWaysLogin);
        skip = findViewById(R.id.loginWaysSkip);
        presenter = LoginWaysPresenter.getInstance(RepositoryImpl.getInstance(FirebaseHandler.getInstance(), NetworkAPI.getInstance(), DatabaseHandler.getInstance(this), SharedPrefHandler.getInstance(this)));;
    }

    void setUp()
    {
        signUp.setOnClickListener((e)->
        {
            Navigator.navigate(this, SignUp.class);
        });

        login.setOnClickListener((e)->{
            Navigator.navigate(this, Login.class);
        });

        skip.setOnClickListener((e)->{
            showDialog();
        });

        //google auth
        gmail.setOnClickListener((e)->{
            progressBar.startProgressBar();
            signInUsingGoogle();
        });

    }

    void showDialog()
    {
        SkipAlertDialog alertDialog = new SkipAlertDialog(this);

        alertDialog.startAlertDialog(new ISkipAlertDialog() {
            @Override
            public void onPositiveButtonClick() {
                presenter.setUserRegisterState(true,LoginWays.this);
            }

            @Override
            public void onNegativeButtonClick() {

            }
        });
    }

    //google auth
    void signInUsingGoogle()
    {
        GoogleSignInOptions signInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail().build();


        mGoogleSignInClient = GoogleSignIn.getClient(this,signInOptions);
        mGoogleSignInClient.signOut();

        Intent intent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(intent,RC_SIGN);
    }

    //google auth
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == RC_SIGN)
        {
            Task<GoogleSignInAccount> accountTask = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = accountTask.getResult(ApiException.class);
                presenter.loginWithGmail(account.getIdToken(),this);
            }catch (ApiException e)
            {
                progressBar.dismissProgressBar();
                Toast.makeText(this, "Error "+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }



    @Override
    public void onLoginWithGmailSuccess() {
        presenter.setUserRegisterState(true, this);
        progressBar.dismissProgressBar();
    }

    @Override
    public void onLoginWithGmailFail(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        progressBar.dismissProgressBar();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(presenter.isUserLoggedIn())
        {
            Navigator.navigateAndClearLast(this, MainScreen.class);
        }
    }

    @Override
    public void setUserRegisterState() {
        Navigator.navigate(this, MainScreen.class);
    }
}