package com.example.warmmeal.meal_screen.view;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.warmmeal.R;
import com.example.warmmeal.fragment_favourite.view.OnAddToFavouriteResponse;
import com.example.warmmeal.fragment_favourite.view.OnDeleteFromFavouriteResponse;
import com.example.warmmeal.fragment_home.view.HomeFragment;
import com.example.warmmeal.meal_screen.presenter.MealScreenPresenter;
import com.example.warmmeal.model.database.DatabaseHandler;
import com.example.warmmeal.model.firebase.FirebaseHandler;
import com.example.warmmeal.model.internet_connection.ConnectivityObserver;
import com.example.warmmeal.model.network.NetworkAPI;
import com.example.warmmeal.model.pojo.PlanMeal;
import com.example.warmmeal.model.pojo.FavouriteMeal;
import com.example.warmmeal.model.pojo.Meal;
import com.example.warmmeal.model.pojo.MealIngredientAndMeasure;
import com.example.warmmeal.model.pojo.Meals;
import com.example.warmmeal.model.repository.RepositoryImpl;
import com.example.warmmeal.model.shared_pref.SharedPrefHandler;
import com.example.warmmeal.model.util.CustomProgressBar;
import com.example.warmmeal.model.util.Day;
import com.google.android.material.snackbar.Snackbar;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

import java.util.ArrayList;

public class MealActivity extends AppCompatActivity implements IMealScreen, OnAddToFavouriteResponse, OnDeleteFromFavouriteResponse,DayChooserItemOnClickListener {

    ImageView mealImage;
    TextView mealName;
    TextView mealCountry;
    TextView mealInstructions;
    RecyclerView ingredientsRecyclerView;
    YouTubePlayerView mealYoutubePlayer;
    Button addToFavourite,backButton,addToPlan;

    ConnectivityObserver connectivityObserver;


    //presenter
    MealScreenPresenter presenter;
    IngredientsRecyclerViewAdapter mAdapter;
    CustomProgressBar customProgressBar;

    ///intent extras
    String mealId;
    boolean isFavourite;

    Meal currentMeal;

