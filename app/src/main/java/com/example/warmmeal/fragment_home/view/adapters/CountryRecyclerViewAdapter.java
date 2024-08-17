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
        ImageView countryImage;
        TextView countryTitle;

        ViewHolder(View view) {
            super(view);
            countryImage = view.findViewById(R.id.categoryImage);
            countryTitle = view.findViewById(R.id.categoryName);
        }
    }

    @Override
    public CountryRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category, parent, false);
        return new CountryRecyclerViewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CountryRecyclerViewAdapter.ViewHolder holder, int position) {
        Glide.with(context).load(getFlagResId(meals.get(position).getStrArea())).placeholder(R.drawable.unknown).into(holder.countryImage);
        holder.countryTitle.setText(meals.get(position).getStrArea());
        holder.countryImage.setOnClickListener((e)->{
            listener.onCountryClicked(meals.get(position).getStrArea());
        });
    }


    @Override
    public int getItemCount() {
        return meals.size();
    }

    int getFlagResId(String countryName)
    {
        switch (countryName)
        {
            case "American":
                return R.drawable.america;
            case "British":
                return R.drawable.british;
            case "Canadian":
                return R.drawable.canada;
            case "Chinese":
                return R.drawable.china;
            case "Croatian":
                return R.drawable.croatian;
            case "Dutch":
                return R.drawable.dutch;
            case "Egyptian":
                return R.drawable.egypt;
            case "Filipino":
                return R.drawable.malaysian;
            case "French":
                return R.drawable.french;
            case "Greek":
                return R.drawable.greek;
            case "Indian":
                return R.drawable.indian;
            case "Irish":
                return R.drawable.ireland;
            case "Italian":
                return R.drawable.italian;
            case "Jamaican":
                return R.drawable.jamaican;
            case "Japanese":
                return R.drawable.japan;
            case "Kenyan":
                return R.drawable.kenya;
            case "Malaysian":
                return R.drawable.malaysian;
            case "Mexican":
                return R.drawable.mexico;
            case "Moroccan":
                return R.drawable.moroco;
            case "Polish":
                return R.drawable.poland;
            case "Portuguese":
                return R.drawable.portug;
            case "Russian":
                return R.drawable.russian;
            case "Spanish":
                return R.drawable.spani;
            case "Thai":
                return R.drawable.thia;
            case "Tunisian":
                return R.drawable.tunisian;
            case "Turkish":
                return R.drawable.turcia;
            case "Ukrainian":
                return R.drawable.dutch;
            case "Vietnamese":
                return R.drawable.vietname;
            default:
                return R.drawable.unknown;
        }
    }

}
