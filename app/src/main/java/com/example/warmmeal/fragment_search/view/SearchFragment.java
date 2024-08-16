package com.example.warmmeal.fragment_search.view;

import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.warmmeal.R;
import com.example.warmmeal.fragment_search.presenter.SearchPresenter;
import com.example.warmmeal.model.database.DatabaseHandler;
import com.example.warmmeal.model.firebase.FirebaseHandler;
import com.example.warmmeal.model.network.NetworkAPI;
import com.example.warmmeal.model.pojo.Meal;
import com.example.warmmeal.model.pojo.Meals;
import com.example.warmmeal.model.repository.RepositoryImpl;
import com.example.warmmeal.model.shared_pref.SharedPrefHandler;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.util.ArrayList;

public class SearchFragment extends Fragment implements OnSearchResponse ,OnSearchRecyclerViewItemClicked{


    EditText searchEditText;
    RecyclerView recyclerView;
    ChipGroup chipGroup;
    Chip chipMealName;
    Chip chipCategory;
    Chip chipCountry;
    Chip chipIngredient;
    TextView noResult;

    ////presenter
    SearchPresenter presenter;

    SearchRecyclerViewAdapter mAdapter;

    Context context;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_search, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view);
        setUp();

    }

    void init(View view)
    {
        context = view.getContext();
        recyclerView = view.findViewById(R.id.searchRecyclerView);
        chipGroup = view.findViewById(R.id.chipGroup);
        chipMealName = view.findViewById(R.id.chipMealName);
        chipCategory = view.findViewById(R.id.chipCategory);
        chipCountry = view.findViewById(R.id.chipCountry);
        chipIngredient = view.findViewById(R.id.chipIngredient);
        noResult = view.findViewById(R.id.searchNoResult);
        searchEditText = view.findViewById(R.id.searchEditText);

        presenter = SearchPresenter.getInstance(RepositoryImpl.getInstance(FirebaseHandler.getInstance(), NetworkAPI.getInstance(), DatabaseHandler.getInstance(view.getContext()), SharedPrefHandler.getInstance()));

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        mAdapter = new SearchRecyclerViewAdapter(new ArrayList<>(), this, getContext());
        recyclerView.setAdapter(mAdapter);
    }

    void setUp()
    {
        searchEditText.setOnKeyListener((v, keyCode, event)->{
            if(event.getAction() == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER)
            {
                if(!searchEditText.getText().toString().isEmpty())
                {
                    if(chipMealName.isChecked())
                    {
                        presenter.getMealByName(searchEditText.getText().toString(), this);
                    }
                    else if(chipCategory.isChecked())
                    {
                        presenter.getMealByCategory(searchEditText.getText().toString(), this);
                    }
                    else if (chipCountry.isChecked())
                    {
                        presenter.getMealByCountry(searchEditText.getText().toString(), this);
                    }
                    else if (chipIngredient.isChecked())
                    {
                        presenter.getMealByMainIngredient(searchEditText.getText().toString(), this);
                    }
                }
                return true;
            }
            return false;
        });
    }


    @Override
    public void onSuccess(Meals meals) {
        if(!meals.getMeals().isEmpty())
        {
            recyclerView.setVisibility(View.VISIBLE);
            noResult.setVisibility(View.GONE);
            mAdapter.setMeals((ArrayList<Meal>) meals.getMeals());
            //recyclerView.setAdapter(new SearchAdapter(meals.getMeals(),getContext()));
        }
    }

    @Override
    public void onFailure(String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
}