package com.example.warmmeal.fragment_search.view;

import com.example.warmmeal.model.pojo.Meal;
import com.example.warmmeal.model.pojo.Meals;

import java.util.ArrayList;

public interface OnSearchResponse {

    void onSuccess(Meals meals);
    void onFailure(String message);

}
