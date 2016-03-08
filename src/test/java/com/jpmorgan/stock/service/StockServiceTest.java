package com.jpmorgan.stock.service;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.jpmorgan.stock.exception.PriceNullException;
import com.jpmorgan.stock.exception.StockNotFoundException;
import com.jpmorgan.stock.model.Trade;
import com.jpmorgan.stock.model.TradeIndicator;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:context/spring-app.xml")
public class StockServiceTest {

	@Autowired
	private IStockService stockService;
	
	@Before
	public void before() throws Exception{
		stockService.clearTrades();
	}
	
	@Test
	public void testYieldForStockTypeCommon() throws StockNotFoundException, PriceNullException{
		Assert.assertEquals(new Double(0), stockService.calculateYield("TEA", 20d));
		Assert.assertEquals(new Double(8d/20d), stockService.calculateYield("POP", 20d));
	}
	
	@Test
	public void testYieldForStockTypePreferred() throws StockNotFoundException, PriceNullException{
		Assert.assertEquals(new Double(10d), stockService.calculateYield("GIN", 20d));
	}
	
	@Test(expected=StockNotFoundException.class)
	public void testYieldForNonExistentStock() throws StockNotFoundException, PriceNullException{
		Assert.assertEquals(new Double(10d), stockService.calculateYield("ABB", 20d));
	}
	
	@Test
	public void testPERatioWithDividentZero() throws StockNotFoundException, PriceNullException{
		Assert.assertEquals(new Double(0d), stockService.calculatePERatio("TEA", 20d));
	}
	
	@Test
	public void testPEWithNonZeroDivident() throws StockNotFoundException, PriceNullException{
		Assert.assertEquals(new Double(2.5d), stockService.calculatePERatio("POP", 20d));
	}
	
	@Test
	public void testAddTrades() throws Exception{
		Trade trade = new Trade();
		trade.setIndicator(TradeIndicator.BUY);
		trade.setQuantityOfShares(10d);
		trade.setSymbol("TEA");
		trade.setTradedPrice(20d);
		stockService.addTrade(trade);
	}
	
	@Test(expected=StockNotFoundException.class)
	public void testAddTradesWithNonExistentStock() throws Exception{
		Trade trade = new Trade();
		trade.setIndicator(TradeIndicator.BUY);
		trade.setQuantityOfShares(10d);
		trade.setSymbol("BBB");
		trade.setTradedPrice(20d);
		stockService.addTrade(trade);
	}
	
	@Test
	public void testVWSP() throws Exception{
		/**
		 * Trade 1
		 */
		Trade trade = new Trade();
		trade.setIndicator(TradeIndicator.BUY);
		trade.setQuantityOfShares(10d);
		trade.setSymbol("TEA");
		trade.setTradedPrice(20d);
		stockService.addTrade(trade);
		
		/**
		 * Trade 2
		 */
		Trade trade2 = new Trade();
		trade2.setIndicator(TradeIndicator.SELL);
		trade2.setQuantityOfShares(10d);
		trade2.setSymbol("TEA");
		trade2.setTradedPrice(15d);
		stockService.addTrade(trade2);
		
		Assert.assertEquals(new Double(17.5d),stockService.calculateVolumeWeightedStockPrice("TEA"));
		Assert.assertEquals(new Double(0d),stockService.calculateVolumeWeightedStockPrice("POP"));
	}
	
	@Test
	public void testGM() throws Exception{
		/**
		 * Trade 1
		 */
		Trade trade = new Trade();
		trade.setIndicator(TradeIndicator.BUY);
		trade.setQuantityOfShares(10d);
		trade.setSymbol("TEA");
		trade.setTradedPrice(12d);
		stockService.addTrade(trade);
		
		/**
		 * Trade 2
		 */
		Trade trade2 = new Trade();
		trade2.setIndicator(TradeIndicator.BUY);
		trade2.setQuantityOfShares(11d);
		trade2.setSymbol("POP");
		trade2.setTradedPrice(3d);
		stockService.addTrade(trade2);
		
		/**
		 * Trade 3
		 */
		Trade trade3 = new Trade();
		trade3.setIndicator(TradeIndicator.BUY);
		trade3.setQuantityOfShares(12d);
		trade3.setSymbol("GIN");
		trade3.setTradedPrice(6d);
		stockService.addTrade(trade3);
		
		Assert.assertEquals(new Double(6d), Double.valueOf(
				new BigDecimal(stockService.calculateGBCEAllShareIndex()).
				setScale(3 , RoundingMode.FLOOR).doubleValue()));
		
	}
	
	
	
}
