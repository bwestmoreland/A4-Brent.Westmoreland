package x40241.brent.westmoreland.a4;

import java.util.ArrayList;
import java.util.List;

import x40241.brent.westmoreland.a4.model.StockSummary;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.widget.RemoteViews;

public class StockWidgetProvider extends AppWidgetProvider {
	
	protected static final String ACTION_CLICK = "ACTION_CLICK";
	protected StockRemoteService mService;
	protected Boolean isBound = false;
	protected BroadcastReceiver mStockDataReceiver;
	protected List<StockSummary> mStockList;
	

	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
		if (!isBound) {
			context.getApplicationContext().registerReceiver(getStockDataReceiver(),new IntentFilter(StockServiceImpl.STOCK_SERVICE_INTENT));
			context.getApplicationContext().bindService(new Intent(StockServiceImpl.STOCK_SERVICE_INTENT), mServiceConnection, Context.BIND_AUTO_CREATE);
		}
		
		ComponentName thisWidget = new ComponentName(context, StockWidgetProvider.class);
		int[] allWidgetIds = appWidgetManager.getAppWidgetIds(thisWidget);
		for (int widgetId : allWidgetIds){
			
			RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.stock_widget_layout);
			remoteViews.setTextViewText(R.id.symbol1Update, getStockList().get(0).getSymbol());
			remoteViews.setTextViewText(R.id.price1Update, getStockList().get(0).getPrice() + "");
			remoteViews.setTextViewText(R.id.symbol2Update, getStockList().get(1).getSymbol());
			remoteViews.setTextViewText(R.id.price2Update, getStockList().get(1).getPrice() + "");
			remoteViews.setTextViewText(R.id.symbol3Update, getStockList().get(2).getSymbol());
			remoteViews.setTextViewText(R.id.price3Update, getStockList().get(2).getPrice() + "");
			
			Intent intent = new Intent(context, MainActivity.class);
			PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
			
			remoteViews.setOnClickPendingIntent(R.id.widgetUpdate, pendingIntent);
			appWidgetManager.updateAppWidget(widgetId, remoteViews);
		}
		
		super.onUpdate(context, appWidgetManager, appWidgetIds);
	}
	
	/**
	 * ServiceConnection
	 */
	
	private ServiceConnection mServiceConnection = new ServiceConnection() {
		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			mService = StockRemoteService.Stub.asInterface(service);
			isBound = true;
		}
		
		@Override
		public void onServiceDisconnected(ComponentName name) {
			mService = null;
			isBound = false;
		}
	};
	
	protected BroadcastReceiver getStockDataReceiver(){
		if (mStockDataReceiver == null){
			mStockDataReceiver = new BroadcastReceiver() {
				@Override
				public void onReceive(Context context, Intent intent) {
					if(intent.getSerializableExtra(StockServiceImpl.STOCK_SERVICE_INTENT).equals(StockServiceImpl.STOCK_DATA_AVAILABLE)) {
						try {
							setStockList(mService.getWidgetData());
						} catch (RemoteException e) {
							e.printStackTrace();
						}
						updateData(context);
					}
				}
			};
		}
		return mStockDataReceiver;
	}
	
	private void updateData(Context context) {
		Log.d("Widget", "Attempting update***********");
		Intent intent = new Intent(context, StockWidgetProvider.class);
		intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
		int ids[] = AppWidgetManager.getInstance(context).getAppWidgetIds(new ComponentName(context, MainActivity.class));
	    intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, ids);
	    context.sendBroadcast(intent);		
	}
	
	private List<StockSummary> getStockList() {
		if (mStockList == null) {
			mStockList = new ArrayList<StockSummary>();
			StockSummary summary = new StockSummary("Symbol", 0.0f);
			mStockList.add(summary);
			mStockList.add(summary);
			mStockList.add(summary);
		}
		return mStockList;
	}

	private void setStockList(List<StockSummary> stockList) {
		mStockList = stockList;
	}

}
