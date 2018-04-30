package com.nanodegree.nahla.cupbake.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nanodegree.nahla.cupbake.R;
import com.nanodegree.nahla.cupbake.adapters.IngredientRVAdapter;
import com.nanodegree.nahla.cupbake.adapters.StepRVAdapter;
import com.nanodegree.nahla.cupbake.models.recipeListing.ResponseRecipeListing;
import com.nanodegree.nahla.cupbake.widget.IngredientsAppWidget;
import com.nanodegree.nahla.cupbake.widget.IngredientsRemoteViewsService;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.nanodegree.nahla.cupbake.activities.DetailsActivity.RECIPE_DETAILS;


public class DescriptionFragment extends Fragment {

    ResponseRecipeListing recipeListing;
    IngredientRVAdapter ingredientsAdapter;
    StepRVAdapter stepsAdapter;

    Unbinder unbinder;
    @BindView(R.id.ingredientRV)
    RecyclerView ingredientRV;
    @BindView(R.id.stepsRV)
    RecyclerView stepsRV;

    public DescriptionFragment() {
    }

    public static DescriptionFragment newInstance(ResponseRecipeListing recipeListing) {
        DescriptionFragment fragment = new DescriptionFragment();
        Bundle args = new Bundle();
        args.putParcelable(RECIPE_DETAILS, recipeListing);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            recipeListing = getArguments().getParcelable(RECIPE_DETAILS);
            IngredientsRemoteViewsService.updateWidget(getActivity().getApplicationContext(), recipeListing);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_description, container, false);
        unbinder = ButterKnife.bind(this, view);

        setUpRecycler();

        return view;
    }

    private void setUpRecycler() {
        ingredientsAdapter = new IngredientRVAdapter(recipeListing.getIngredients());
        ingredientRV.setLayoutManager(new GridLayoutManager(getContext(), 2, GridLayoutManager.HORIZONTAL, false));
        ingredientRV.setAdapter(ingredientsAdapter);

        stepsAdapter = new StepRVAdapter(recipeListing.getSteps(), recipeListing.getName());
        stepsRV.setLayoutManager(new LinearLayoutManager(getContext()));
        stepsRV.setAdapter(stepsAdapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
