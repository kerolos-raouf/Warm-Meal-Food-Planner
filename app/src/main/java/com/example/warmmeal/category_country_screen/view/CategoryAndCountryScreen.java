package com.example.warmmeal.category_country_screen.view;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.warmmeal.R;
import com.example.warmmeal.category_country_screen.presenter.CategoryAndCountryPresenter;
import com.example.warmmeal.fragment_home.view.HomeFragment;
import com.example.warmmeal.fragment_search.view.OnRecyclerViewItemClickedListener;
import com.example.warmmeal.fragment_search.view.OnSearchResponse;
import com.example.warmmeal.fragment_search.view.SearchRecyclerViewAdapter;
import com.example.warmmeal.meal_screen.view.MealActivity;
import com.example.warmmeal.model.database.DatabaseHandler;
import com.example.warmmeal.model.firebase.FirebaseHandler;
import com.example.warmmeal.model.internet_connection.ConnectivityObserver;
import com.example.warmmeal.model.network.NetworkAPI;
import com.example.warmmeal.model.pojo.Meal;
import com.example.warmmeal.model.pojo.Meals;
import com.example.warmmeal.model.repository.RepositoryImpl;
import com.example.warmmeal.model.shared_pref.SharedPrefHandler;
import com.example.warmmeal.model.util.CustomProgressBar;
import com.example.warmmeal.model.util.Navigator;

import java.util.ArrayList;

public class CategoryAndCountryScreen extends AppCompatActivity implements OnRecyclerViewItemClickedListener, OnSearchResponse {

    RecyclerView recyclerView;
    TextView categoryAndCountryScreenText;
    Toolbar toolbar;

    //presenter
    CategoryAndCountryPresenter presenter;
    SearchRecyclerViewAdapter mAdapter;
    ConnectivityObserver connectivityObserver;
    CustomProgressBar customProgressBar;

    String currentText;
    String currentType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_and_country_screen);
        init();
        setUp();
    }

    void init()
    {

        connectivityObserver = new ConnectivityObserver(this);
        customProgressBar = new CustomProgressBar(this);

        currentText = getIntent().getStringExtra(HomeFragment.CATEGORY_COUNTRY_KEY);
        currentType = getIntent().getStringExtra(HomeFragment.CATEGORY_COUNTRY_TYPE);

        recyclerView = findViewById(R.id.categoryAndCountryRecyclerView);
        categoryAndCountryScreenText = findViewById(R.id.categoryAndCountryScreenText);
        toolbar = findViewById(R.id.categoryAndCountryScreenToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(v -> finish());

        presenter = CategoryAndCountryPresenter.getInstance(RepositoryImpl.getInstance(FirebaseHandler.getInstance(), NetworkAPI.getInstance(), DatabaseHandler.getInstance(this), SharedPrefHandler.getInstance(this)),connectivityObserver);

        if(currentType.equals(Type.CATEGORY.toString()))
        {
            presenter.getMealsByCategory(currentText,this);
        }else {
            presenter.getMealsByCountry(currentText,this);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.checkInternetStatus();
    }

    void setUp()
    {
        categoryAndCountryScreenText.setText(currentText + " : ");

        LinearLayoutManager layoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);

        mAdapter = new SearchRecyclerViewAdapter(new ArrayList<>(), this, this,false);
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onMealClicked(Meal meal) {
        if (ConnectivityObserver.InternetStatus == ConnectivityObserver.Status.Available) {
            customProgressBar.startProgressBar();
            Navigator.navigateWithExtra(this, MealActivity.class, HomeFragment.MEAL_KEY,meal.getIdMeal(),HomeFragment.IS_FAVOURITE_KEY,meal.isFavourite());
        }else
        {
            Toast.makeText(this, "Check your internet connection", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onButtonClicked(Meal meal) {

    }

    @Override
    public void onSuccess(Meals meals) {
        Log.d("Kerolos", "onSuccess: " + meals.getMeals().size());
        mAdapter.setMeals((ArrayList<Meal>) meals.getMeals());
    }

    @Override
    public void onFailure(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onPause() {
        super.onPause();
        customProgressBar.dismissProgressBar();
    }
}