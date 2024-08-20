package com.example.warmmeal.fragment_search.view;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.warmmeal.R;
import com.example.warmmeal.fragment_home.view.HomeFragment;
import com.example.warmmeal.fragment_search.presenter.SearchPresenter;
import com.example.warmmeal.meal_screen.view.MealActivity;
import com.example.warmmeal.model.database.DatabaseHandler;
import com.example.warmmeal.model.firebase.FirebaseHandler;
import com.example.warmmeal.model.network.NetworkAPI;
import com.example.warmmeal.model.pojo.Ingredient;
import com.example.warmmeal.model.pojo.Ingredients;
import com.example.warmmeal.model.pojo.Meal;
import com.example.warmmeal.model.pojo.Meals;
import com.example.warmmeal.model.repository.RepositoryImpl;
import com.example.warmmeal.model.shared_pref.SharedPrefHandler;
import com.example.warmmeal.model.util.CustomProgressBar;
import com.example.warmmeal.model.util.Navigator;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.util.ArrayList;

public class SearchFragment extends Fragment implements OnSearchResponse ,OnSearchRecyclerViewItemClicked,OnGetListsResponse{


    EditText searchEditText;
    RecyclerView recyclerView;
    ChipGroup chipGroup;
    Chip chipMealName;
    Chip chipCategory;
    Chip chipCountry;
    Chip chipIngredient;
    TextView noResult;
    ImageView searchIcon;

    ////presenter
    SearchPresenter presenter;

    SearchRecyclerViewAdapter mAdapter;

    Context context;

    //ingredients
    ArrayList<String> ingredients;

    //progressBar
    CustomProgressBar customProgressBar;


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
        customProgressBar = new CustomProgressBar(getActivity());
        ingredients = new ArrayList<>();
        context = view.getContext();
        recyclerView = view.findViewById(R.id.searchRecyclerView);
        chipGroup = view.findViewById(R.id.chipGroup);
        chipMealName = view.findViewById(R.id.chipMealName);
        chipCategory = view.findViewById(R.id.chipCategory);
        chipCountry = view.findViewById(R.id.chipCountry);
        chipIngredient = view.findViewById(R.id.chipIngredient);
        noResult = view.findViewById(R.id.searchNoResult);
        searchEditText = view.findViewById(R.id.searchEditText);
        searchIcon = view.findViewById(R.id.searchIcon);

