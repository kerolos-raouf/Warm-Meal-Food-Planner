package com.example.warmmeal.fragment_home.view.contracts;

import com.example.warmmeal.model.pojo.Categories;
import com.example.warmmeal.model.pojo.Meals;

public interface OnNetworkCallResponse  {

    void onGetMealByCharacterForMoreYouLikeSuccess(Meals meals);
    void onGetMealByCharacterForInspirationSuccess(Meals meals);
    void onGetCategorySuccess(Categories categories);
    void onGetAllCountriesSuccess(Meals meals);
    void onFailure(String message);


}
