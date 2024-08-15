package com.example.warmmeal.fragment_home.view.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.warmmeal.R;
import com.example.warmmeal.fragment_home.view.OnNestedRecyclerViewItemClickedListener;
import com.example.warmmeal.model.pojo.Category;
import com.example.warmmeal.model.pojo.Meal;

import java.util.ArrayList;

public class CountryRecyclerViewAdapter extends RecyclerView.Adapter<CountryRecyclerViewAdapter.ViewHolder> {

    ArrayList<Meal> meals;
    Context context;
    OnNestedRecyclerViewItemClickedListener listener;


    CountryRecyclerViewAdapter(Context context, ArrayList<Meal> objects, OnNestedRecyclerViewItemClickedListener listener) {
        this.meals = objects;
        this.context = context;
        this.listener = listener;
    }

    void setData(ArrayList<Meal> objects) {
        meals = objects;
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView categoryImage;
        TextView categoryTitle;

        ViewHolder(View view) {
            super(view);
            categoryImage = view.findViewById(R.id.categoryImage);
            categoryTitle = view.findViewById(R.id.categoryName);
        }
    }

    @Override
    public CountryRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category, parent, false);
        return new CountryRecyclerViewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CountryRecyclerViewAdapter.ViewHolder holder, int position) {
        Glide.with(context).load(meals.get(position).getStrMealThumb()).placeholder(R.drawable.login_ways).into(holder.categoryImage);
        holder.categoryTitle.setText(meals.get(position).getStrCategory());
        holder.categoryImage.setOnClickListener((e)->{
            listener.onCategoryClicked(meals.get(position).getStrCategory());
        });
    }






    @Override
    public int getItemCount() {
        return meals.size();
    }


}
