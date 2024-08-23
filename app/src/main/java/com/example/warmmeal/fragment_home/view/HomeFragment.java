package com.example.warmmeal.fragment_home.view;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.StackView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.warmmeal.R;

import com.example.warmmeal.category_country_screen.view.CategoryAndCountryScreen;
import com.example.warmmeal.category_country_screen.view.Type;
import com.example.warmmeal.fragment_favourite.view.OnAddToFavouriteResponse;
import com.example.warmmeal.fragment_favourite.view.OnDeleteFromFavouriteResponse;
import com.example.warmmeal.fragment_favourite.view.OnGetFavouriteMealResponse;
import com.example.warmmeal.fragment_home.presenter.HomeFragmentPresenter;
import com.example.warmmeal.fragment_home.view.adapters.HomeRecyclerViewAdapter;
import com.example.warmmeal.fragment_home.view.contracts.IHomeFragment;
import com.example.warmmeal.fragment_home.view.contracts.OnNestedRecyclerViewItemClickedListener;
import com.example.warmmeal.fragment_home.view.contracts.OnNetworkCallResponse;
import com.example.warmmeal.meal_screen.view.MealActivity;
import com.example.warmmeal.model.internet_connection.ConnectivityObserver;
import com.example.warmmeal.model.pojo.Categories;
import com.example.warmmeal.model.pojo.FavouriteMeal;
import com.example.warmmeal.model.repository.RepositoryImpl;
import com.example.warmmeal.model.database.DatabaseHandler;
import com.example.warmmeal.model.firebase.FirebaseHandler;
import com.example.warmmeal.model.network.NetworkAPI;

