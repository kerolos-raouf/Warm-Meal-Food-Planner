package com.example.warmmeal.fragment_home.view.adapters;

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
import com.example.warmmeal.fragment_home.view.contracts.OnNestedRecyclerViewItemClickedListener;
import com.example.warmmeal.model.firebase.FirebaseHandler;
import com.example.warmmeal.model.pojo.Meal;

import java.util.ArrayList;

public class MealMightLikeRecyclerViewAdapter extends RecyclerView.Adapter<MealMightLikeRecyclerViewAdapter.ViewHolder> {

    ArrayList<Meal> meals;
    Context context;
    OnNestedRecyclerViewItemClickedListener listener;


    MealMightLikeRecyclerViewAdapter(Context context, ArrayList<Meal> objects, OnNestedRecyclerViewItemClickedListener listener) {
        this.meals = objects;
        this.context = context;
        this.listener = listener;
    }


    void setData(ArrayList<Meal> objects) {
        meals = objects;
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView mealImage;
        TextView mealName;
        ImageView addToFavourite;

        ViewHolder(View view) {
            super(view);
            mealImage = view.findViewById(R.id.itemSmallMealImage);
            mealName = view.findViewById(R.id.itemSmallMealName);
            addToFavourite = view.findViewById(R.id.itemSmallAddToFavourite);
        }
    }

    @Override
    public MealMightLikeRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_small_might_you_like, parent, false);
        return new MealMightLikeRecyclerViewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MealMightLikeRecyclerViewAdapter.ViewHolder holder, int position) {

        Meal currentMeal = meals.get(position);
        if(meals.get(position).isFavourite())
        {
            holder.addToFavourite.setImageResource(R.drawable.icon_favourite_item);
        }
        else
        {
            holder.addToFavourite.setImageResource(R.drawable.icon_favourite_border);
        }

        Glide.with(context).load(currentMeal.getStrMealThumb()).into(holder.mealImage);
        holder.mealName.setText(currentMeal.getStrMeal());


        holder.mealImage.setOnClickListener((e)->{
            listener.onMealClicked(currentMeal);
        });


        holder.addToFavourite.setOnClickListener((e)->{
            if(!meals.get(position).isFavourite() && FirebaseHandler.CURRENT_USER_ID != null)
            {
                holder.addToFavourite.setImageResource(R.drawable.icon_favourite_item);
                meals.get(position).setFavourite(true);
            }
            else
            {
                holder.addToFavourite.setImageResource(R.drawable.icon_favourite_border);
                meals.get(position).setFavourite(false);
            }
            listener.onAddToFavouriteClicked(currentMeal);
        });


        //holder.dailyAdd.setText(meals.get(position).get());
    }


    @Override
    public int getItemCount() {
        return meals.size();
    }


}
