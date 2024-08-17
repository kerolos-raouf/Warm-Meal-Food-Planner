package com.example.warmmeal.meal_screen.view;

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
import com.example.warmmeal.model.pojo.MealIngredientAndMeasure;

import java.util.ArrayList;

public class IngredientsRecyclerViewAdapter extends RecyclerView.Adapter<IngredientsRecyclerViewAdapter.ViewHolder> {

    ArrayList<MealIngredientAndMeasure> mealIngredientAndMeasures;
    Context context;
    public IngredientsRecyclerViewAdapter(ArrayList<MealIngredientAndMeasure> mealIngredientAndMeasures, Context context) {
        this.mealIngredientAndMeasures = mealIngredientAndMeasures;
        this.context = context;
    }

    void setData(ArrayList<MealIngredientAndMeasure> mealIngredientAndMeasures){
        this.mealIngredientAndMeasures = mealIngredientAndMeasures;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ingredient_details, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MealIngredientAndMeasure currentItem = mealIngredientAndMeasures.get(position);
        holder.ingredientName.setText(currentItem.getIngredient());
        holder.ingredientMeasure.setText(currentItem.getMeasure());
        Glide.with(context).load("https://www.themealdb.com/images/ingredients/" + currentItem.getIngredient() + "-Small.png").placeholder(R.drawable.lime).into(holder.ingredientImage);
    }

    @Override
    public int getItemCount() {
        return mealIngredientAndMeasures.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView ingredientName;
        TextView ingredientMeasure;
        ImageView ingredientImage;
        public ViewHolder(View view) {
            super(view);
            ingredientName = view.findViewById(R.id.itemIngredientName);
            ingredientMeasure = view.findViewById(R.id.itemIngredientMeasure);
            ingredientImage = view.findViewById(R.id.itemIngredientImage);
        }
    }
}
