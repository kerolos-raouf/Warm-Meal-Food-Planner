package com.example.warmmeal.fragment_search.view;

import com.example.warmmeal.model.pojo.Categories;
import com.example.warmmeal.model.pojo.Meals;

public interface OnNetworkCallResponse  {

    void onGetMealByCharacterSuccess(Meals meals);
    void onGetCategorySuccess(Categories categories);
    void onGetAllCountriesSuccess(Meals meals);
    void onFailure(String message);


}
