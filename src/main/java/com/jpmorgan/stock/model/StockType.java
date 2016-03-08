package com.jpmorgan.stock.model;

public enum StockType {
	COMMON("Common"), PREFERRED("preferred");
	
	private String description;
	
	private StockType(String description){
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	
}
