package com.example.warmmeal.model.util;

import static androidx.core.content.ContextCompat.startActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.example.warmmeal.category_country_screen.view.CategoryAndCountryScreen;
import com.example.warmmeal.login_ways.view.LoginWays;
import com.example.warmmeal.meal_screen.view.MealActivity;
import com.example.warmmeal.model.pojo.Meal;

public class Navigator {

    public static void navigate(Context context, Class cls)
    {
        Intent intent = new Intent(context,cls);
        context.startActivity(intent);
    }



    public static void navigateAndClearLast(Context context, Class cls)
    {
        Intent intent = new Intent(context, cls);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);
    }


    public static void navigateWithStringExtra(Context context, Class cls,String extraKey1, String extraValue1,String extraKey2, String extraValue2)
    {
        Intent intent = new Intent(context,cls);
        intent.putExtra(extraKey1,extraValue1);
        intent.putExtra(extraKey2,extraValue2);
        context.startActivity(intent);
    }

    public static void navigateWithStringExtra(Context context, Class cls,String extraKey, String extraValue)
    {
        Intent intent = new Intent(context,cls);
        intent.putExtra(extraKey,extraValue);
        context.startActivity(intent);
    }

    public static void navigateWithMealExtra(Context context, Class cls, String extraKey, Meal extraValue)
    {
        Intent intent = new Intent(context,cls);
        intent.putExtra(extraKey,extraValue);
        context.startActivity(intent);
    }
}
