package com.example.warmmeal.fragment_home.view;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.warmmeal.R;
import com.example.warmmeal.model.pojo.Meal;

import java.util.ArrayList;

public class DailyInspirationRecyclerViewAdapter extends RecyclerView.Adapter<DailyInspirationRecyclerViewAdapter.ViewHolder> {

    ArrayList<Meal> meals;
    Context context;


    DailyInspirationRecyclerViewAdapter(Context context, ArrayList<Meal> objects) {
        this.meals = objects;
        this.context = context;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView dailyMealImage;
        TextView dailyMealName;
        Button dailyAdd;

        ViewHolder(View view) {
            super(view);
            dailyMealImage = view.findViewById(R.id.stackViewMealImage);
            dailyMealName = view.findViewById(R.id.stackViewMealName);
            dailyAdd = view.findViewById(R.id.stackViewMealAdd);
        }
    }

    @Override
    public DailyInspirationRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_stack_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DailyInspirationRecyclerViewAdapter.ViewHolder holder, int position) {

        Glide.with(context).load(meals.get(position).getStrMealThumb()).into(holder.dailyMealImage);

        holder.dailyMealName.setText(meals.get(position).getStrMeal());
        //holder.dailyAdd.setText(meals.get(position).get());
    }


    @Override
    public int getItemCount() {
        return meals.size();
    }


}
