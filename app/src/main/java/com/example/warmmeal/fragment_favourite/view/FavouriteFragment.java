package com.example.warmmeal.fragment_favourite.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.warmmeal.R;
import com.example.warmmeal.fragment_favourite.presenter.FavouritePresenter;
import com.example.warmmeal.fragment_home.view.HomeFragment;
import com.example.warmmeal.fragment_search.view.OnSearchRecyclerViewItemClicked;
import com.example.warmmeal.fragment_search.view.SearchRecyclerViewAdapter;
import com.example.warmmeal.meal_screen.view.MealActivity;
import com.example.warmmeal.model.database.DatabaseHandler;
import com.example.warmmeal.model.firebase.FirebaseHandler;
import com.example.warmmeal.model.network.NetworkAPI;
import com.example.warmmeal.model.pojo.FavouriteMeal;
import com.example.warmmeal.model.pojo.Meal;
import com.example.warmmeal.model.repository.RepositoryImpl;
import com.example.warmmeal.model.shared_pref.SharedPrefHandler;
import com.example.warmmeal.model.util.Navigator;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;


public class FavouriteFragment extends Fragment implements OnGetFavouriteMealResponse , OnSearchRecyclerViewItemClicked ,OnDeleteFromFavouriteResponse{


    RecyclerView recyclerView;
    TextView noResult;

    FavouritePresenter presenter;
    SearchRecyclerViewAdapter mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favourite, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view);
        setUp();
    }

    void init(View view)
    {
        recyclerView = view.findViewById(R.id.favouriteRecyclerView);
        noResult = view.findViewById(R.id.favouriteNoResult);

        presenter = FavouritePresenter.getInstance(RepositoryImpl.getInstance(FirebaseHandler.getInstance(), NetworkAPI.getInstance(), DatabaseHandler.getInstance(view.getContext()), SharedPrefHandler.getInstance(view.getContext())));
    }

    void setUp()
    {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        mAdapter = new SearchRecyclerViewAdapter(new ArrayList<>(),this,getContext(),true);
        recyclerView.setAdapter(mAdapter);


    }

    @Override
    public void onStart() {
        super.onStart();
        presenter.getAllFavouriteMeals(FirebaseHandler.CURRENT_USER_ID,this);
    }

    @Override
    public void onGetFavouriteMealSuccess(List<FavouriteMeal> favouriteMeals) {
        if(favouriteMeals.isEmpty())
        {
            noResult.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        }
        else
        {
            noResult.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
            ArrayList<Meal> meals = new ArrayList<>();
            for(FavouriteMeal favMeal : favouriteMeals)
            {
                Meal currentMeal = new Meal();
                currentMeal.setIdMeal(favMeal.idMeal);
                currentMeal.setStrMeal(favMeal.strMeal);
                currentMeal.setStrMealThumb(favMeal.strMealThumb);
                currentMeal.setFavourite(true);
                meals.add(currentMeal);
            }

            mAdapter.setMeals(meals);
        }
    }

    @Override
    public void onGetFavouriteMealFailure(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onMealClicked(Meal meal) {
        Navigator.navigateWithExtra(getContext(), MealActivity.class, HomeFragment.MEAL_KEY,meal.getIdMeal(),HomeFragment.IS_FAVOURITE_KEY,meal.isFavourite());
    }

    @Override
    public void onAddToFavouriteClicked(Meal meal) {
        presenter.deleteFavouriteMeal(new FavouriteMeal(FirebaseHandler.CURRENT_USER_ID,meal.getIdMeal(),meal.getStrMeal(),meal.getStrMealThumb(),meal.isFavourite()),this);
    }

    @Override
    public void onDeleteFromFavouriteSuccess() {
        Snackbar.make(recyclerView,"Meal was removed from favourites", Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void onDeleteFromFavouriteFailure(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }
}