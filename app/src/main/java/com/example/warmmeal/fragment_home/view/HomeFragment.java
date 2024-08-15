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
import com.example.warmmeal.fragment_home.presenter.HomeFragmentPresenter;
import com.example.warmmeal.fragment_home.view.adapters.HomeRecyclerViewAdapter;
import com.example.warmmeal.fragment_search.view.OnNetworkCallResponse;
import com.example.warmmeal.model.pojo.Categories;
import com.example.warmmeal.model.pojo.Category;
import com.example.warmmeal.model.repository.RepositoryImpl;
import com.example.warmmeal.model.database.DatabaseHandler;
import com.example.warmmeal.model.firebase.FirebaseHandler;
import com.example.warmmeal.model.network.NetworkAPI;
import com.example.warmmeal.model.pojo.Meal;
import com.example.warmmeal.model.pojo.Meals;
import com.example.warmmeal.model.shared_pref.SharedPrefHandler;

import java.util.ArrayList;

public class HomeFragment extends Fragment implements OnNestedRecyclerViewItemClickedListener, OnNetworkCallResponse {


    ////
    HomeFragmentPresenter presenter;

    ////
    RecyclerView recyclerView;
    StackView stackView;

    ///lists
    ArrayList<Meal> dailyInspirationMeals;
    ArrayList<Category> categories;
    ArrayList<Meal> countries;
    ArrayList<Meal> mealsYouMightLike;
    ArrayList<HomeFragmentItem<Object>> homeFragmentItems;


    Context context;
    HomeRecyclerViewAdapter mAdapter;


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
        recyclerView = view.findViewById(R.id.homeRecyclerView);
        dailyInspirationMeals = new ArrayList<>();
        categories = new ArrayList<>();
        countries = new ArrayList<>();
        homeFragmentItems = new ArrayList<>();
        context = view.getContext();

        try {
            presenter = HomeFragmentPresenter.getInstance(RepositoryImpl.getInstance(FirebaseHandler.getInstance(), NetworkAPI.getInstance(), DatabaseHandler.getInstance(context), SharedPrefHandler.getInstance()));

            presenter.getMealsByFirstLetter('b',this);
            presenter.getAllCategories(this);
        }catch (Exception e) {
            Log.d("Kerolos", "init: " + e.getMessage());
            Toast.makeText(context, "Error : " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }

    void setUp()
    {



        Meal meal = new Meal();
        meal.setStrMeal("Kepda");
        meal.setStrArea("Egypt");
        meal.setStrMealThumb("https://buffer.com/library/content/images/size/w1200/2023/10/free-images.jpg");
        meal.setStrCategory("Dessert");


        homeFragmentItems.add(new HomeFragmentItem<>(ItemType.HEADER_TEXT,"Daily Inspiration :"));

        dailyInspirationMeals.add(meal);
        dailyInspirationMeals.add(meal);
        dailyInspirationMeals.add(meal);
        dailyInspirationMeals.add(meal);
        dailyInspirationMeals.add(meal);
        dailyInspirationMeals.add(meal);
        dailyInspirationMeals.add(meal);
        dailyInspirationMeals.add(meal);

        homeFragmentItems.add(new HomeFragmentItem<>(ItemType.DAILY_INSPIRATION,dailyInspirationMeals));

        homeFragmentItems.add(new HomeFragmentItem<>(ItemType.HEADER_TEXT,"Categories :"));



        homeFragmentItems.add(new HomeFragmentItem<>(ItemType.CATEGORY,categories));

        homeFragmentItems.add(new HomeFragmentItem<>(ItemType.HEADER_TEXT,"Countries :"));

        countries.add(meal);
        countries.add(meal);
        countries.add(meal);
        countries.add(meal);
        countries.add(meal);
        countries.add(meal);
        countries.add(meal);
        countries.add(meal);

        homeFragmentItems.add(new HomeFragmentItem<>(ItemType.CATEGORY,countries));

        homeFragmentItems.add(new HomeFragmentItem<>(ItemType.HEADER_TEXT,"Meals you might like :"));


        homeFragmentItems.add(new HomeFragmentItem<>(ItemType.MEALS_YOU_MIGHT_LIKE, meal));
        homeFragmentItems.add(new HomeFragmentItem<>(ItemType.MEALS_YOU_MIGHT_LIKE, meal));
        homeFragmentItems.add(new HomeFragmentItem<>(ItemType.MEALS_YOU_MIGHT_LIKE, meal));
        homeFragmentItems.add(new HomeFragmentItem<>(ItemType.MEALS_YOU_MIGHT_LIKE, meal));
        homeFragmentItems.add(new HomeFragmentItem<>(ItemType.MEALS_YOU_MIGHT_LIKE, meal));
        homeFragmentItems.add(new HomeFragmentItem<>(ItemType.MEALS_YOU_MIGHT_LIKE, meal));
        homeFragmentItems.add(new HomeFragmentItem<>(ItemType.MEALS_YOU_MIGHT_LIKE, meal));

    }

    void setUpRecyclerViewWithLists()
    {
        homeFragmentItems.clear();

        homeFragmentItems.add(new HomeFragmentItem<>(ItemType.HEADER_TEXT,"Daily Inspiration :"));

        homeFragmentItems.add(new HomeFragmentItem<>(ItemType.DAILY_INSPIRATION,dailyInspirationMeals));

        homeFragmentItems.add(new HomeFragmentItem<>(ItemType.HEADER_TEXT,"Categories :"));

        homeFragmentItems.add(new HomeFragmentItem<>(ItemType.CATEGORY,categories));

        homeFragmentItems.add(new HomeFragmentItem<>(ItemType.HEADER_TEXT,"Countries :"));

        homeFragmentItems.add(new HomeFragmentItem<>(ItemType.COUNTRY,countries));

        homeFragmentItems.add(new HomeFragmentItem<>(ItemType.HEADER_TEXT,"Meals you might like :"));

        for(Meal meal : mealsYouMightLike)
        {
            homeFragmentItems.add(new HomeFragmentItem<>(ItemType.MEALS_YOU_MIGHT_LIKE, meal));
        }

        mAdapter.setData(homeFragmentItems);

    }

    @Override
    public void onMealClicked(Meal meal) {
        Toast.makeText(context, "meal clicked " + meal.getStrMeal(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onAddToFavouriteClicked(Meal meal) {
        Toast.makeText(context, "add to favourite clicked " + meal.getStrMeal() , Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCategoryClicked(String category) {
        Toast.makeText(context, "category clicked " + category, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCountryClicked(String country) {
        Toast.makeText(context, "category clicked " + country, Toast.LENGTH_SHORT).show();
    }


///////onNetworkCallResponse
    @Override
    public void onGetMealByCharacterSuccess(Meals meals) {
        mealsYouMightLike = (ArrayList<Meal>) meals.getMeals();
        setUpRecyclerViewWithLists();
    }

    @Override
    public void onGetMealByCharacterFailure(String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onGetCategorySuccess(Categories categories) {
        this.categories = (ArrayList<Category>) categories.getCategories();
        Log.d("Kerolos", "onGetCategorySuccess: " + categories.getCategories().size());
        //setUpRecyclerViewWithLists();
    }

    @Override
    public void onGetCategoryFailure(String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
    ////////
}