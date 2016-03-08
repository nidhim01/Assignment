package com.jpmorgan.stock.app;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.jpmorgan.stock.model.OperationType;
import com.jpmorgan.stock.service.IOperationService;

public class AppBootstrap {

	private static final Logger LOGGER = LoggerFactory.getLogger(AppBootstrap.class);

	@SuppressWarnings("resource")
	public static void main(String[] args) throws Exception {
		ApplicationContext context = new ClassPathXmlApplicationContext("classpath:context/spring-app.xml");

		try {
			LOGGER.info("Enter one of the operation along with arguments to begin {} ",
					OperationType.listOfOperation());
			LOGGER.info("Press Enter to Exit ");
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			String input = null;
			while ((input = br.readLine()) != null) {
				if("".equals(input)){
					LOGGER.info("Exiting system ");
					System.exit(0);
				}
				args = input.split(" ");
				for(String arg : args){
					arg = arg.trim();
				}
				OperationType operationType = OperationType.findByType(args.length>0? args[0] :"");
				if(operationType!=null){
					IOperationService stockService = context.getBean(IOperationService.class);
					try{
						stockService.doOperation(operationType, args);
					}catch(Exception e){
						LOGGER.error("Error ::", e);
					}
				}else{
					LOGGER.info("Please supply appropriate Operation Type , possible values :{}",OperationType.listOfOperation());
				}
			}

		} catch (Exception e) {
			LOGGER.error("Error ::", e);
		}

	}

}
