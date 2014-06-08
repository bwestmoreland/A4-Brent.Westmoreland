package x40241.brent.westmoreland.a4;
import x40241.brent.westmoreland.a4.model.StockSummary;

interface StockRemoteService {
	String getSymbol(String index);
	List<StockSummary> getStockData();
	List<StockSummary> getWidgetData();
}