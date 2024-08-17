package com.example.warmmeal.model.util;

import static androidx.core.content.ContextCompat.startActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.example.warmmeal.login_ways.view.LoginWays;

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


    public static void navigateWithStringExtra(Context context, Class cls,String extraKey,String extraValue)
    {
        Intent intent = new Intent(context,cls);
        intent.putExtra(extraKey,extraValue);
        context.startActivity(intent);
    }
}
