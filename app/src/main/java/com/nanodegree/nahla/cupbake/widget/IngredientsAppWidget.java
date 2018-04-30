package com.nanodegree.nahla.cupbake.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.nanodegree.nahla.cupbake.R;
import com.nanodegree.nahla.cupbake.activities.DetailsActivity;
import com.nanodegree.nahla.cupbake.utils.SharedPref;

/**
 * Implementation of App Widget functionality.
 */
public class IngredientsAppWidget extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId) {

        if (SharedPref.loadRecipe(context) != null) {

            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.ingredients_app_widget);

            views.setTextViewText(R.id.appwidget_text, SharedPref.loadRecipe(context).getName());
            views.setOnClickPendingIntent(R.id.widgetContainerRL, PendingIntent.getActivity(context, 0, DetailsActivity.newInstance(context, SharedPref.loadRecipe(context)), 0));

            Intent intent = new Intent(context, IngredientsRemoteViewsService.class);
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);

            views.setRemoteAdapter(R.id.appwidget_LV, intent);

            appWidgetManager.updateAppWidget(appWidgetId, views);
            appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetId, R.id.appwidget_LV);

        }


    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    public static void updateAppWidgets(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
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

