package com.example.warmmeal.fragment_plan.view;

import com.example.warmmeal.model.pojo.PlanMeal;

public interface OnPlanFragmentRecyclerViewItemClicked {
    void onMealClicked(PlanMeal meal);
    void onDeleteButtonClicked(PlanMeal meal);
}
