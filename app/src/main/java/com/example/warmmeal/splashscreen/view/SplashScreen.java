package com.example.warmmeal.splashscreen.view;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.warmmeal.R;
import com.example.warmmeal.loginways.view.LoginWays;
import com.example.warmmeal.splashscreen.presenter.SplashScreenPresenter;

import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class SplashScreen extends AppCompatActivity implements ISplashScreen {


    SplashScreenPresenter presenter;
    public static final String TAG= "Kerolos";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        presenter = SplashScreenPresenter.getInstance(this);
    }


    @Override
    protected void onResume() {
        super.onResume();

        presenter.timer(3);
    }

    @Override
    public void onTimerFinished() {
        Intent intent = new Intent(this, LoginWays.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}