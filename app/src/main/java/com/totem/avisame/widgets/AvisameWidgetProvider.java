package com.totem.avisame.widgets;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.totem.avisame.R;
import com.totem.avisame.activities.RegisterActivity;

/**
 * Implementation of App Widget functionality.
 */
public class AvisameWidgetProvider extends AppWidgetProvider {

    private final static int ARRIVED_FLAG = 1;
    private final static int ALERT_FLAG = 2;
    private final static int DANGER_FLAG = 3;

    private final static String ARRIVED_ACTION = "ARRIVED_ACTION";
    private final static String ALERT_ACTION = "ALERT_ACTION";
    private final static String DANGER_ACTION = "DANGER_ACTION";

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {

            // Get the layout for the App Widget and attach an on-click listener
            // to the button
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.avisame_widget);
            views.setOnClickPendingIntent(R.id.arrived_button, getPendingSelfIntent(context, ARRIVED_ACTION));
            views.setOnClickPendingIntent(R.id.alert_button, getPendingSelfIntent(context, ALERT_ACTION));
            views.setOnClickPendingIntent(R.id.danger_button, getPendingSelfIntent(context, DANGER_ACTION));

            // Tell the AppWidgetManager to perform an update on the current app widget
            appWidgetManager.updateAppWidget(appWidgetId, views);
        }
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        Intent action = new Intent(context, RegisterActivity.class);
        if (intent.getAction().equals(ARRIVED_ACTION)) {

            action.putExtra("PENDING_FLAG", ARRIVED_FLAG);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, action, 0);
            try {
                pendingIntent.send();
            } catch (PendingIntent.CanceledException e) {
                e.printStackTrace();
            }
        } else if (intent.getAction().equals(ALERT_ACTION)) {

            action.putExtra("PENDING_FLAG", ALERT_FLAG);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, action, 0);
            try {
                pendingIntent.send();
            } catch (PendingIntent.CanceledException e) {
                e.printStackTrace();
            }
        } else if (intent.getAction().equals(DANGER_ACTION)) {

            action.putExtra("PENDING_FLAG", DANGER_FLAG);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, action, 0);
            try {
                pendingIntent.send();
            } catch (PendingIntent.CanceledException e) {
                e.printStackTrace();
            }
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

    protected PendingIntent getPendingSelfIntent(Context context, String action) {
        Intent intent = new Intent(context, getClass());
        intent.setAction(action);
        return PendingIntent.getBroadcast(context, 0, intent, 0);
    }
}

