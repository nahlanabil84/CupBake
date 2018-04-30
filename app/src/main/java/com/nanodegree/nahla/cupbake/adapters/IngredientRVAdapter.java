package com.nanodegree.nahla.cupbake.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nanodegree.nahla.cupbake.R;
import com.nanodegree.nahla.cupbake.models.recipeListing.Ingredient;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class IngredientRVAdapter extends RecyclerView.Adapter<IngredientRVAdapter.RecipeViewHolder> {

    ArrayList<Ingredient> ingredients;
    private View itemView;

    public IngredientRVAdapter(ArrayList<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    @Override
    public RecipeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_ingredient, parent, false);

        return new RecipeViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecipeViewHolder holder, int position) {
        holder.emptyVH();
        holder.quantityTV.setText(ingredients.get(position).getQuantity() + "");
        holder.measureTV.setText(ingredients.get(position).getMeasure());
        holder.ingredientTV.setText(ingredients.get(position).getIngredient());

    }

    @Override
    public int getItemCount() {
        return ingredients.size();
    }

    public class RecipeViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.quantityTV)
        TextView quantityTV;
        @BindView(R.id.measureTV)
        TextView measureTV;
        @BindView(R.id.ingredientTV)
        TextView ingredientTV;

        public RecipeViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        public void emptyVH() {
            quantityTV.setText("");
            quantityTV.setTextSize(18);
            quantityTV.setVisibility(View.VISIBLE);
            measureTV.setText("");
            measureTV.setTextSize(18);
            measureTV.setVisibility(View.VISIBLE);
            ingredientTV.setText("");
            ingredientTV.setTextSize(18);
            ingredientTV.setVisibility(View.VISIBLE);
        }
    }
}
