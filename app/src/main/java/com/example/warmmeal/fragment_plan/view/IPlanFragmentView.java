package com.example.warmmeal.fragment_plan.view;

import com.example.warmmeal.model.pojo.PlanMeal;

import java.util.List;

public interface IPlanFragmentView
{
    void onGetAllMealsSuccess(List<PlanMeal> meals);

    void onDeleteMealSuccess();

    void onFailure(String message);
}
