package com.example.warmmeal.meal_screen.view;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.warmmeal.R;
import com.example.warmmeal.fragment_home.view.HomeFragment;
import com.example.warmmeal.meal_screen.presenter.MealScreenPresenter;
import com.example.warmmeal.model.database.DatabaseHandler;
import com.example.warmmeal.model.firebase.FirebaseHandler;
import com.example.warmmeal.model.network.NetworkAPI;
import com.example.warmmeal.model.pojo.Meal;
import com.example.warmmeal.model.pojo.MealIngredientAndMeasure;
import com.example.warmmeal.model.pojo.Meals;
import com.example.warmmeal.model.repository.RepositoryImpl;
import com.example.warmmeal.model.shared_pref.SharedPrefHandler;

import java.util.ArrayList;

public class MealActivity extends AppCompatActivity implements OnMealScreenResponse{

    ImageView mealImage;
    TextView mealName;
    TextView mealCountry;
    TextView mealInstructions;
    RecyclerView ingredientsRecyclerView;
    VideoView mealVideoViewer;
    Button addToFavourite;


    //presenter
    MealScreenPresenter presenter;
    IngredientsRecyclerViewAdapter mAdapter;

    String mealId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal);
        init();
        setUp();

    }

    void init()
    {
        mealId = getIntent().getStringExtra(HomeFragment.ID_KEY);
        mealImage = findViewById(R.id.mealScreenImage);
        mealName = findViewById(R.id.mealScreenMealName);
        mealCountry = findViewById(R.id.mealScreenMealCountry);
        mealInstructions = findViewById(R.id.mealScreenMealInstructions);
        ingredientsRecyclerView = findViewById(R.id.mealScreenRecyclerView);
        mealVideoViewer = findViewById(R.id.mealScreenVideoViewer);
        addToFavourite = findViewById(R.id.mealScreenAddToFavourite);

        presenter = MealScreenPresenter.getInstance(RepositoryImpl.getInstance(FirebaseHandler.getInstance(), NetworkAPI.getInstance(), DatabaseHandler.getInstance(this), SharedPrefHandler.getInstance()));

        presenter.getMealById(mealId,this);
    }

    void setUp()
    {

    }


    @Override
    public void onGetMealByIdSuccess(Meals meals) {
        setMealData(meals.getMeals().get(0));
    }

    @Override
    public void onFailure(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        Log.d("Kerolos", "Meal Activity onFailure: " + message);
    }


    @Override
    public void onStop() {
        super.onStop();
        NetworkAPI.getInstance().clearDisposable();
    }


    private void setMealData(Meal meal)
    {
        Glide.with(this).load(meal.getStrMealThumb()).into(mealImage);
        mealName.setText(meal.getStrMeal());
        mealCountry.setText(meal.getStrArea());
        mealInstructions.setText("Instrcutions : " + meal.getStrInstructions());

        LinearLayoutManager layoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        ingredientsRecyclerView.setLayoutManager(layoutManager);
        mAdapter = new IngredientsRecyclerViewAdapter(getIngredients(meal.getIngredients(), meal.getMeasures()),this);
        ingredientsRecyclerView.setAdapter(mAdapter);
    }

    ArrayList<MealIngredientAndMeasure> getIngredients(ArrayList<String> ingredients, ArrayList<String> measures)
    {
        ArrayList<MealIngredientAndMeasure> mealIngredients = new ArrayList<>();
        for(int i = 0; i < ingredients.size(); i++)
        {
            mealIngredients.add(new MealIngredientAndMeasure(ingredients.get(i), measures.get(i)));
        }
        return mealIngredients;
    }

}