package com.example.warmmeal.meal_screen.view;

import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AlertDialog;

import com.example.warmmeal.R;

import java.util.Objects;

public class DayChooser {

    private final Activity activity;
    private AlertDialog alertDialog;
    private boolean isShowing = false;


    public DayChooser(Activity activity)
    {
        this.activity = activity;
    }

    public void startDayChooser(DayChooserItemOnClickListener listener)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);

        LayoutInflater layoutInflater = activity.getLayoutInflater();

        View view = layoutInflater.inflate(R.layout.day_chooser,null);

        builder.setView(view);
        builder.setCancelable(true);

        Button monday = view.findViewById(R.id.dayChooserMonday);
        Button tuesday = view.findViewById(R.id.addToPhoneSetMeal);
        Button wednesday = view.findViewById(R.id.dayChooserWednesday);
        Button thursday = view.findViewById(R.id.dayChooserThursday);
        Button friday = view.findViewById(R.id.dayChooserFriday);
        Button saturday = view.findViewById(R.id.dayChooserSaturday);
        Button sunday = view.findViewById(R.id.dayChooserSunday);


        monday.setOnClickListener(v -> listener.onMondayClicked());
        tuesday.setOnClickListener(v -> listener.onTuesdayClicked());
        wednesday.setOnClickListener(v -> listener.onWednesdayClicked());
        thursday.setOnClickListener(v -> listener.onThursdayClicked());
        friday.setOnClickListener(v -> listener.onFridayClicked());
        saturday.setOnClickListener(v -> listener.onSaturdayClicked());
        sunday.setOnClickListener(v -> listener.onSundayClicked());


        alertDialog = builder.create();
        Objects.requireNonNull(alertDialog.getWindow()).setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        alertDialog.show();
        isShowing = true;
    }

    public void dismiss()
    {
        if(isShowing)
        {
            alertDialog.dismiss();
            isShowing = false;
        }
    }
}
