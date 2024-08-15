package com.example.warmmeal.signup.view;

import android.os.Bundle;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.warmmeal.R;
import com.example.warmmeal.model.database.DatabaseHandler;
import com.example.warmmeal.model.firebase.FirebaseHandler;
import com.example.warmmeal.model.repository.Repository;
import com.example.warmmeal.model.repository.RepositoryImpl;
import com.example.warmmeal.model.contracts.ManagingAccount;
import com.example.warmmeal.model.network.NetworkAPI;
import com.example.warmmeal.model.shared_pref.SharedPrefHandler;
import com.example.warmmeal.model.util.CustomProgressBar;
import com.example.warmmeal.signup.presenter.SignUpPresenter;

public class SignUp extends AppCompatActivity implements OnCreatingAccountResponse,ISignUpView{


    Button signUp,back;
    EditText email,password,conPassword;


    SignUpPresenter presenter;
    Repository repository;
    ManagingAccount managingAccount;

    CustomProgressBar customProgressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        init();
        setUp();

    }

    void init()
    {
        signUp = findViewById(R.id.signUpBtn);
        back = findViewById(R.id.signUpBackBtn);
        email = findViewById(R.id.emailEditText);
        password = findViewById(R.id.passwordEditText);
        conPassword = findViewById(R.id.confirmPasswordEditText);

        customProgressBar = new CustomProgressBar(this);

        presenter = SignUpPresenter.getInstance(RepositoryImpl.getInstance(FirebaseHandler.getInstance(), NetworkAPI.getInstance(), DatabaseHandler.getInstance(this), SharedPrefHandler.getInstance()),this);
    }

    void setUp()
    {
        signUp.setOnClickListener((e)->{
            String emailText = email.getText().toString().trim();
            String passText = password.getText().toString().trim();
            String conPassText = conPassword.getText().toString().trim();

            if(!isThatValidEmail(emailText) || emailText.isEmpty())
            {
                email.setError("Illegal email format.");
            }
            else if(passText.length() < 6){
                password.setError("The password length must be greater than 5 characters.");
            }
            else if(conPassText.isEmpty() || !passText.equals(conPassText)){
                conPassword.setError("Password and confirm password must be the same.");
            }
            else {
                customProgressBar.startProgressBar();
                presenter.createNewAccount(email.getText().toString(),password.getText().toString(),this);
            }
        });

        back.setOnClickListener((e)->{
            finish();
        });
    }

    boolean isThatValidEmail(String emailString)
    {
        return Patterns.EMAIL_ADDRESS.matcher(emailString).matches();
    }

    @Override
    public void onCreatingAccountSuccess() {

        Toast.makeText(this, "Account was Created Successfully.", Toast.LENGTH_SHORT).show();
        customProgressBar.dismissProgressBar();
    }

    @Override
    public void onCreatingAccountFail(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        customProgressBar.dismissProgressBar();
    }
}