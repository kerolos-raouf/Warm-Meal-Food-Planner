package com.example.warmmeal.fragment_plan.view;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.warmmeal.R;
import com.example.warmmeal.fragment_home.view.HomeFragment;
import com.example.warmmeal.fragment_plan.presenter.PlanFragmentPresenter;
import com.example.warmmeal.meal_screen.view.MealActivity;
import com.example.warmmeal.model.database.DatabaseHandler;
import com.example.warmmeal.model.firebase.FirebaseHandler;
import com.example.warmmeal.model.internet_connection.ConnectivityObserver;
import com.example.warmmeal.model.network.NetworkAPI;
import com.example.warmmeal.model.pojo.PlanMeal;
import com.example.warmmeal.model.repository.RepositoryImpl;
import com.example.warmmeal.model.shared_pref.SharedPrefHandler;
import com.example.warmmeal.model.util.CustomProgressBar;
import com.example.warmmeal.model.util.Day;
import com.example.warmmeal.model.util.Navigator;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class PlanFragment extends Fragment implements IPlanFragmentView,OnPlanFragmentRecyclerViewItemClicked{


    private static final String TAG = "Kerolos";
    Button mondayAdd,tuesdayAdd,wednesdayAdd,thursdayAdd,fridayAdd,saturdayAdd,sundayAdd;
    RecyclerView mondayRecyclerView,tuesdayRecyclerView,wednesdayRecyclerView,thursdayRecyclerView,fridayRecyclerView,saturdayRecyclerView,sundayRecyclerView;


    PlanFragmentPresenter presenter;
    CustomProgressBar customProgressBar;

    ArrayList<PlanMeal> mondayMeals;
    ArrayList<PlanMeal> tuesdayMeals;
    ArrayList<PlanMeal> wednesdayMeals;
    ArrayList<PlanMeal> thursdayMeals;
    ArrayList<PlanMeal> fridayMeals;
    ArrayList<PlanMeal> saturdayMeals;
    ArrayList<PlanMeal> sundayMeals;

    PlanFragmentRecyclerViewAdapter mondayAdapter,tuesdayAdapter,wednesdayAdapter,thursdayAdapter,fridayAdapter,saturdayAdapter,sundayAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_calendar, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d(TAG, "onViewCreated: ");
        init(view);
        setUp();
    }


    void init(View view)
    {
        customProgressBar = new CustomProgressBar(getActivity());

        mondayAdd = view.findViewById(R.id.calendarMondayAdd);
        tuesdayAdd = view.findViewById(R.id.calendarTuesdayAdd);
        wednesdayAdd = view.findViewById(R.id.calendarWednesdayAdd);
        thursdayAdd = view.findViewById(R.id.calendarThursdayAdd);
        fridayAdd = view.findViewById(R.id.calendarFridayAdd);
        saturdayAdd = view.findViewById(R.id.calendarSaturdayAdd);
        sundayAdd = view.findViewById(R.id.calendarSundayAdd);
        mondayRecyclerView = view.findViewById(R.id.calendarMondayRecyclerView);
        tuesdayRecyclerView = view.findViewById(R.id.calendarTuesdayRecyclerView);
        wednesdayRecyclerView = view.findViewById(R.id.calendarWednesdayRecyclerView);
        thursdayRecyclerView = view.findViewById(R.id.calendarThursdayRecyclerView);
        fridayRecyclerView = view.findViewById(R.id.calendarFridayRecyclerView);
        saturdayRecyclerView = view.findViewById(R.id.calendarSaturdayRecyclerView);
        sundayRecyclerView = view.findViewById(R.id.calendarSundayRecyclerView);

        //////lists
        mondayMeals = new ArrayList<>();
        tuesdayMeals = new ArrayList<>();
        wednesdayMeals = new ArrayList<>();
        thursdayMeals = new ArrayList<>();
        fridayMeals = new ArrayList<>();
        saturdayMeals = new ArrayList<>();
        sundayMeals = new ArrayList<>();

        //////adapters


        presenter = PlanFragmentPresenter.getInstance(RepositoryImpl.getInstance(FirebaseHandler.getInstance(), NetworkAPI.getInstance(), DatabaseHandler.getInstance(view.getContext()), SharedPrefHandler.getInstance(view.getContext())),this);

        //customProgressBar.startProgressBar();
        presenter.getAllMealsInPlan(FirebaseHandler.CURRENT_USER_ID);
    }

    void setUp()
    {
        mondayAdapter = new PlanFragmentRecyclerViewAdapter(mondayMeals,this,getContext());
        tuesdayAdapter = new PlanFragmentRecyclerViewAdapter(tuesdayMeals,this,getContext());
        wednesdayAdapter = new PlanFragmentRecyclerViewAdapter(wednesdayMeals,this,getContext());
        thursdayAdapter = new PlanFragmentRecyclerViewAdapter(thursdayMeals,this,getContext());
        fridayAdapter = new PlanFragmentRecyclerViewAdapter(fridayMeals,this,getContext());
        saturdayAdapter = new PlanFragmentRecyclerViewAdapter(saturdayMeals,this,getContext());
        sundayAdapter = new PlanFragmentRecyclerViewAdapter(sundayMeals,this,getContext());


        mondayRecyclerView.setAdapter(mondayAdapter);
        tuesdayRecyclerView.setAdapter(tuesdayAdapter);
        wednesdayRecyclerView.setAdapter(wednesdayAdapter);
        thursdayRecyclerView.setAdapter(thursdayAdapter);
        fridayRecyclerView.setAdapter(fridayAdapter);
        saturdayRecyclerView.setAdapter(saturdayAdapter);
        sundayRecyclerView.setAdapter(sundayAdapter);
    }


    private void clearAllLists()
    {
        mondayMeals.clear();
        tuesdayMeals.clear();
        wednesdayMeals.clear();
        thursdayMeals.clear();
        fridayMeals.clear();
        saturdayMeals.clear();
        sundayMeals.clear();
    }
    private void showRecyclerViewBaseOnItems()
    {
        //setUp();
        if(!mondayMeals.isEmpty())
        {
            mondayRecyclerView.setVisibility(View.VISIBLE);
        }
        if(!tuesdayMeals.isEmpty())
        {
            tuesdayRecyclerView.setVisibility(View.VISIBLE);
        }
        if(!wednesdayMeals.isEmpty())
        {
            wednesdayRecyclerView.setVisibility(View.VISIBLE);
        }
        if(!thursdayMeals.isEmpty())
        {
            thursdayRecyclerView.setVisibility(View.VISIBLE);
        }
        if(!fridayMeals.isEmpty())
        {
            fridayRecyclerView.setVisibility(View.VISIBLE);
        }
        if(!saturdayMeals.isEmpty())
        {
            saturdayRecyclerView.setVisibility(View.VISIBLE);
        }
        if(!sundayMeals.isEmpty())
        {
            sundayRecyclerView.setVisibility(View.VISIBLE);
        }

        mondayAdapter.setMeals(mondayMeals);
        tuesdayAdapter.setMeals(tuesdayMeals);
        wednesdayAdapter.setMeals(wednesdayMeals);
        thursdayAdapter.setMeals(thursdayMeals);
        fridayAdapter.setMeals(fridayMeals);
        saturdayAdapter.setMeals(saturdayMeals);
        sundayAdapter.setMeals(sundayMeals);
    }




    @Override
    public void onGetAllMealsSuccess(List<PlanMeal> meals) {

        clearAllLists();

        for(PlanMeal meal : meals)
        {
            switch (meal.day)
            {
                case MONDAY:
                    mondayMeals.add(meal);
                    break;
                case TUESDAY:
                    tuesdayMeals.add(meal);
                    break;
                case WEDNESDAY:
                    wednesdayMeals.add(meal);
                    break;
                case THURSDAY:
                    thursdayMeals.add(meal);
                    break;
                case FRIDAY:
                    fridayMeals.add(meal);
                    break;
                case SATURDAY:
                    saturdayMeals.add(meal);
                    break;
                case SUNDAY:
                    sundayMeals.add(meal);
            }
        }

        showRecyclerViewBaseOnItems();

    }

    @Override
    public void onDeleteMealSuccess() {
        //Snackbar.make(findViewById(android.R.id.content), "Meal Deleted.", Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void onFailure(String message) {
        Log.d("Kerolos", "onFailure: " + message);
        //customProgressBar.dismissProgressBar();
    }

    PlanMeal currentPlanMeal;
    @Override
    public void onMealClicked(PlanMeal meal) {
        if(ConnectivityObserver.InternetStatus == ConnectivityObserver.Status.Available)
        {
            customProgressBar.startProgressBar();
            currentPlanMeal = meal;
            presenter.isFavourite(meal.mealId);
        }else
        {
            Toast.makeText(getContext(), "Check your internet connection", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onGetIsFavouriteSuccess(boolean isFavourite) {

        Navigator.navigateWithExtra(getContext(), MealActivity.class, HomeFragment.MEAL_KEY,currentPlanMeal.mealId,HomeFragment.IS_FAVOURITE_KEY,isFavourite);
    }

    @Override
    public void onDeleteButtonClicked(PlanMeal meal) {
        presenter.deleteMealFromPlan(meal);
    }

    @Override
    public void onPause() {
        super.onPause();
        customProgressBar.dismissProgressBar();
    }

    @Override
    public void onStop() {
        super.onStop();
        presenter.clearCompositeDisposable();
    }
}