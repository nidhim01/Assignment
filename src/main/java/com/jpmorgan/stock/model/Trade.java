package com.jpmorgan.stock.model;

import java.util.Date;

public class Trade {
	private String symbol;
	private Double quantityOfShares;
	private TradeIndicator indicator;
	private Double tradedPrice;
	private Date tradedTime;
	public String getSymbol() {
		return symbol;
	}
	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}
	public Double getQuantityOfShares() {
		return quantityOfShares;
	}
	public void setQuantityOfShares(Double quantityOfShares) {
		this.quantityOfShares = quantityOfShares;
	}
	public TradeIndicator getIndicator() {
		return indicator;
	}
	public void setIndicator(TradeIndicator indicator) {
		this.indicator = indicator;
	}
	public Double getTradedPrice() {
		return tradedPrice;
	}
	public void setTradedPrice(Double tradedPrice) {
		this.tradedPrice = tradedPrice;
	}
	public Date getTradedTime() {
		return tradedTime;
	}
	public void setTradedTime(Date tradedTime) {
		this.tradedTime = tradedTime;
	}
	

}
