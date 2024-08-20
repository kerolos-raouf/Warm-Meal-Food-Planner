package com.example.warmmeal.fragment_home.view.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.warmmeal.R;
import com.example.warmmeal.fragment_home.view.HomeFragmentItem;
import com.example.warmmeal.fragment_home.view.contracts.OnNestedRecyclerViewItemClickedListener;

import com.example.warmmeal.fragment_search.view.SearchRecyclerViewAdapter;
import com.example.warmmeal.model.pojo.Meal;

import java.util.ArrayList;

public class HomeRecyclerViewAdapter extends RecyclerView.Adapter<HomeRecyclerViewAdapter.BaseViewHolder> {


    static final int CATEGORY = 0;
    static final int COUNTRY = 1;
    static final int MEALS_YOU_MIGHT_LIKE = 2;
    static final int DAILY_INSPIRATION = 3;
    static final int HEADER_TEXT = 4;



    private ArrayList<HomeFragmentItem<Object>> items;
    Context context;
    OnNestedRecyclerViewItemClickedListener listener;

    public HomeRecyclerViewAdapter(Context context, ArrayList<HomeFragmentItem<Object>> items, OnNestedRecyclerViewItemClickedListener listener) {
        this.items = items;
        this.context = context;
        this.listener = listener;
    }

    public void setData(ArrayList<HomeFragmentItem<Object>> objects) {
        items = objects;
        notifyDataSetChanged();
    }


    @Override
    public int getItemCount() {
        return items.size();
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType) {

            case CATEGORY:
                return new CategoryViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_categories_countries, parent, false));
            case COUNTRY:
                return new CountryViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_categories_countries, parent, false));
            case MEALS_YOU_MIGHT_LIKE:
                return new MealsYouMightLikeViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_meals_might_like, parent, false));
            case DAILY_INSPIRATION:
                return new DailyInspirationViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_daily_inspiration, parent, false));
            case HEADER_TEXT:
                return new HeaderTextViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_header_text, parent, false));
            default:
                return null;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {

        if (holder instanceof HeaderTextViewHolder) {
            String headerText = (String)items.get(position).getItem();
            ((HeaderTextViewHolder) holder).header.setText(headerText);
        }
        else if(holder instanceof CategoryViewHolder) {
            ArrayList<Meal> meals = (ArrayList<Meal>)items.get(position).getItem();
            ((CategoryViewHolder) holder).recyclerView.setAdapter(new CategoryRecyclerViewAdapter(context,  meals,listener));
        }
        else if(holder instanceof CountryViewHolder) {
            ArrayList<Meal> meals = (ArrayList<Meal>)items.get(position).getItem();
            ((CountryViewHolder) holder).recyclerView.setAdapter(new CountryRecyclerViewAdapter(context,  meals,listener));
        }
        else if(holder instanceof DailyInspirationViewHolder) {
            ArrayList<Meal> meals = (ArrayList<Meal>)items.get(position).getItem();
            ((DailyInspirationViewHolder) holder).recyclerView.setAdapter(new DailyInspirationRecyclerViewAdapter(context,  meals,listener));
        }
        else if(holder instanceof MealsYouMightLikeViewHolder)
        {
            ArrayList<Meal> meals = (ArrayList<Meal>)items.get(position).getItem();
            ((MealsYouMightLikeViewHolder) holder).recyclerView.setAdapter(new MealMightLikeRecyclerViewAdapter(context,meals,listener));
        }
    }

    @Override
    public int getItemViewType(int position) {
        HomeFragmentItem<Object> currentItem = items.get(position);
        switch (currentItem.getItemType()) {
            case CATEGORY:
                return CATEGORY;
            case COUNTRY:
                return COUNTRY;
            case MEALS_YOU_MIGHT_LIKE:
                return MEALS_YOU_MIGHT_LIKE;
            case DAILY_INSPIRATION:
                return DAILY_INSPIRATION;
            case HEADER_TEXT:
                return HEADER_TEXT;
            default:
                return -1;
        }
    }


    abstract class BaseViewHolder extends RecyclerView.ViewHolder {
        public BaseViewHolder(View itemView) {
            super(itemView);
        }
    }

    class HeaderTextViewHolder extends BaseViewHolder {
        TextView header;
        public HeaderTextViewHolder(View itemView) {
            super(itemView);
            header = itemView.findViewById(R.id.header_title);
        }
    }

    class DailyInspirationViewHolder extends BaseViewHolder {
        RecyclerView recyclerView;

        public DailyInspirationViewHolder(View itemView) {
            super(itemView);
            recyclerView = itemView.findViewById(R.id.daily_inspiration_recycler_view);
        }
    }
    class CategoryViewHolder extends BaseViewHolder {

        RecyclerView recyclerView;
        public CategoryViewHolder(View itemView) {
            super(itemView);
            recyclerView = itemView.findViewById(R.id.category_recycler_view);
        }
    }

    class CountryViewHolder extends BaseViewHolder {
        RecyclerView recyclerView;
        public CountryViewHolder(View itemView) {
            super(itemView);
            recyclerView = itemView.findViewById(R.id.category_recycler_view);
        }
    }


    class MealsYouMightLikeViewHolder extends BaseViewHolder {

        RecyclerView recyclerView;


        public MealsYouMightLikeViewHolder(View itemView) {
            super(itemView);
            recyclerView = itemView.findViewById(R.id.itemYouMightLikeRecycler);
        }
    }





}
