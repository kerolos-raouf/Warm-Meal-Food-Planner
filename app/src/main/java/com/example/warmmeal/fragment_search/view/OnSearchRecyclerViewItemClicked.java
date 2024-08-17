package com.example.warmmeal.fragment_search.view;

import com.example.warmmeal.model.pojo.Meal;

public interface OnSearchRecyclerViewItemClicked {

    void onMealClicked(Meal meal);
    void onAddToFavouriteClicked(Meal meal);

}