    ///day chooer
    DayChooser dayChooser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal);
        init();
        setUp();
    }

    void init()
    {
        ///connectivity observer
        connectivityObserver = new ConnectivityObserver(this);

        //day chooser
        dayChooser = new DayChooser(this);
        //currentMeal = getIntent().getParcelableExtra(HomeFragment.MEAL_KEY);
        mealId = getIntent().getStringExtra(HomeFragment.MEAL_KEY);
        isFavourite = getIntent().getBooleanExtra(HomeFragment.IS_FAVOURITE_KEY,false);

        mealImage = findViewById(R.id.mealScreenImage);
        mealName = findViewById(R.id.mealScreenMealName);
        mealCountry = findViewById(R.id.mealScreenMealCountry);
        mealInstructions = findViewById(R.id.mealScreenMealInstructions);
        ingredientsRecyclerView = findViewById(R.id.mealScreenRecyclerView);
        mealYoutubePlayer = findViewById(R.id.mealScreenVideoViewer);
        addToFavourite = findViewById(R.id.mealScreenAddToFavourite);
        backButton = findViewById(R.id.mealScreenBack);
        addToPlan = findViewById(R.id.mealScreenAddToCalendar);

        presenter = MealScreenPresenter.getInstance(RepositoryImpl.getInstance(FirebaseHandler.getInstance(), NetworkAPI.getInstance(), DatabaseHandler.getInstance(this), SharedPrefHandler.getInstance(this)),this,connectivityObserver);

        presenter.checkInternetStatus();

        if(ConnectivityObserver.InternetStatus == ConnectivityObserver.Status.Available)
        {
            presenter.getMealById(mealId,this);
        }else
        {
            Toast.makeText(this, "Check your internet connection.", Toast.LENGTH_SHORT).show();
        }
        //setMealData(currentMeal);
    }

    void setUp()
    {
        if(isFavourite)
        {
            addToFavourite.setText("Remove from favourites");
        }

        addToFavourite.setOnClickListener((e)->{
            if(FirebaseHandler.CURRENT_USER_ID != null)
            {
                if(currentMeal != null)
                {
                    if(isFavourite)
                    {
                        presenter.deleteFromFavourite(new FavouriteMeal(FirebaseHandler.CURRENT_USER_ID,mealId,mealName.getText().toString(),currentMeal.getStrMealThumb(),true), this);
                    }else {
                        presenter.addFavouriteMeal(new FavouriteMeal(FirebaseHandler.CURRENT_USER_ID,mealId,mealName.getText().toString(),currentMeal.getStrMealThumb(),true), this);
                    }
                    isFavourite = !isFavourite;
                }
                else
                {
                    Toast.makeText(this, "Poor network connection.", Toast.LENGTH_SHORT).show();
                }
                
            }else
            {
                Toast.makeText(this, "Please login first.", Toast.LENGTH_SHORT).show();
            }
        });


        addToPlan.setOnClickListener((v) -> {
            dayChooser.startDayChooser(this);
        });

        backButton.setOnClickListener((v) -> {
            finish();
        });
    }



    @Override
    public void onStop() {
        super.onStop();
        presenter.clearDisposable();
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
    public void onNetworkCallResponse(ConnectivityObserver.Status status, String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onFail(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onGetMealByIdSuccess(Meals meals) {
        currentMeal = meals.getMeals().get(0);
        setMealData(currentMeal);
    }

    @Override
    public void onAddMealToPlanSuccess(String day) {
        Snackbar.make(findViewById(android.R.id.content), "Meal Added to " + day + ".", Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void onFailure(String message) {
        Log.d("Kerolos", "onFailure: " + message);
    }


    @Override
    public void onAddToFavouriteSuccess() {
        addToFavourite.setText("Remove from favourites");
        Snackbar.make(findViewById(android.R.id.content), "Meal Added to favourites", Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void onAddToFavouriteFailure(String message) {
        Log.d("Kerolos", "onAddToFavouriteFailure: " + message);
    }

    @Override
    public void onDeleteFromFavouriteSuccess() {
        addToFavourite.setText("Add to favourites");
        Snackbar.make(findViewById(android.R.id.content), "Meal Removed from favourites", Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void onDeleteFromFavouriteFailure(String message) {
        Log.d("Kerolos", "onDeleteFromFavouriteFailure: " + message);
    }


    /////////day chooser item onclick
    @Override
    public void onMondayClicked() {
        presenter.addMealToPlan(new PlanMeal(FirebaseHandler.CURRENT_USER_ID, Day.MONDAY,currentMeal.getIdMeal(),currentMeal.getStrMeal(),currentMeal.getStrMealThumb()));
        dayChooser.dismiss();
    }

    @Override
    public void onTuesdayClicked() {
        presenter.addMealToPlan(new PlanMeal(FirebaseHandler.CURRENT_USER_ID, Day.TUESDAY,currentMeal.getIdMeal(),currentMeal.getStrMeal(),currentMeal.getStrMealThumb()));
        dayChooser.dismiss();
    }

    @Override
    public void onWednesdayClicked() {
        presenter.addMealToPlan(new PlanMeal(FirebaseHandler.CURRENT_USER_ID, Day.WEDNESDAY,currentMeal.getIdMeal(),currentMeal.getStrMeal(),currentMeal.getStrMealThumb()));
        dayChooser.dismiss();
    }

    @Override
    public void onThursdayClicked() {
        presenter.addMealToPlan(new PlanMeal(FirebaseHandler.CURRENT_USER_ID, Day.THURSDAY,currentMeal.getIdMeal(),currentMeal.getStrMeal(),currentMeal.getStrMealThumb()));
        dayChooser.dismiss();
    }

    @Override
    public void onFridayClicked() {
        presenter.addMealToPlan(new PlanMeal(FirebaseHandler.CURRENT_USER_ID, Day.FRIDAY,currentMeal.getIdMeal(),currentMeal.getStrMeal(),currentMeal.getStrMealThumb()));
        dayChooser.dismiss();
    }

    @Override
    public void onSaturdayClicked() {
        presenter.addMealToPlan(new PlanMeal(FirebaseHandler.CURRENT_USER_ID, Day.SATURDAY,currentMeal.getIdMeal(),currentMeal.getStrMeal(),currentMeal.getStrMealThumb()));
        dayChooser.dismiss();
    }

    @Override
    public void onSundayClicked() {
        presenter.addMealToPlan(new PlanMeal(FirebaseHandler.CURRENT_USER_ID, Day.SUNDAY,currentMeal.getIdMeal(),currentMeal.getStrMeal(),currentMeal.getStrMealThumb()));
        dayChooser.dismiss();
    }
}