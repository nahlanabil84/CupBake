package com.nanodegree.nahla.cupbake.fragments;

import android.app.ProgressDialog;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.nanodegree.nahla.cupbake.R;
import com.nanodegree.nahla.cupbake.adapters.RecipeRVAdapter;
import com.nanodegree.nahla.cupbake.listeners.OnGetDataListener;
import com.nanodegree.nahla.cupbake.models.recipeListing.ResponseRecipeListing;
import com.nanodegree.nahla.cupbake.retrofit.RetrofitManager;
import com.nanodegree.nahla.cupbake.utils.SharedPref;
import com.nanodegree.nahla.cupbake.widget.IngredientsRemoteViewsService;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class RecipeListingFragment extends Fragment implements OnGetDataListener<ArrayList<ResponseRecipeListing>, String> {

    @BindView(R.id.recipesRV)
    RecyclerView recipesRV;
    ArrayList<ResponseRecipeListing> recipes;

    Unbinder unbinder;
    RecipeRVAdapter adapter;
    GridLayoutManager layoutManager;
    RetrofitManager manager;
    private View view;
    ProgressDialog dialog;


    public RecipeListingFragment() {
    }

    public static RecipeListingFragment newInstance() {
        RecipeListingFragment fragment = new RecipeListingFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_recipe_listing, container, false);

        setUp();

        return view;
    }

    private void setUp() {
        unbinder = ButterKnife.bind(this, view);
        recipes = new ArrayList<>();

        adapter = new RecipeRVAdapter(recipes);

        if (getContext().getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
            layoutManager = new GridLayoutManager(getContext(), 1);
        else
            layoutManager = new GridLayoutManager(getContext(), 2);

        recipesRV.setLayoutManager(layoutManager);
        recipesRV.setItemAnimator(new DefaultItemAnimator());
        recipesRV.setAdapter(adapter);

        manager = RetrofitManager.getInstance();

        dialog = new ProgressDialog(getContext());
        manager.getRecipes(getContext(), this, dialog);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onSuccess(ArrayList<ResponseRecipeListing> recipeListing) {
        recipes.addAll(recipeListing);
        adapter.notifyDataSetChanged();

        if (getActivity().getApplicationContext() != null && SharedPref.loadRecipe(getActivity().getApplicationContext()) == null) {
            IngredientsRemoteViewsService.updateWidget(getActivity(), recipeListing.get(0));
        }

    }

    @Override
    public void onFailed(String errorMsg) {
        Toast.makeText(getContext(), errorMsg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPause() {
        super.onPause();
        dialog.dismiss();
    }
}
