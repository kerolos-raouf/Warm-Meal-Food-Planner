package com.example.warmmeal.fragment_home.view;

import android.content.Context;
import android.os.Bundle;
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
import com.example.warmmeal.fragment_home.view.adapters.HomeRecyclerViewAdapter;
import com.example.warmmeal.model.pojo.Meal;

import java.util.ArrayList;

public class HomeFragment extends Fragment implements OnNestedRecyclerViewItemClickedListener {


    RecyclerView recyclerView;
    StackView stackView;
    ArrayList<Meal> dailyInspirationMeals;
    ArrayList<Meal> categories;
    ArrayList<Meal> countries;
    ArrayList<HomeFragmentItem<Object>> homeFragmentItems;
    Context context;


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

        HomeRecyclerViewAdapter adapter = new HomeRecyclerViewAdapter(getContext(), homeFragmentItems,this);
        recyclerView.setAdapter(adapter);


    }

    void init(View view)
    {
        recyclerView = view.findViewById(R.id.homeRecyclerView);
        dailyInspirationMeals = new ArrayList<>();
        categories = new ArrayList<>();
        countries = new ArrayList<>();
        homeFragmentItems = new ArrayList<>();
        context = view.getContext();
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

        categories.add(meal);
        categories.add(meal);
        categories.add(meal);
        categories.add(meal);
        categories.add(meal);
        categories.add(meal);
        categories.add(meal);
        categories.add(meal);

        homeFragmentItems.add(new HomeFragmentItem<>(ItemType.CATEGORY_COUNTRY,categories));

        homeFragmentItems.add(new HomeFragmentItem<>(ItemType.HEADER_TEXT,"Countries :"));

        countries.add(meal);
        countries.add(meal);
        countries.add(meal);
        countries.add(meal);
        countries.add(meal);
        countries.add(meal);
        countries.add(meal);
        countries.add(meal);

        homeFragmentItems.add(new HomeFragmentItem<>(ItemType.CATEGORY_COUNTRY,countries));

        homeFragmentItems.add(new HomeFragmentItem<>(ItemType.HEADER_TEXT,"Meals you might like :"));


        homeFragmentItems.add(new HomeFragmentItem<>(ItemType.MEALS_YOU_MIGHT_LIKE, meal));
        homeFragmentItems.add(new HomeFragmentItem<>(ItemType.MEALS_YOU_MIGHT_LIKE, meal));
        homeFragmentItems.add(new HomeFragmentItem<>(ItemType.MEALS_YOU_MIGHT_LIKE, meal));
        homeFragmentItems.add(new HomeFragmentItem<>(ItemType.MEALS_YOU_MIGHT_LIKE, meal));
        homeFragmentItems.add(new HomeFragmentItem<>(ItemType.MEALS_YOU_MIGHT_LIKE, meal));
        homeFragmentItems.add(new HomeFragmentItem<>(ItemType.MEALS_YOU_MIGHT_LIKE, meal));
        homeFragmentItems.add(new HomeFragmentItem<>(ItemType.MEALS_YOU_MIGHT_LIKE, meal));

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
}