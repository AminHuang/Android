package com.example.ex05;

import android.appwidget.AppWidgetManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

public class MyBroadcastReceiver extends BroadcastReceiver{

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget_layout);
		remoteViews.setTextViewText(R.id.widget_text, intent.getStringExtra("msg"));
		
		AppWidgetManager.getInstance(context).updateAppWidget(
				new ComponentName(context.getApplicationContext(),
						MyWidgetProvider.class),remoteViews
				);
		
	}

}
