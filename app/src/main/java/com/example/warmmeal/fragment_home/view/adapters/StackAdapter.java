package com.example.warmmeal.fragment_home.view.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.warmmeal.model.pojo.Meal;

import java.util.ArrayList;

public class StackAdapter extends ArrayAdapter {


    ArrayList<Meal> meals;
    int itemLayout;
    Context context;

    public StackAdapter(@NonNull Context context, int resource, ArrayList<Meal> objects) {
        super(context, resource, objects);
        this.itemLayout = resource;
        this.context = context;
        this.meals = objects;
    }


    @Override
    public int getCount() {
        return meals.size();
    }

    @Nullable
    @Override
    public Object getItem(int position) {
        return meals.get(position);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if(convertView == null){
            convertView = LayoutInflater.from(parent.getContext()).inflate(itemLayout, parent, false);
        }

        return convertView;
    }
}
