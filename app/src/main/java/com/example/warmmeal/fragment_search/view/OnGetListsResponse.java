package com.example.warmmeal.fragment_search.view;

import com.example.warmmeal.model.pojo.Ingredients;

public interface OnGetListsResponse {

    void onGetIngredientsSuccess(Ingredients ingredients);
    void onFailure(String message);

}
