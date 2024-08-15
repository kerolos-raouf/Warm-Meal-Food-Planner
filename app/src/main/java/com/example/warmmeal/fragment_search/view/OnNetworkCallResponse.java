package com.example.warmmeal.fragment_search.view;

import com.example.warmmeal.model.pojo.Categories;
import com.example.warmmeal.model.pojo.Meals;

public interface OnNetworkCallResponse  {

    void onGetMealByCharacterSuccess(Meals meals);
    void onGetMealByCharacterFailure(String message);
    void onGetCategorySuccess(Categories categories);
    void onGetCategoryFailure(String message);


}
