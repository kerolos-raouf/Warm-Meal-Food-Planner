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
import com.example.warmmeal.fragment_home.view.contracts.OnNestedRecyclerViewItemClickedListener;
import com.example.warmmeal.model.pojo.Meal;

import java.util.ArrayList;

public class CategoryRecyclerViewAdapter extends RecyclerView.Adapter<CategoryRecyclerViewAdapter.ViewHolder> {

    ArrayList<Meal> meals;
    Context context;
    OnNestedRecyclerViewItemClickedListener listener;


    CategoryRecyclerViewAdapter(Context context, ArrayList<Meal> objects, OnNestedRecyclerViewItemClickedListener listener) {
        this.meals = objects;
        this.context = context;
        this.listener = listener;
    }

    void setData(ArrayList<Meal> objects) {
        this.meals = objects;
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
    public CategoryRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryRecyclerViewAdapter.ViewHolder holder, int position) {
        holder.categoryTitle.setText(meals.get(position).getStrCategory());
        Glide.with(context).load(meals.get(position).getStrCategoryThumb()).placeholder(R.drawable.login_ways).into(holder.categoryImage);
        holder.categoryImage.setOnClickListener((e)->{
            listener.onCategoryClicked(meals.get(position).getStrCategory());
        });
    }


    @Override
    public int getItemCount() {
        return meals.size();
    }


}
