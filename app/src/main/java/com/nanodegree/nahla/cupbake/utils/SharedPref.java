package com.nanodegree.nahla.cupbake.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.nanodegree.nahla.cupbake.models.recipeListing.ResponseRecipeListing;

import static com.nanodegree.nahla.cupbake.utils.Const.RECIPE_DESCRIPTION;

public class SharedPref {
    public static final String PREFS_NAME = "prefs";

    public static void saveRecipe(Context context, ResponseRecipeListing recipe) {
        SharedPreferences.Editor prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE).edit();

        Gson gson = new Gson();
        String json = gson.toJson(recipe);

        prefs.putString(RECIPE_DESCRIPTION, json);
        prefs.apply();
    }

    public static ResponseRecipeListing loadRecipe(Context context) {

        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);

        Gson gson = new Gson();
        String json = prefs.getString(RECIPE_DESCRIPTION, null);

        ResponseRecipeListing recipeListing = gson.fromJson(json, ResponseRecipeListing.class);

        return recipeListing;
    }

}
