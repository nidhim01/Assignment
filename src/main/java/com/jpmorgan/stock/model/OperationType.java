package com.jpmorgan.stock.model;

public enum OperationType {
	YIELD("Calculate Yield"), PE("Calculate P/E Ration"), GM("Geometric Mean"), VWSP(
			"Calculate VolumeWeighted StockPrice"), ADD_TRADE("Add Trade");

	private String description;

	private OperationType(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public static String listOfOperation(){
		StringBuilder builder = new StringBuilder();
		for(OperationType type : OperationType.values()){
			builder.append("\n");
			builder.append("Type :"+type +" and description : "+type.description);
		}
		return builder.toString();
	}
	
	public static OperationType findByType(String value){
		OperationType matchedType = null;
		for(OperationType type: OperationType.values()){
			if(String.valueOf(type).equals(value)){
				matchedType = type;
			}
		}
		return matchedType;
	}
	
}
