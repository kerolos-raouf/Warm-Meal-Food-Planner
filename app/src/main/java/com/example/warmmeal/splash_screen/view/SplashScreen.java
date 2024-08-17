package com.example.warmmeal.splash_screen.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import com.example.warmmeal.R;
import com.example.warmmeal.login_ways.view.LoginWays;
import com.example.warmmeal.model.util.Navigator;
import com.example.warmmeal.splash_screen.presenter.SplashScreenPresenter;

public class SplashScreen extends AppCompatActivity implements ISplashScreen {


    SplashScreenPresenter presenter;
    public static final String TAG= "Kerolos";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        init();

    }

    void init()
    {
        Window window = getWindow();
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN  | View.SYSTEM_UI_FLAG_FULLSCREEN);
        presenter = SplashScreenPresenter.getInstance(this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        presenter.timer(4);
    }

    @Override
    public void onTimerFinished() {
        Navigator.navigateAndClearLast(this, LoginWays.class);
    }

    @Override
    protected void onStop() {
        super.onStop();
        presenter.stopTimer();
    }
}