        ////
        presenter = SearchPresenter.getInstance(RepositoryImpl.getInstance(FirebaseHandler.getInstance(), NetworkAPI.getInstance(), DatabaseHandler.getInstance(view.getContext()), SharedPrefHandler.getInstance(context)));
        presenter.getIngredients(this, ListPurpose.INGREDIENTS);



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
                doActionOnSearchBegin();
                return true;
            }
            return false;
        });

        searchEditText.setOnClickListener((v)->{
            doActionOnSearchBegin();
        });
        //searchEditText.setOnIc
    }



    @Override
    public void onSuccess(Meals meals) {
        if(meals.getMeals() != null && !meals.getMeals().isEmpty())
        {
            recyclerView.setVisibility(View.VISIBLE);
            noResult.setVisibility(View.GONE);
            mAdapter.setMeals((ArrayList<Meal>) meals.getMeals());
        }
        else
        {
            recyclerView.setVisibility(View.GONE);
            noResult.setVisibility(View.VISIBLE);
        }
    }

    ///////////
    @Override
    public void onGetIngredientsSuccess(Ingredients ingredients) {
        this.ingredients.clear();
        for(Ingredient ingredient : ingredients.getMeals())
        {
            this.ingredients.add(ingredient.getStrIngredient());
        }
    }

    @Override
    public void onFailure(String message) {
        Log.d("Kerolos", "onFailure: " + message);
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }


    void doActionOnSearchBegin()
    {
        if(!searchEditText.getText().toString().isEmpty())
        {
            if(chipMealName.isChecked())
            {
                presenter.getMealByName(searchEditText.getText().toString(), this);
            }
            else if(chipCategory.isChecked())
            {
                String newCategory = parseToLegalCategoryName(searchEditText.getText().toString());
                if(newCategory!= null)
                {
                    presenter.getMealByCategory(newCategory, this);
                }else
                {
                    Toast.makeText(context, "Illegal Category Name.", Toast.LENGTH_SHORT).show();
                }
            }
            else if (chipCountry.isChecked())
            {
                String newName = parseToLegalCountryName(searchEditText.getText().toString());
                if(newName!= null)
                {
                    presenter.getMealByCountry(newName, this);
                }else
                {
                    Toast.makeText(context, "Illegal Country Name.", Toast.LENGTH_SHORT).show();
                }
            }
            else if (chipIngredient.isChecked())
            {
                String newIngredient = parseToLegalIngredientName(searchEditText.getText().toString());
                if(newIngredient!= null)
                {
                    presenter.getMealByMainIngredient(newIngredient, this);
                }else
                {
                    Toast.makeText(context, "Illegal Ingredient Name.", Toast.LENGTH_SHORT).show();
                }
            }
        }

    }



    String parseToLegalCountryName(String country)
    {
        ArrayList<String> countries = new ArrayList<>();
        countries.add("American");
        countries.add( "British");
        countries.add( "Canadian");
        countries.add( "Chinese");
        countries.add( "Croatian");
        countries.add( "Dutch");
        countries.add( "Egyptian");
        countries.add( "Filipino");
        countries.add( "French");
        countries.add( "Greek");
        countries.add( "Indian");
        countries.add( "Irish");
        countries.add( "Italian");
        countries.add( "Jamaican");
        countries.add( "Japanese");
        countries.add( "Kenyan");
        countries.add( "Malaysian");
        countries.add( "Mexican");
        countries.add( "Moroccan");
        countries.add( "Polish");
        countries.add( "Portuguese");
        countries.add( "Russian");
        countries.add( "Spanish");
        countries.add( "Thai");
        countries.add( "Tunisian");
        countries.add( "Turkish");
        countries.add( "Ukrainian");
        countries.add( "Vietnamese");

        for(String c : countries)
        {
            String temp = c.toLowerCase();
            if(temp.equals(country.toLowerCase()) || temp.contains(country.toLowerCase()))
            {
                return c;
            }
        }
        return null;
    }

    String parseToLegalCategoryName(String category)
    {
        ArrayList<String> categories = new ArrayList<>();
        categories.add("Beef");
        categories.add( "Breakfast");
        categories.add( "Chicken");
        categories.add( "Dessert");
        categories.add( "Goat");
        categories.add( "Lamb");
        categories.add( "Miscellaneous");
        categories.add( "Pasta");
        categories.add( "Pork");
        categories.add( "Seafood");
        categories.add( "Side");
        categories.add( "Starter");
        categories.add( "Vegan");
        categories.add( "Vegetarian");
        for(String c : categories)
        {
            String temp = c.toLowerCase();
            //Log.d("Kerolos", "parseToLegalCategoryName: " + c + " " + category + " " + temp);
            if(temp.equals(category.toLowerCase()) || temp.contains(category.toLowerCase()))
            {
                return c;
            }
        }
        return null;
    }

    String parseToLegalIngredientName(String ingredient)
    {
        for(String c : ingredients)
        {
            String temp = c.toLowerCase();
            if(temp.equals(ingredient.toLowerCase()) || temp.contains(ingredient.toLowerCase()))
            {
                return c;
            }
        }
        return null;
    }

    ///

    @Override
    public void onMealClicked(Meal meal) {

        customProgressBar.startProgressBar();
        Navigator.navigateWithExtra(context, MealActivity.class, HomeFragment.MEAL_KEY,meal.getIdMeal());
    }

    @Override
    public void onAddToFavouriteClicked(Meal meal) {

    }

    @Override
    public void onStop() {
        super.onStop();
        NetworkAPI.getInstance().clearDisposable();
    }

    @Override
    public void onPause() {
        super.onPause();
        if(customProgressBar.isShowing())
            customProgressBar.dismissProgressBar();
    }
}