package com.example.warmmeal.meal_screen.view;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.warmmeal.R;
import com.example.warmmeal.fragment_favourite.view.OnGetFavouriteMealResponse;
import com.example.warmmeal.fragment_home.view.HomeFragment;
import com.example.warmmeal.meal_screen.presenter.MealScreenPresenter;
import com.example.warmmeal.model.database.DatabaseHandler;
import com.example.warmmeal.model.firebase.FirebaseHandler;
import com.example.warmmeal.model.network.NetworkAPI;
import com.example.warmmeal.model.pojo.FavouriteMeal;
import com.example.warmmeal.model.pojo.Meal;
import com.example.warmmeal.model.pojo.MealIngredientAndMeasure;
import com.example.warmmeal.model.pojo.Meals;
import com.example.warmmeal.model.repository.RepositoryImpl;
import com.example.warmmeal.model.shared_pref.SharedPrefHandler;
import com.example.warmmeal.model.util.CustomProgressBar;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

import java.util.ArrayList;
import java.util.List;

public class MealActivity extends AppCompatActivity implements OnMealScreenResponse, OnGetFavouriteMealResponse {

    ImageView mealImage;
    TextView mealName;
    TextView mealCountry;
    TextView mealInstructions;
    RecyclerView ingredientsRecyclerView;
    YouTubePlayerView mealYoutubePlayer;
    Button addToFavourite,backButton;


    //presenter
    MealScreenPresenter presenter;
    IngredientsRecyclerViewAdapter mAdapter;
    CustomProgressBar customProgressBar;

    String mealId;

    private static boolean isFavouriteMealsFetched = false;

    Meal currentMeal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal);
        init();
        setUp();

    }

    void init()
    {
        isFavouriteMealsFetched = false;
        //currentMeal = getIntent().getParcelableExtra(HomeFragment.MEAL_KEY);
        mealId = getIntent().getStringExtra(HomeFragment.MEAL_KEY);

        mealImage = findViewById(R.id.mealScreenImage);
        mealName = findViewById(R.id.mealScreenMealName);
        mealCountry = findViewById(R.id.mealScreenMealCountry);
        mealInstructions = findViewById(R.id.mealScreenMealInstructions);
        ingredientsRecyclerView = findViewById(R.id.mealScreenRecyclerView);
        mealYoutubePlayer = findViewById(R.id.mealScreenVideoViewer);
        addToFavourite = findViewById(R.id.mealScreenAddToFavourite);
        backButton = findViewById(R.id.mealScreenBack);

        presenter = MealScreenPresenter.getInstance(RepositoryImpl.getInstance(FirebaseHandler.getInstance(), NetworkAPI.getInstance(), DatabaseHandler.getInstance(this), SharedPrefHandler.getInstance(this)));

        presenter.getMealById(mealId,this);
        //setMealData(currentMeal);
    }

    void setUp()
    {
        backButton.setOnClickListener((v) -> {
            finish();
        });
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

        if(meal.getStrYoutube() != null && !meal.getStrYoutube().equals(""))
        {
            youtubePlayerSetUp(meal.getStrYoutube());
        }

    }

    void youtubePlayerSetUp(String url)
    {
        getLifecycle().addObserver(mealYoutubePlayer);
        mealYoutubePlayer.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
            @Override
            public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                String id = getVideoId(url);
                youTubePlayer.loadVideo(id, 0);
                youTubePlayer.pause();
            }
        });
    }

    String getVideoId(String url)
    {
        String videoId = "";
        String[] parts = url.split("=");
        if(parts.length > 1)
        {
            videoId = parts[1];
        }
        return videoId;
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

    @Override
    public void onGetMealByIdSuccess(Meals meals) {

        setMealData(meals.getMeals().get(0));
    }

    @Override
    public void onFailure(String message) {
        Log.d("Kerolos", "onFailure: " + message);
    }

    @Override
    public void onGetFavouriteMealSuccess(List<FavouriteMeal> favouriteMeals) {

    }

    @Override
    public void onGetFavouriteMealFailure(String message) {
        Log.d("Kerolos", "onGetFavouriteMealFailure: " + message);
    }
}