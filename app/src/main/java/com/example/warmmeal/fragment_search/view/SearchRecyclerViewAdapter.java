package com.example.warmmeal.fragment_search.view;

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

public class SearchRecyclerViewAdapter extends RecyclerView.Adapter<SearchRecyclerViewAdapter.ViewHolder> {


    ArrayList<Meal> meals;
    OnRecyclerViewItemClickedListener listener;
    Context context;
    boolean showFavButton;

    public SearchRecyclerViewAdapter(ArrayList<Meal> meals, OnRecyclerViewItemClickedListener listener, Context context, boolean showFavButton ) {
        this.meals = meals;
        this.listener = listener;
        this.context = context;
        this.showFavButton = showFavButton;
    }

    public void setMeals(ArrayList<Meal> meals)
    {
        this.meals = meals;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_more_you_might_like, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.mealName.setText(meals.get(position).getStrMeal());
        Glide.with(context).load(meals.get(position).getStrMealThumb()).placeholder(R.drawable.login_ways).into(holder.mealImage);

        holder.mealImage.setOnClickListener((v) -> {
            listener.onMealClicked(meals.get(position));
        });

        if(!showFavButton)
        {
            holder.addToFavButton.setVisibility(View.GONE);
            holder.favShadow.setVisibility(View.GONE);
        }

        holder.addToFavButton.setOnClickListener((v) -> {
            if(meals.get(position).isFavourite())
            {
                holder.addToFavButton.setImageResource(R.drawable.icon_favourite_border);
                meals.get(position).setFavourite(false);
            }
            else
            {
                holder.addToFavButton.setImageResource(R.drawable.icon_favourite_item);
                meals.get(position).setFavourite(true);
            }
            listener.onButtonClicked(meals.get(position));
        });

        if(meals.get(position).isFavourite())
        {
            holder.addToFavButton.setImageResource(R.drawable.icon_favourite_item);
        }
        else
        {
            holder.addToFavButton.setImageResource(R.drawable.icon_favourite_border);
        }
    }

    @Override
    public int getItemCount() {
        return meals.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        ImageView addToFavButton;
        TextView mealName;
        ImageView mealImage;
        ImageView favShadow;

        public ViewHolder(View view) {
            super(view);

            addToFavButton = view.findViewById(R.id.itemYouMightLikeViewMealAdd);
            mealName = view.findViewById(R.id.itemYouMightLikeViewMealName);
            mealImage = view.findViewById(R.id.itemYouMightLikeViewMealImage);
            favShadow = view.findViewById(R.id.itemYouMightLikeViewShadow);
        }
    }

}
