package com.hackerrank.stocktrades.service;


import java.util.List;

import com.hackerrank.stocktrades.model.StockTrade;

public interface StockTradeService {

	public List<StockTrade> getAllStockTrades();

	public List<StockTrade> getStockTradesByTypeBuyUserId(String type, String userId);
	
	public StockTrade createNewStockTrade(StockTrade stockTrade);

	public StockTrade getStockTradeById(Integer id);


}
