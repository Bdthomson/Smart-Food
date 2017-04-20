package com.schachte.android.proactive_food_app.activities.recipe_detail_activity;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.schachte.android.proactive_food_app.R;

import java.util.List;

public class IngredientListAdapter extends RecyclerView.Adapter<IngredientListAdapter.IngredientViewHolder> {

    List<String> ingredientList;

    public IngredientListAdapter(List<String> ingredientList) {
        this.ingredientList = ingredientList;
    }

    @Override
    public IngredientViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ingredient_list_cell, parent, false);
        IngredientViewHolder viewHolder = new IngredientViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(IngredientViewHolder holder, int position) {
        holder.ingredient.setText(ingredientList.get(position));
    }

    @Override
    public int getItemCount() {
        return ingredientList.size();
    }

    public static class IngredientViewHolder extends RecyclerView.ViewHolder {

        protected TextView ingredient;

        public IngredientViewHolder(View itemView) {
            super(itemView);
            ingredient = (TextView) itemView.findViewById(R.id.ingredientName);
        }
    }
}
