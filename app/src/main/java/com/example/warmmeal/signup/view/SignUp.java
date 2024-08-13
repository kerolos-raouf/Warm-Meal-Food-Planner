package com.example.warmmeal.signup.view;

import android.os.Bundle;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.warmmeal.R;
import com.example.warmmeal.model.Firebase.FirebaseHandler;
import com.example.warmmeal.model.Repository.Repository;
import com.example.warmmeal.model.Repository.RepositoryImpl;
import com.example.warmmeal.model.contracts.ManagingAccount;
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

        managingAccount = FirebaseHandler.getInstance(this);
        repository = RepositoryImpl.getInstance(managingAccount);
        presenter = SignUpPresenter.getInstance(repository,this);
    }

    void setUp()
    {
        signUp.setOnClickListener((e)->{
            String emailText = email.getText().toString().trim();
            String passText = password.getText().toString().trim();
            String conPassText = conPassword.getText().toString().trim();

            if(!isThatValidEmail(emailText) || emailText.isEmpty())
            {
                Toast.makeText(this, "Illegal email format.", Toast.LENGTH_SHORT).show();
            }
            else if(passText.length() < 6){
                Toast.makeText(this, "Password length must be greater than or equal 6.", Toast.LENGTH_SHORT).show();
            }
            else if(conPassText.isEmpty() || !passText.equals(conPassText)){
                Toast.makeText(this, "Password and confirm password must be the same.", Toast.LENGTH_SHORT).show();
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