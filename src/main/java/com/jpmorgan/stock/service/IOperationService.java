package com.jpmorgan.stock.service;

import com.jpmorgan.stock.model.OperationType;

public interface IOperationService {

	public void doOperation (OperationType operationType , String [] args) throws Exception;
	
}