import com.example.warmmeal.model.pojo.Meal;
import com.example.warmmeal.model.pojo.Meals;
import com.example.warmmeal.model.shared_pref.SharedPrefHandler;
import com.example.warmmeal.model.util.CustomProgressBar;
import com.example.warmmeal.model.util.Navigator;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class HomeFragment extends Fragment implements OnNestedRecyclerViewItemClickedListener, OnNetworkCallResponse, OnAddToFavouriteResponse, OnGetFavouriteMealResponse , OnDeleteFromFavouriteResponse , IHomeFragment {



    ////
    public static final String MEAL_KEY = "MEAL_KEY";
    public static final String IS_FAVOURITE_KEY = "IS_FAVOURITE_KEY";
    public static final String CATEGORY_COUNTRY_KEY = "CATEGORY_COUNTRY_KEY";
    public static String CATEGORY_COUNTRY_TYPE = "CATEGORY";
    ////
    HomeFragmentPresenter presenter;

    ////
    RecyclerView recyclerView;
    StackView stackView;

    ///lists
    ArrayList<Meal> dailyInspirationMeals;
    ArrayList<Meal> categories;
    ArrayList<Meal> countries;
    ArrayList<Meal> mealsYouMightLike;
    ArrayList<FavouriteMeal> favouriteMeals;
    ArrayList<HomeFragmentItem<Object>> homeFragmentItems;
    Context context;


    HomeRecyclerViewAdapter mAdapter;

    ///for dialog
    CustomProgressBar customProgressBar;
    public static boolean isFavouriteMealsFetched = false;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view);
        setUp();
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);

        mAdapter = new HomeRecyclerViewAdapter(getContext(), homeFragmentItems,this);
        recyclerView.setAdapter(mAdapter);

    }

    void init(View view)
    {
        isFavouriteMealsFetched = false;
        customProgressBar = new CustomProgressBar(getActivity());
        customProgressBar.startProgressBar();
        recyclerView = view.findViewById(R.id.homeRecyclerView);
        dailyInspirationMeals = new ArrayList<>();
        categories = new ArrayList<>();
        countries = new ArrayList<>();
        homeFragmentItems = new ArrayList<>();
        mealsYouMightLike = new ArrayList<>();
        favouriteMeals = new ArrayList<>();
        context = view.getContext();

        presenter = HomeFragmentPresenter.getInstance(RepositoryImpl.getInstance(FirebaseHandler.getInstance(), NetworkAPI.getInstance(), DatabaseHandler.getInstance(context), SharedPrefHandler.getInstance(context)),this);

        //presenter.getMealsByFirstLetter('a',DataPurpose.INSPIRATION,this);
        presenter.getRandomMeal();
        presenter.getMealsByFirstLetter('b',DataPurpose.MORE_YOU_LIKE,this);
        presenter.getAllCategories(this);
        presenter.getAllCountries(this);


    }

    void setUp()
    {


    }


    public static String DAILY_INSPIRATION = "Inspiration Meals";
    public static String CATEGORY = "Categories";
    public static String COUNTRY = "Countries";
    public static String MEALS_YOU_MIGHT_LIKE = "Meals you might like";

    void setUpRecyclerViewWithLists()
    {
        homeFragmentItems.clear();

        homeFragmentItems.add(new HomeFragmentItem<>(ItemType.HEADER_TEXT,DAILY_INSPIRATION));

        homeFragmentItems.add(new HomeFragmentItem<>(ItemType.DAILY_INSPIRATION,dailyInspirationMeals));

        homeFragmentItems.add(new HomeFragmentItem<>(ItemType.HEADER_TEXT,CATEGORY));

        homeFragmentItems.add(new HomeFragmentItem<>(ItemType.CATEGORY,categories));

        homeFragmentItems.add(new HomeFragmentItem<>(ItemType.HEADER_TEXT,COUNTRY));

        homeFragmentItems.add(new HomeFragmentItem<>(ItemType.COUNTRY,countries));

        homeFragmentItems.add(new HomeFragmentItem<>(ItemType.HEADER_TEXT,MEALS_YOU_MIGHT_LIKE));

        homeFragmentItems.add(new HomeFragmentItem<>(ItemType.MEALS_YOU_MIGHT_LIKE, mealsYouMightLike));


        mAdapter.setData(homeFragmentItems);
    }

    @Override
    public void onMealClicked(Meal meal) {

        if(ConnectivityObserver.InternetStatus == ConnectivityObserver.Status.Available)
        {
            customProgressBar.startProgressBar();
            Navigator.navigateWithExtra(context, MealActivity.class, MEAL_KEY,meal.getIdMeal(),IS_FAVOURITE_KEY,meal.isFavourite());        }else
        {
            Toast.makeText(context, "You lost internet connection.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onAddToFavouriteClicked(Meal meal) {
        if (!meal.isFavourite() && FirebaseHandler.CURRENT_USER_ID != null)
        {
            presenter.deleteFromFavourite(new FavouriteMeal(FirebaseHandler.CURRENT_USER_ID,meal.getIdMeal(),meal.getStrMeal(),meal.getStrMealThumb(),true),this);
        }else if(FirebaseHandler.CURRENT_USER_ID != null)
        {
            presenter.addFavouriteMeal(new FavouriteMeal(FirebaseHandler.CURRENT_USER_ID,meal.getIdMeal(),meal.getStrMeal(),meal.getStrMealThumb(),true),this);
        }else {
            Toast.makeText(context, "Please login first", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onCategoryClicked(String category) {
        //Toast.makeText(context, "category clicked " + category + " " + Type.CATEGORY.toString(), Toast.LENGTH_SHORT).show();
        if(ConnectivityObserver.InternetStatus == ConnectivityObserver.Status.Available)
        {
            Navigator.navigateWithExtra(context, CategoryAndCountryScreen.class, CATEGORY_COUNTRY_TYPE, Type.CATEGORY.toString(), CATEGORY_COUNTRY_KEY,category);
        }else
        {
            Toast.makeText(context, "You lost internet connection.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onCountryClicked(String country) {
        if(ConnectivityObserver.InternetStatus == ConnectivityObserver.Status.Available)
        {
            Navigator.navigateWithExtra(context, CategoryAndCountryScreen.class, CATEGORY_COUNTRY_TYPE,Type.COUNTRY.toString(),CATEGORY_COUNTRY_KEY,country);
        }else
        {
            Toast.makeText(context, "You lost internet connection.", Toast.LENGTH_SHORT).show();
        }
    }


///////onNetworkCallResponse
    @Override
    public void onGetMealByCharacterForMoreYouLikeSuccess(Meals meals) {
        mealsYouMightLike = (ArrayList<Meal>) meals.getMeals();
        setUpRecyclerViewWithLists();
        presenter.getAllFavouriteMeals(FirebaseHandler.CURRENT_USER_ID,this);

    }

    @Override
    public void onGetMealByCharacterForInspirationSuccess(Meals meals) {
        dailyInspirationMeals = (ArrayList<Meal>) meals.getMeals();
        setUpRecyclerViewWithLists();
    }


    @Override
    public void onGetCategorySuccess(Categories categories) {
        this.categories = (ArrayList<Meal>) categories.getCategories();
        setUpRecyclerViewWithLists();
        customProgressBar.dismissProgressBar();
    }

    @Override
    public void onGetAllCountriesSuccess(Meals meals) {
        this.countries = (ArrayList<Meal>) meals.getMeals();
        setUpRecyclerViewWithLists();
        customProgressBar.dismissProgressBar();
    }

    @Override
    public void onGetRandomMealSuccess(Meals meals) {
        dailyInspirationMeals.add(meals.getMeals().get(0));
    }

    @Override
    public void onGetRandomMealComplete() {
        setUpRecyclerViewWithLists();
    }

    @Override
    public void onFailure(String message) {
        Log.d("Kerolos", "Home Fragment onFailure: " + message);
        customProgressBar.dismissProgressBar();
    }



    ////////


    @Override
    public void onPause() {
        super.onPause();
        customProgressBar.dismissProgressBar();
    }

    @Override
    public void onStop() {
        super.onStop();
        presenter.clearDisposable();
    }

    @Override
    public void onAddToFavouriteSuccess() {
        Snackbar.make(recyclerView,"Meal was added to favourites", Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void onAddToFavouriteFailure(String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onGetFavouriteMealSuccess(List<FavouriteMeal> favouriteMeals) {
        if(!isFavouriteMealsFetched && FirebaseHandler.CURRENT_USER_ID != null)
        {
            FavouriteMeal.getFavouriteMealsList((ArrayList<FavouriteMeal>) favouriteMeals, dailyInspirationMeals);
            FavouriteMeal.getFavouriteMealsList((ArrayList<FavouriteMeal>) favouriteMeals, mealsYouMightLike);
            setUpRecyclerViewWithLists();
            customProgressBar.dismissProgressBar();
            isFavouriteMealsFetched = true;
        }
        else {
            customProgressBar.dismissProgressBar();
        }
    }

    @Override
    public void onGetFavouriteMealFailure(String message) {
        Log.d("Kerolos", "onGetFavouriteMealFailure: " + message);
    }

    @Override
    public void onDeleteFromFavouriteSuccess() {
        Snackbar.make(recyclerView,"Meal was removed from favourites", Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void onDeleteFromFavouriteFailure(String message) {
        Log.d("Kerolos", "onDeleteFromFavouriteFailure: " + message);
    }
}