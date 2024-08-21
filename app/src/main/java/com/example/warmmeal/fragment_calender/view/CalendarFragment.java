package com.example.warmmeal.fragment_calender.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.warmmeal.R;

public class CalendarFragment extends Fragment {


    Button mondayAdd,tuesdayAdd,wednesdayAdd,thursdayAdd,fridayAdd,saturdayAdd,sundayAdd;
    RecyclerView mondayRecyclerView,tuesdayRecyclerView,wednesdayRecyclerView,thursdayRecyclerView,fridayRecyclerView,saturdayRecyclerView,sundayRecyclerView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_calendar, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view);
        setUp();
    }

    void init(View view)
    {
        mondayAdd = view.findViewById(R.id.calendarMondayAdd);
        tuesdayAdd = view.findViewById(R.id.calendarTuesdayAdd);
        wednesdayAdd = view.findViewById(R.id.calendarWednesdayAdd);
        thursdayAdd = view.findViewById(R.id.calendarThursdayAdd);
        fridayAdd = view.findViewById(R.id.calendarFridayAdd);
        saturdayAdd = view.findViewById(R.id.calendarSaturdayAdd);
        sundayAdd = view.findViewById(R.id.calendarSundayAdd);
        mondayRecyclerView = view.findViewById(R.id.calendarMondayRecyclerView);
        tuesdayRecyclerView = view.findViewById(R.id.calendarTuesdayRecyclerView);
        wednesdayRecyclerView = view.findViewById(R.id.calendarWednesdayRecyclerView);
        thursdayRecyclerView = view.findViewById(R.id.calendarThursdayRecyclerView);
        fridayRecyclerView = view.findViewById(R.id.calendarFridayRecyclerView);
        saturdayRecyclerView = view.findViewById(R.id.calendarSaturdayRecyclerView);
        sundayRecyclerView = view.findViewById(R.id.calendarSundayRecyclerView);


    }

    void setUp()
    {

    }
}