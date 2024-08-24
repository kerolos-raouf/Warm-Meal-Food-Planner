package com.example.warmmeal.fragment_search.view;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
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

import com.airbnb.lottie.LottieAnimationView;
import com.example.warmmeal.R;
import com.example.warmmeal.fragment_home.view.HomeFragment;
import com.example.warmmeal.fragment_search.presenter.SearchPresenter;
import com.example.warmmeal.meal_screen.view.MealActivity;
import com.example.warmmeal.model.database.DatabaseHandler;
import com.example.warmmeal.model.firebase.FirebaseHandler;
import com.example.warmmeal.model.internet_connection.ConnectivityObserver;
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
import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class SearchFragment extends Fragment implements OnSearchResponse , OnRecyclerViewItemClickedListener,OnGetListsResponse{


    EditText searchEditText;
    RecyclerView recyclerView;
    ChipGroup chipGroup;
    Chip chipMealName;
    Chip chipCategory;
    Chip chipCountry;
    Chip chipIngredient;
    TextView noResult;
    ImageView searchIcon;
    LottieAnimationView lottieAnimation;

    ////presenter
    SearchPresenter presenter;

    SearchRecyclerViewAdapter mAdapter;

    Context context;

    //ingredients
    ArrayList<String> ingredients;

    //progressBar
    CustomProgressBar customProgressBar;

    CompositeDisposable compositeDisposable;


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
        lottieAnimation = view.findViewById(R.id.searchLottieAnimation);
        compositeDisposable = new CompositeDisposable();
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
        mAdapter = new SearchRecyclerViewAdapter(new ArrayList<>(), this, getContext(),false);
        recyclerView.setAdapter(mAdapter);
    }

    void setUp()
    {
        compositeDisposable.add(Observable.create(emitter -> {
            searchEditText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    emitter.onNext(s.toString());
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
        }).subscribeOn(Schedulers.io())
                .debounce(1000, TimeUnit.MILLISECONDS)
                .distinctUntilChanged()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(searchText -> {doActionOnSearchBegin((String) searchText);}));

        chipMealName.setOnCheckedChangeListener((buttonView, isChecked) -> {
           doActionOnSearchBegin(searchEditText.getText().toString());
        });

        chipCategory.setOnCheckedChangeListener((buttonView, isChecked) -> {
            doActionOnSearchBegin(searchEditText.getText().toString());
        });

        chipCountry.setOnCheckedChangeListener((buttonView, isChecked) -> {
            doActionOnSearchBegin(searchEditText.getText().toString());
        });

        chipIngredient.setOnCheckedChangeListener((buttonView, isChecked) -> {
            doActionOnSearchBegin(searchEditText.getText().toString());
        });

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
        noResult.setVisibility(View.GONE);
        recyclerView.setVisibility(View.GONE);
        lottieAnimation.setVisibility(View.VISIBLE);
    }


    void doActionOnSearchBegin(String searchText)
    {
        if(searchEditText != null && !searchText.trim().isEmpty())
        {
            if(chipMealName.isChecked())
            {
                presenter.getMealByName(searchText, this);
            }
            else if(chipCategory.isChecked())
            {
                String newCategory = parseToLegalCategoryName(searchText);
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
                String newName = parseToLegalCountryName(searchText);
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
                String newIngredient = parseToLegalIngredientName(searchText);
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

        if(ConnectivityObserver.InternetStatus == ConnectivityObserver.Status.Available)
        {
            customProgressBar.startProgressBar();
            Navigator.navigateWithExtra(context, MealActivity.class, HomeFragment.MEAL_KEY,meal.getIdMeal(),HomeFragment.IS_FAVOURITE_KEY,meal.isFavourite());
        }else
        {
            Toast.makeText(context, "Check your internet connection", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onButtonClicked(Meal meal) {

    }

    @Override
    public void onStop() {
        super.onStop();
        NetworkAPI.getInstance().clearDisposable();
        compositeDisposable.clear();
    }

    @Override
    public void onPause() {
        super.onPause();
        customProgressBar.dismissProgressBar();
    }

}