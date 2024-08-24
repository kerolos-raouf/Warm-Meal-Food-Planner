package com.example.warmmeal.meal_screen.view;

import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;

import androidx.appcompat.app.AlertDialog;

import com.example.warmmeal.R;

import java.util.Calendar;
import java.util.Objects;

public class CalendarChooser {

    private final Activity activity;
    private AlertDialog alertDialog;
    private boolean isShowing = false;


    public CalendarChooser(Activity activity)
    {
        this.activity = activity;
    }

    public void startCalendarChooser(CalendarItemClickedListener listener)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);

        LayoutInflater layoutInflater = activity.getLayoutInflater();

        View view = layoutInflater.inflate(R.layout.add_to_phone_calendar_view,null);

        builder.setView(view);
        builder.setCancelable(true);

        Button setMeal = view.findViewById(R.id.addToPhoneSetMeal);
        CalendarView calendarView = view.findViewById(R.id.addToPhoneCalendar);

        setMeal.setOnClickListener(v -> listener.onSetMealClicked());

        calendarView.setOnDateChangeListener((view1, year, month, dayOfMonth) -> {
            Calendar calendar = Calendar.getInstance();
            calendar.set(year,month,dayOfMonth);
            listener.onDateChanged(calendar.getTimeInMillis());
        });



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
