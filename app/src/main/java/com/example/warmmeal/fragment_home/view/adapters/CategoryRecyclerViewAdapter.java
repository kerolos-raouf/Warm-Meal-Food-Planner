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
import com.example.warmmeal.fragment_home.view.OnNestedRecyclerViewItemClickedListener;
import com.example.warmmeal.model.pojo.Category;

import java.util.ArrayList;

public class CategoryRecyclerViewAdapter extends RecyclerView.Adapter<CategoryRecyclerViewAdapter.ViewHolder> {

    ArrayList<Category> categories;
    Context context;
    OnNestedRecyclerViewItemClickedListener listener;


    CategoryRecyclerViewAdapter(Context context, ArrayList<Category> objects, OnNestedRecyclerViewItemClickedListener listener) {
        this.categories = objects;
        this.context = context;
        this.listener = listener;
    }

    void setData(ArrayList<Category> objects) {
        categories = objects;
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
        try {
            holder.categoryTitle.setText(categories.get(position).getStrCategory());
            Glide.with(context).load(categories.get(position).getStrCategoryThumb()).placeholder(R.drawable.login_ways).into(holder.categoryImage);
            holder.categoryImage.setOnClickListener((e)->{
                listener.onCategoryClicked(categories.get(position).getStrCategory());
            });
        } catch (Exception e) {
            Log.d("Kerolos", "onBindViewHolder: " + e.getMessage());
        }
    }


    @Override
    public int getItemCount() {
        return categories.size();
    }


}
