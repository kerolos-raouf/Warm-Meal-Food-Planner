package com.example.warmmeal.fragment_plan.view;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.warmmeal.R;
import com.example.warmmeal.model.pojo.PlanMeal;

import java.util.ArrayList;

public class PlanFragmentRecyclerViewAdapter extends RecyclerView.Adapter<PlanFragmentRecyclerViewAdapter.ViewHolder>{

    ArrayList<PlanMeal> meals;
    OnPlanFragmentRecyclerViewItemClicked listener;
    Context context;

    public PlanFragmentRecyclerViewAdapter(ArrayList<PlanMeal> meals, OnPlanFragmentRecyclerViewItemClicked listener, Context context) {
        this.meals = meals;
        this.listener = listener;
        this.context = context;
    }

    public void setMeals(ArrayList<PlanMeal> meals) {
        this.meals = meals;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_plan_fragment, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        PlanMeal currentMeal = meals.get(position);


        Glide.with(context).load(currentMeal.mealImage).placeholder(R.drawable.login_ways).into(holder.mealImage);

        holder.mealName.setText(currentMeal.mealName);
        holder.deleteButton.setOnClickListener(view -> listener.onDeleteButtonClicked(currentMeal));
        holder.mealImage.setOnClickListener(view -> listener.onMealClicked(currentMeal));
    }

    @Override
    public int getItemCount() {
        return meals.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {


        ImageView deleteButton;
        TextView mealName;
        ImageView mealImage;

        public ViewHolder(View view) {
            super(view);
            deleteButton = view.findViewById(R.id.itemPlanDelete);
            mealName = view.findViewById(R.id.itemPlanMealName);
            mealImage = view.findViewById(R.id.itemPlanMealImage);
        }
    }

}
