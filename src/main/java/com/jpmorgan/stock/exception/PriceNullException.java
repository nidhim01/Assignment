package com.jpmorgan.stock.exception;

public class PriceNullException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7500981820705200794L;

	public PriceNullException(String message){
		super(message);
	}
}
