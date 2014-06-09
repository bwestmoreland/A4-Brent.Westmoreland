package x40241.brent.westmoreland.a4;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import x40241.brent.westmoreland.a4.db.StockDatabaseHelper;
import x40241.brent.westmoreland.a4.db.StockDatabaseHelper.StockCursor;
import x40241.brent.westmoreland.a4.model.StockSummary;
import android.app.PendingIntent;
import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.os.IBinder;
import android.widget.RemoteViews;

public class StockWidgetUpdateService extends Service {
//	private String LOGTAG = "*****WIDGET SERVICE*****";
	private StockDatabaseHelper mHelper;
	private DecimalFormat mDecimalFormat;
	
	@Override
	public void onCreate() {
//		Log.d(LOGTAG, "on create");
		mHelper = new StockDatabaseHelper(getApplicationContext());
		super.onCreate();
	}
	
	@Override
	public void onDestroy() {
//		Log.d(LOGTAG, "on destroy");
		super.onDestroy();
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
//		Log.d(LOGTAG, "on start command");
	    AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this.getApplicationContext());

	    ComponentName thisWidget = new ComponentName(getApplicationContext(), StockWidgetProvider.class);
	    int[] allWidgetIds = appWidgetManager.getAppWidgetIds(thisWidget);
	    
	    List<StockSummary> summary = getStockList();
	    if (summary.size() >= 3) {
	    	for (int widgetId : allWidgetIds) {
			    RemoteViews remoteViews = new RemoteViews(this.getApplicationContext().getPackageName(), R.layout.stock_widget_layout);
				remoteViews.setTextViewText(R.id.symbol1Update, "SYM: " + summary.get(0).getSymbol());
				remoteViews.setTextViewText(R.id.avg1Update, "AVG: " + getDecimalFormat().format(summary.get(0).getAvg()));
				remoteViews.setTextViewText(R.id.price1Update, "PRC: " + summary.get(0).getPrice());
				remoteViews.setTextViewText(R.id.symbol2Update, "SYM: " + summary.get(1).getSymbol());
				remoteViews.setTextViewText(R.id.avg2Update, "AVG: " + getDecimalFormat().format(summary.get(1).getAvg()));
				remoteViews.setTextViewText(R.id.price2Update, "PRC: " + summary.get(1).getPrice());
				remoteViews.setTextViewText(R.id.symbol3Update, "SYM: " + summary.get(2).getSymbol());
				remoteViews.setTextViewText(R.id.avg3Update, "AVG: " + getDecimalFormat().format(summary.get(2).getAvg()));
				remoteViews.setTextViewText(R.id.price3Update, "PRC: " + summary.get(2).getPrice());
		
			    Intent clickIntent = new Intent(this.getApplicationContext(), MainActivity.class);	    
			    PendingIntent pendingIntent = PendingIntent.getActivity(this.getApplicationContext(), 0, clickIntent, 0);
			
			    remoteViews.setOnClickPendingIntent(R.id.widgetUpdate, pendingIntent);
			    appWidgetManager.updateAppWidget(widgetId, remoteViews);
		    }
	    }
	    stopSelf();
		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public IBinder onBind(Intent intent) {
		
		return null;
	}
	
	private List<StockSummary> getStockList(){
    	List<StockSummary> list = new ArrayList<StockSummary>();
        StockCursor cursor = mHelper.queryStockHighestAvg();
        if (cursor.moveToFirst())
        {
            do
            {
            	list.add(cursor.getSummary());
            }
            while (cursor.moveToNext());
        }
        if (cursor != null && !cursor.isClosed())
        {
            cursor.close();
        }
        return list;
	}
	
	private DecimalFormat getDecimalFormat() {
		if (mDecimalFormat == null){
            mDecimalFormat = new DecimalFormat("##.##");
            mDecimalFormat.setRoundingMode(RoundingMode.DOWN);
		}
		return mDecimalFormat;
	}
}
