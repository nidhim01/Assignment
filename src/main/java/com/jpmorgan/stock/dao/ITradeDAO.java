package com.jpmorgan.stock.dao;

import java.util.Date;
import java.util.List;

import com.jpmorgan.stock.model.Trade;

public interface ITradeDAO {

	public void addTrade(Trade trade) throws Exception;
	public List<Trade> getAllTrades() throws Exception;
	public List<Trade> getTradesForAStockBeforeCutOffDate(String symbol,Date cutOffTime) throws Exception;
	public void clearTrades();
}
