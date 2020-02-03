package com.attafakkur.myfilmfinal.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.attafakkur.myfilmfinal.R;

import java.util.Objects;

/**
 * Implementation of App Widget functionality.
 */
public class ImageWidget extends AppWidgetProvider {

    private static final String TOAST = "com.attafakkur.myfilmfinal.widget.TOAST";
    public static final String ITEM = "com.attafakkur.myfilmfinal.widget.ITEM";
    public static final String UPDATE = "com.attafakkur.myfilmfinal.widget.UPDATE";

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        Intent intent = new Intent(context, StackWidgetService.class);
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,appWidgetId);
        intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.image_widget);

        views.setRemoteAdapter(R.id.stack_view,intent);
        views.setEmptyView(R.id.stack_view, R.id.empty_view);

        Intent intentView = new Intent(context, ImageWidget.class);
        intentView.setAction(ImageWidget.TOAST);
        intentView.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,appWidgetId);

        intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context,0, intentView, PendingIntent.FLAG_UPDATE_CURRENT);
        views.setPendingIntentTemplate(R.id.stack_view, pendingIntent);
        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction() != null){
            if (intent.getAction().equals(TOAST)){
                int index = intent.getIntExtra(ITEM, 0);
                Toast.makeText(context,"Movie "+ index, Toast.LENGTH_SHORT).show();
            }
        }
        if (Objects.requireNonNull(intent.getAction()).equals(UPDATE)){
            AppWidgetManager manager = AppWidgetManager.getInstance(context);
            int[] id = manager.getAppWidgetIds(new ComponentName(context, ImageWidget.class));
            manager.notifyAppWidgetViewDataChanged(id, R.id.stack_view);
        }
        super.onReceive(context, intent);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

