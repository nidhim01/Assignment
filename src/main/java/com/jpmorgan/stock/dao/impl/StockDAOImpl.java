package com.jpmorgan.stock.dao.impl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jpmorgan.stock.dao.IStockDAO;
import com.jpmorgan.stock.exception.StockNotFoundException;
import com.jpmorgan.stock.model.Stock;

@Repository("stockDAOImpl")
public class StockDAOImpl implements IStockDAO {

	private static final Logger LOGGER = LoggerFactory.getLogger(StockDAOImpl.class);
	private Map<String,Stock> stocks;

	@PostConstruct
	public void init() throws Exception {
		/**
		 * Reading data from a property file for the assignment
		 */
		ObjectMapper objectMapper = new ObjectMapper();
		String json = this.readDataFile("context/data/data.json");
		stocks = objectMapper.readValue(json, new TypeReference<Map<String,Stock>>() {
		});
	}

	@Override
	public Stock findBySymbol(String symbol) throws StockNotFoundException{
		Stock matchedStock = this.stocks.get(symbol);
		if(matchedStock==null){
			LOGGER.error("Stock {} not found in the system",symbol);
			throw new StockNotFoundException(String.format("Stock %s not found",symbol));
		}
		return matchedStock;
	}
	
	@Override
	public boolean isStockExist(String symbol) {
	  return stocks.containsKey(symbol);
	}

	@Override
	public List<Stock> getStockList() {
		List<Stock> stockList = new ArrayList<>();
		for(Map.Entry<String,Stock> entry : this.stocks.entrySet()){
			stockList.add(entry.getValue());
		}
		return stockList;
	}
	

	private String readDataFile(String fileName) {
		StringBuilder result = new StringBuilder();
		ClassLoader classLoader = getClass().getClassLoader();
		File file = new File(classLoader.getResource(fileName).getFile());

		try (Scanner scanner = new Scanner(file)) {
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				result.append(line);
			}
			scanner.close();

		} catch (IOException e) {
			LOGGER.error("Error while reading the data file",e);
		}
		return result.toString();
	}

}
