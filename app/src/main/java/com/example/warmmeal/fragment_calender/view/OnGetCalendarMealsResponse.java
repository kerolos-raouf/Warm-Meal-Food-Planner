package com.example.warmmeal.fragment_calender.view;

import com.example.warmmeal.model.pojo.CalenderMeal;

import java.util.List;

public interface OnGetCalendarMealsResponse {

    void onGetCalendarMealsSuccess(List<CalenderMeal> meals);
    void onGetCalendarMealsFailure(String message);

}
