package com.example.warmmeal.login_ways.view;

import android.content.Intent;
import android.credentials.GetCredentialRequest;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.warmmeal.R;
import com.example.warmmeal.login.view.Login;
import com.example.warmmeal.main_screen.view.MainScreen;
import com.example.warmmeal.model.util.ISkipAlertDialog;
import com.example.warmmeal.model.util.Navigator;
import com.example.warmmeal.model.util.SkipAlertDialog;
import com.example.warmmeal.signup.view.SignUp;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.identity.googleid.GetGoogleIdOption;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;

public class LoginWays extends AppCompatActivity{

    ImageButton gmail;
    Button signUp,skip;
    TextView login;

    GoogleSignInClient mGoogleSignInClient;
    private final int RC_SIGN = 20;

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
        skip = findViewById(R.id.loginWaysSkip);
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

        signInUsingGoogle();
    }

    void showDialog()
    {
        SkipAlertDialog alertDialog = new SkipAlertDialog(this);

        alertDialog.startAlertDialog(new ISkipAlertDialog() {
            @Override
            public void onPositiveButtonClick() {
                Navigator.navigate(LoginWays.this, MainScreen.class);
            }

            @Override
            public void onNegativeButtonClick() {

            }
        });
    }

    void signInUsingGoogle()
    {
        GoogleSignInOptions signInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.gcm_defaultSenderId))
                .requestEmail().build();

        mGoogleSignInClient = GoogleSignIn.getClient(this,signInOptions);

        gmail.setOnClickListener((e)->{
            Intent intent = mGoogleSignInClient.getSignInIntent();
            startActivityForResult(intent,RC_SIGN);

        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == RC_SIGN)
        {
            Task<GoogleSignInAccount> accountTask = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = accountTask.getResult(ApiException.class);
                fireBaseAuth(account.getIdToken());
            }catch (ApiException e)
            {
                Toast.makeText(this, "Error "+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    void fireBaseAuth(String token)
    {
        AuthCredential credential = GoogleAuthProvider.getCredential(token,null);
        FirebaseAuth.getInstance().signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful())
                {
                    Toast.makeText(LoginWays.this, "Logging in done.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}