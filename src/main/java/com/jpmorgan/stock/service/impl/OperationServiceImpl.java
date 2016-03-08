package com.jpmorgan.stock.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jpmorgan.stock.model.OperationType;
import com.jpmorgan.stock.model.Trade;
import com.jpmorgan.stock.model.TradeIndicator;
import com.jpmorgan.stock.service.IOperationService;
import com.jpmorgan.stock.service.IStockService;

@Service("operationService")
public class OperationServiceImpl implements IOperationService {

	private static final Logger LOGGER = LoggerFactory.getLogger(OperationServiceImpl.class);
	
	@Autowired
	private IStockService stockService;

	/**
	 * All Possible validation is not performed here ,
	 * Can always add validation later
	 */
	
	@Override
	public void doOperation(OperationType operationType, String[] args) throws Exception {

		switch (operationType) {
		case YIELD: {
			if(args.length<3){
				String message = String.format("Operation %s needs 3 arguments in order operationType,symbol,price", operationType);
				LOGGER.error(message);
				throw new Exception(message);
			}
			stockService.calculateYield(args[1], Double.valueOf(args[2]));
			break;
		}
		case PE: {
			if(args.length<3){
				String message = String.format("Operation %s needs 3 arguments in order operationType,symbol,price", operationType);
				LOGGER.error(message);
				throw new Exception(message);
			}
			stockService.calculatePERatio(args[1], Double.valueOf(args[2]));
			break;
		}
		case VWSP: {
			if(args.length<2){
				String message = String.format("Operation %s needs 2 arguments in order operationType,symbol", operationType);
				LOGGER.error(message);
				throw new Exception(message);
			}
			stockService.calculateVolumeWeightedStockPrice(args[1]);
			break;
		}
		case GM: {
			stockService.calculateGBCEAllShareIndex();
			break;
		}
		case ADD_TRADE:{
			if(args.length<5){
				String message = String.format("Operation %s needs 5 arguments in following order operationType,symbol,quantityOfShares,tradePrice,indicator", operationType);
				LOGGER.error(message);
				throw new Exception(message);
			}
			Trade trade = new Trade();
			trade.setSymbol(args[1]);
			trade.setQuantityOfShares(Double.valueOf(args[2]));
			trade.setTradedPrice(Double.valueOf(args[3]));
			trade.setIndicator(TradeIndicator.valueOf(args[4]));
			stockService.addTrade(trade);
			break;
		}
		default:
			LOGGER.info("No implemenation found for operationType :{}",operationType);
			break;
		}
	}

}
