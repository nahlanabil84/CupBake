package com.nanodegree.nahla.cupbake.retrofit;

import com.nanodegree.nahla.cupbake.models.recipeListing.ResponseRecipeListing;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;

public interface RetrofitService {

    String END_POINT = "baking.json";

    @GET(END_POINT)
    Call<ArrayList<ResponseRecipeListing>> getRecipes();

}
