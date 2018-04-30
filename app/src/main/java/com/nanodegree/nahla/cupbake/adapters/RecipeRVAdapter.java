package com.nanodegree.nahla.cupbake.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.nanodegree.nahla.cupbake.R;
import com.nanodegree.nahla.cupbake.activities.DetailsActivity;
import com.nanodegree.nahla.cupbake.models.recipeListing.ResponseRecipeListing;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.nanodegree.nahla.cupbake.widget.IngredientsRemoteViewsService.updateWidget;

public class RecipeRVAdapter extends RecyclerView.Adapter<RecipeRVAdapter.RecipeViewHolder> {

    ArrayList<ResponseRecipeListing> recipes;
    private View itemView;

    public RecipeRVAdapter(ArrayList<ResponseRecipeListing> recipes) {
        this.recipes = recipes;
    }

    @Override
    public RecipeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_recipe, parent, false);

        return new RecipeViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecipeViewHolder holder, int position) {
        holder.emptyVH();

        holder.nameTV.setText(recipes.get(position).getName());
        holder.servingsTV.setText(String.format("%d Servings", recipes.get(position).getServings()));

        Glide.with(itemView.getContext())
                .load(recipes.get(position).getImage())
                .error(R.drawable.ic_recipe)
                .placeholder(R.drawable.ic_recipe)
                .fitCenter()
                .into(holder.imageIV);

    }

    @Override
    public int getItemCount() {
        return recipes.size();
    }

    public class RecipeViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.imageIV)
        ImageView imageIV;
        @BindView(R.id.nameTV)
        TextView nameTV;
        @BindView(R.id.servingsTV)
        TextView servingsTV;

        public RecipeViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        @OnClick(R.id.recipeCV)
        public void onViewClicked() {
            updateWidget(itemView.getContext().getApplicationContext(), recipes.get(getAdapterPosition()));
            itemView.getContext().startActivity(DetailsActivity.newInstance(itemView.getContext(), recipes.get(getAdapterPosition())));
        }

        public void emptyVH() {
            imageIV.setImageResource(R.drawable.ic_recipe);
            nameTV.setText("");
            servingsTV.setText("");
        }
    }
}
