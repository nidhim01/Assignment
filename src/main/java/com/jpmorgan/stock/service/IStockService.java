package com.jpmorgan.stock.service;

import com.jpmorgan.stock.exception.PriceNullException;
import com.jpmorgan.stock.exception.StockNotFoundException;
import com.jpmorgan.stock.model.Trade;

public interface IStockService {

	public Double calculateYield(String symbol, Double price) throws StockNotFoundException, PriceNullException;

	public Double calculatePERatio(String symbol, Double price) throws StockNotFoundException, PriceNullException;

	public Double calculateVolumeWeightedStockPrice(String symbol) throws StockNotFoundException,Exception;

	public Double calculateGBCEAllShareIndex() throws Exception;

	public void addTrade(Trade trade) throws Exception;
	
	public void clearTrades() throws Exception;

}
