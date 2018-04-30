package com.nanodegree.nahla.cupbake.adapters;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nanodegree.nahla.cupbake.R;
import com.nanodegree.nahla.cupbake.activities.StepActivity;
import com.nanodegree.nahla.cupbake.fragments.StepsFragment;
import com.nanodegree.nahla.cupbake.models.recipeListing.Step;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class StepRVAdapter extends RecyclerView.Adapter<StepRVAdapter.RecipeViewHolder> {

    ArrayList<Step> steps;
    private View itemView;
    String recipeName;

    public StepRVAdapter(ArrayList<Step> steps, String recipeName) {
        this.steps = steps;
        this.recipeName = recipeName;
    }

    @Override
    public RecipeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_step, parent, false);

        return new RecipeViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecipeViewHolder holder, int position) {
        holder.emptyVH();
        holder.shortDescriptionTV.setText(steps.get(position).getShortDescription());
    }

    @Override
    public int getItemCount() {
        return steps.size();
    }


    public class RecipeViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.shortDescriptionTV)
        TextView shortDescriptionTV;

        public RecipeViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        public void emptyVH() {
            shortDescriptionTV.setText("");
        }

        @OnClick(R.id.stepContainerCV)
        public void onViewClicked() {

            if (itemView.getContext().getResources().getConfiguration().smallestScreenWidthDp >= 600) {
                ((AppCompatActivity) itemView.getContext())
                        .getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.stepFL, StepsFragment.newInstance(steps.get(getAdapterPosition())))
                        .addToBackStack(null)
                        .commit();
            } else {
                itemView.getContext().startActivity(StepActivity.newInstance(itemView.getContext(), steps.get(getAdapterPosition()), recipeName));
            }
        }

    }
}
