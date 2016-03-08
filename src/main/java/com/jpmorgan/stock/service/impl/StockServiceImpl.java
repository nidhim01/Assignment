package com.jpmorgan.stock.service.impl;

import java.util.Calendar;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jpmorgan.stock.dao.IStockDAO;
import com.jpmorgan.stock.dao.ITradeDAO;
import com.jpmorgan.stock.exception.PriceNullException;
import com.jpmorgan.stock.exception.StockNotFoundException;
import com.jpmorgan.stock.model.Stock;
import com.jpmorgan.stock.model.StockType;
import com.jpmorgan.stock.model.Trade;
import com.jpmorgan.stock.service.IStockService;

@Service("stockServiceImpl")
public class StockServiceImpl implements IStockService {
	private static final Logger LOGGER = LoggerFactory.getLogger(StockServiceImpl.class);
	@Autowired
	private IStockDAO stockDAO;
	@Autowired
	private ITradeDAO tradeDAO;

	@Override
	public Double calculateYield(String symbol, Double price) throws StockNotFoundException, PriceNullException {
		LOGGER.info("Calculating yield for stock: {}  and price {}",symbol,price);
		if (price == null || price==0) {
			throw new PriceNullException("Price cannot be null or Zero to calculate yield");
		}
		Stock stock = stockDAO.findBySymbol(symbol);
		Double yield = null;
		if (StockType.COMMON.equals(stock.getType())) {
			yield = stock.getLastDividend() / price;
		} else {
			yield = (stock.getFixedDividend() * stock.getParValue()) / price;
		}
		LOGGER.info("Yield value: {}",yield);
		return yield;
	}

	@Override
	public Double calculatePERatio(String symbol, Double price) throws StockNotFoundException, PriceNullException {
		LOGGER.info("Calculating P/E ratio for stock: {}  and price {}",symbol,price);
		Double peRatio =0d;
		if (price == null) {
			throw new PriceNullException("Price cannot be null to calculate P/E ratio");
		}
		Stock stock = stockDAO.findBySymbol(symbol);
		/**
		 * Calculating P/E ratio for non zero dividend
		 */
		if(stock.getLastDividend()!=0){
			peRatio = (price / stock.getLastDividend());
		}
		LOGGER.info("p/e Ratio value: {}",peRatio);
		return peRatio;
	}

	@Override
	public Double calculateGBCEAllShareIndex() throws Exception {
		LOGGER.info("Calculating calculateGBCEAllShareIndex");
		Double results = 0d;
		List<Trade> trades = tradeDAO.getAllTrades();
		Double priceLog = 0d;
		int counter =0;
		for(Trade trade: trades){
			LOGGER.info("Traded Price {}",trade.getTradedPrice());
			priceLog = priceLog + Math.log(trade.getTradedPrice());
			counter = counter+1;
		}
		if(counter >0){
			results = Math.exp(priceLog / counter);
		}
		LOGGER.info("BCEAllShareIndex value: {}",results);
		return results;
	}

	@Override
	public void addTrade(Trade trade) throws StockNotFoundException,Exception {
		LOGGER.info("Adding Trade to System");
		try{
		   stockDAO.findBySymbol(trade.getSymbol());
		}catch(StockNotFoundException e){
			throw new StockNotFoundException(String.format("Cannot add Trade as stock : %s is not registered",trade.getSymbol()));
		}
		LOGGER.info("Trade Added successfully");
		tradeDAO.addTrade(trade);
	}

	@Override
	public Double calculateVolumeWeightedStockPrice(String symbol) throws StockNotFoundException,Exception {
		LOGGER.info("Calculating VolumeWeightedStockPrice for stock : {}",symbol);
		if(!stockDAO.isStockExist(symbol)){
			throw new StockNotFoundException(String.format("Stock : %s is not registered or does not exist", symbol));
		}
		Double vwsp = 0d;
		Calendar now = Calendar.getInstance();
		now.add(Calendar.MINUTE, -15);
		List<Trade> trades = tradeDAO.getTradesForAStockBeforeCutOffDate(symbol, now.getTime());
		Double priceQuantitySummation = 0d;
		Double quantitySummation =0d;
		for(Trade trade : trades){
			priceQuantitySummation = priceQuantitySummation + (trade.getTradedPrice()*trade.getQuantityOfShares());
			quantitySummation = quantitySummation + trade.getQuantityOfShares();
		}
		if(quantitySummation!=0){
			vwsp = priceQuantitySummation/quantitySummation;
		}
		LOGGER.info("VolumeWeightedStockPrice for symbol {} is {}",symbol,vwsp);
		return vwsp;
		
	}

	@Override
	public void clearTrades() throws Exception {
		tradeDAO.clearTrades();
		
	}


}
