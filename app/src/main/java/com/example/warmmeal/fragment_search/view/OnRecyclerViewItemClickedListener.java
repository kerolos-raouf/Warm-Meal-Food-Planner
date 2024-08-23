package com.example.warmmeal.fragment_search.view;

import com.example.warmmeal.model.pojo.Meal;

public interface OnRecyclerViewItemClickedListener {

    void onMealClicked(Meal meal);
    void onButtonClicked(Meal meal);

}
