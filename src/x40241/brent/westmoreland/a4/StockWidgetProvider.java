package x40241.brent.westmoreland.a4;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

public class StockWidgetProvider extends AppWidgetProvider {
//	private final static String LOGTAG = "****WIDGET******";

	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
		context.getApplicationContext().registerReceiver(this, new IntentFilter(StockServiceImpl.STOCK_SERVICE_INTENT));
//		Log.d(LOGTAG, "On update called");
	    ComponentName thisWidget = new ComponentName(context, StockWidgetProvider.class);
	    int[] allWidgetIds = appWidgetManager.getAppWidgetIds(thisWidget);

	    Intent serviceIntent = new Intent(context.getApplicationContext(), StockWidgetUpdateService.class);
	    serviceIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, allWidgetIds);
	    
	    context.startService(serviceIntent);
	    
		super.onUpdate(context, appWidgetManager, appWidgetIds);
	}
	
	@Override
	public void onReceive(Context context, Intent intent) {
//		Log.d(LOGTAG, "On receive called");
		AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context.getApplicationContext());
		onUpdate(context, appWidgetManager, null);
		super.onReceive(context, intent);
	}
}
