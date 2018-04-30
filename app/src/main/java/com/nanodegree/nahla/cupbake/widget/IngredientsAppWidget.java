package com.nanodegree.nahla.cupbake.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.widget.RemoteViews;

import com.nanodegree.nahla.cupbake.R;
import com.nanodegree.nahla.cupbake.activities.DetailsActivity;
import com.nanodegree.nahla.cupbake.models.recipeListing.ResponseRecipeListing;
import com.nanodegree.nahla.cupbake.utils.SharedPref;

import butterknife.BindView;

/**
 * Implementation of App Widget functionality.
 */
public class IngredientsAppWidget extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {


        ResponseRecipeListing recipe = SharedPref.loadRecipe(context);
        if (recipe != null) {

            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.ingredients_app_widget);
            views.setTextViewText(R.id.appwidget_text, recipe.getName());

            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, DetailsActivity.newInstance(context, recipe), 0);
            views.setOnClickPendingIntent(R.id.widgetContainerRL, pendingIntent);

            Intent intent = new Intent(context, IngredientsRemoteViewsService.class);
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
            views.setRemoteAdapter(R.id.appwidget_LV, intent);

            appWidgetManager.updateAppWidget(appWidgetId, views);
            appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetId, R.id.appwidget_LV);

        }


    }

    public static void updateAppWidgets(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
    }

    @Override
    public void onDisabled(Context context) {
    }
}

