package com.example.warmmeal.model.util;

import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;

import androidx.appcompat.app.AlertDialog;

import com.example.warmmeal.R;

import java.util.Objects;

public class CustomProgressBar {

    private final Activity activity;
    private AlertDialog alertDialog;
    private boolean isShowing;

    public CustomProgressBar(Activity activity)
    {
        this.activity = activity;
    }

    public void startProgressBar()
    {
        if(isShowing)
            return;

        AlertDialog.Builder builder = new AlertDialog.Builder(activity);

        LayoutInflater layoutInflater = activity.getLayoutInflater();
        builder.setView(layoutInflater.inflate(R.layout.custom_progress_bar,null));
        builder.setCancelable(false);


        alertDialog = builder.create();
        Objects.requireNonNull(alertDialog.getWindow()).setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        alertDialog.show();
        isShowing = true;
    }

    public void dismissProgressBar()
    {
        if(isShowing)
        {
            alertDialog.dismiss();
            isShowing = false;
        }
    }

    public boolean isShowing()
    {
        return isShowing;
    }

}
