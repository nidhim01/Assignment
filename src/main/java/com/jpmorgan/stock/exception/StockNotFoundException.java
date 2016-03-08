package com.jpmorgan.stock.exception;

public class StockNotFoundException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4457422862998846972L;

	public StockNotFoundException(String message){
		super(message);
	}

}
