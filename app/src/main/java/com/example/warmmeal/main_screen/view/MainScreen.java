package com.example.warmmeal.main_screen.view;

import android.os.Bundle;
import android.widget.Toast;

import com.example.warmmeal.R;
import com.example.warmmeal.main_screen.presenter.MainScreenPresenter;
import com.example.warmmeal.model.internet_connection.ConnectivityObserver;
import com.example.warmmeal.model.network.NetworkAPI;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MainScreen extends AppCompatActivity implements IMainScreen {


    BottomNavigationView navView;
    Toolbar toolbar;
    NavController navController;
    ConnectivityObserver connectivityObserver;


    MainScreenPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);
        init();
        setUp();


        /*AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_search, R.id.navigation_profile)
                .build();*/
        navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main_screen);
        NavigationUI.setupActionBarWithNavController(this, navController);
        NavigationUI.setupWithNavController(navView, navController);

    }

    void init()
    {
        connectivityObserver = new ConnectivityObserver(this);
        presenter = new MainScreenPresenter(connectivityObserver, this);
        navView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.mainToolBar);
        setSupportActionBar(toolbar);
    }

    void setUp()
    {

    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.checkInternetStatus();
    }

    @Override
    public boolean onSupportNavigateUp() {
        return navController.navigateUp() || super.onSupportNavigateUp();
    }

    @Override
    protected void onStop() {
        super.onStop();
        presenter.clearDisposable();
    }

    @Override
    public void onNetworkCallResponse(ConnectivityObserver.Status status, String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onFail(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}