package com.example.warmmeal.fragment_home.view.contracts;

import com.example.warmmeal.model.pojo.Meal;

public interface OnNestedRecyclerViewItemClickedListener {

    void onMealClicked(Meal meal);
    void onAddToFavouriteClicked(Meal meal);
    void onCategoryClicked(String category);
    void onCountryClicked(String country);

}
