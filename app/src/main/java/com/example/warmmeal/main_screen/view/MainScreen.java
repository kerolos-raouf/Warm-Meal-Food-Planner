package com.example.warmmeal.main_screen.view;

import android.os.Bundle;

import com.example.warmmeal.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

public class MainScreen extends AppCompatActivity {

    BottomNavigationView navView;
    Toolbar toolbar;
    NavController navController;

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
        navView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.mainToolBar);
        setSupportActionBar(toolbar);
    }

    void setUp()
    {

    }


    @Override
    public boolean onSupportNavigateUp() {
        return navController.navigateUp() || super.onSupportNavigateUp();
    }



}