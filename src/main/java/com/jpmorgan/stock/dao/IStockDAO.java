package com.jpmorgan.stock.dao;

import java.util.List;

import com.jpmorgan.stock.exception.StockNotFoundException;
import com.jpmorgan.stock.model.Stock;

public interface IStockDAO {

	public Stock findBySymbol(String symbol) throws StockNotFoundException;
	public boolean isStockExist(String symbol);
	public List<Stock> getStockList();
	
}
