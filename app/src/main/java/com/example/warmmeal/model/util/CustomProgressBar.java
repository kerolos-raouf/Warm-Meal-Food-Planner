package com.example.warmmeal.model.util;

import android.app.Activity;
import android.view.LayoutInflater;

import androidx.appcompat.app.AlertDialog;

import com.example.warmmeal.R;

public class CustomProgressBar {

    private final Activity activity;
    private AlertDialog alertDialog;

    public CustomProgressBar(Activity activity)
    {
        this.activity = activity;
    }

    public void startProgressBar()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);

        LayoutInflater layoutInflater = activity.getLayoutInflater();
        builder.setView(layoutInflater.inflate(R.layout.custom_progress_bar,null));
        builder.setCancelable(false);

        alertDialog = builder.create();
        alertDialog.show();
    }

    public void dismissProgressBar()
    {
        alertDialog.dismiss();
    }

}
