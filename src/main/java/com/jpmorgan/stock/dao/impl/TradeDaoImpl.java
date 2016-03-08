package com.jpmorgan.stock.dao.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.jpmorgan.stock.dao.ITradeDAO;
import com.jpmorgan.stock.model.Trade;

@Repository("tradeDao")
public class TradeDaoImpl implements ITradeDAO {
    
	private static final Logger LOGGER = LoggerFactory.getLogger(TradeDaoImpl.class);
	private List<Trade> trades = new ArrayList<>();

	@Override
	public void addTrade(Trade trade) throws Exception {
		Calendar now = Calendar.getInstance();
		trade.setTradedTime(now.getTime());
		trades.add(trade);
	}

	@Override
	public List<Trade> getAllTrades() throws Exception {
		return this.trades;
	}

	@Override
	public List<Trade> getTradesForAStockBeforeCutOffDate(String symbol,Date cutOffTime) throws Exception {
		List<Trade> matchedTrades = new ArrayList<>();
		LOGGER.info("Cut Off Time {}",cutOffTime);
		for(Trade trade: this.trades){
			LOGGER.info("Traded Time : {}",trade.getTradedTime());
			if(symbol.equals(trade.getSymbol())
					&& (trade.getTradedTime().after(cutOffTime) || trade.getTradedTime().equals(cutOffTime))){
				matchedTrades.add(trade);
			}	
		}
		return matchedTrades;
	}

	@Override
	public void clearTrades() {
	  this.trades.clear();
	}
	
	
}
