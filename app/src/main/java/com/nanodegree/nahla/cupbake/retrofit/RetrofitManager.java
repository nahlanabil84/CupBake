package com.nanodegree.nahla.cupbake.retrofit;


import android.app.ProgressDialog;
import android.content.Context;
import android.widget.Toast;

import com.nanodegree.nahla.cupbake.R;
import com.nanodegree.nahla.cupbake.listeners.OnGetDataListener;
import com.nanodegree.nahla.cupbake.models.recipeListing.ResponseRecipeListing;
import com.nanodegree.nahla.cupbake.utils.CheckConnection;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.nanodegree.nahla.cupbake.utils.Const.BASE_URL;

public class RetrofitManager {

    private static RetrofitManager retrofitManager;
    private RetrofitService api;

    private RetrofitManager() {
        createApi();
    }

    public static RetrofitManager getInstance() {
        retrofitManager = new RetrofitManager();
        return retrofitManager;
    }

    void createApi() {
        final OkHttpClient.Builder clientBuilder;
        final OkHttpClient client;

        clientBuilder = new OkHttpClient.Builder()
                .readTimeout(1000, TimeUnit.SECONDS)
                .connectTimeout(1000, TimeUnit.SECONDS);

        client = clientBuilder.build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        api = retrofit.create(RetrofitService.class);
    }

    public void getRecipes(final Context context, final OnGetDataListener<ArrayList<ResponseRecipeListing>, String> listener, final ProgressDialog dialog) {

        if (!CheckConnection.setCheckConnection(context)) {
            Toast.makeText(context, R.string.error_connnection_internet, Toast.LENGTH_SHORT).show();
            return;
        }

        showLoadingDialog(dialog, context);

        api.getRecipes().enqueue(new Callback<ArrayList<ResponseRecipeListing>>() {
            @Override
            public void onResponse(Call<ArrayList<ResponseRecipeListing>> call, Response<ArrayList<ResponseRecipeListing>> response) {
                if (response.isSuccessful()) {
                    listener.onSuccess(response.body());

                } else {
                    listener.onFailed(response.message());
                }

                dialog.dismiss();
            }

            @Override
            public void onFailure(Call<ArrayList<ResponseRecipeListing>> call, Throwable t) {
                dialog.dismiss();
                listener.onFailed(t.toString());
            }
        });
    }

    private void showLoadingDialog(ProgressDialog dialog, final Context context) {
        String pleaseWait = context.getString(R.string.Dialog_please_wait);
        dialog.setMessage(pleaseWait);
        dialog.setCancelable(false);
        dialog.show();
    }

}
