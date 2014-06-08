package x40241.brent.westmoreland.a4;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class StockStart extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		Intent stockServiceIntent = new Intent(StockServiceImpl.STOCK_SERVICE_INTENT);
		context.startService(stockServiceIntent);
		Log.i("********StockService*********", "started");
	}

}
