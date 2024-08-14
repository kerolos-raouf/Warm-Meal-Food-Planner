package com.example.warmmeal.fragment_home.view;

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
import com.example.warmmeal.model.pojo.Meal;

import java.util.ArrayList;

public class CategoryCountryRecyclerViewAdapter extends RecyclerView.Adapter<CategoryCountryRecyclerViewAdapter.ViewHolder> {

    ArrayList<Meal> meals;
    Context context;


    CategoryCountryRecyclerViewAdapter(Context context, ArrayList<Meal> objects) {
        this.meals = objects;
        this.context = context;
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
    public CategoryCountryRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryCountryRecyclerViewAdapter.ViewHolder holder, int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
        Glide.with(context).load(meals.get(position).getStrMealThumb()).into(viewHolder.categoryImage);
        viewHolder.categoryTitle.setText(meals.get(position).getStrCategory());
    }


    @Override
    public int getItemCount() {
        return meals.size();
    }


}
