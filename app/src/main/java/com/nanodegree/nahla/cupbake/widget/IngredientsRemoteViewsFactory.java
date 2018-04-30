package com.nanodegree.nahla.cupbake.widget;

import android.content.Context;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.nanodegree.nahla.cupbake.R;
import com.nanodegree.nahla.cupbake.models.recipeListing.ResponseRecipeListing;
import com.nanodegree.nahla.cupbake.utils.SharedPref;


public class IngredientsRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    Context context;
    ResponseRecipeListing recipeListing;

    public IngredientsRemoteViewsFactory(Context context) {
        this.context = context;
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {
        recipeListing = SharedPref.loadRecipe(context);
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return recipeListing.getIngredients().size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        final RemoteViews remoteView = new RemoteViews(context.getPackageName(), R.layout.item_widget_ingredient);

        remoteView.setTextViewText(R.id.quantityTV, recipeListing.getIngredients().get(position).getQuantity() + "");

        remoteView.setTextViewText(R.id.measureTV, recipeListing.getIngredients().get(position).getMeasure());

        remoteView.setTextViewText(R.id.ingredientTV, recipeListing.getIngredients().get(position).getIngredient());

        return remoteView;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}
