package com.batch.util;

import org.apache.log4j.Logger;

public class LoggerUtil {
	
	private Logger logger = Logger.getLogger(getClass());
	private static LoggerUtil loggerUtil;  
	
	private LoggerUtil(){ }
	
	public static LoggerUtil getInstance(){
		if(loggerUtil == null){
			loggerUtil = new LoggerUtil(); 
		}
		return loggerUtil;
	}
	
	public void write(Class clazz, String message){
		message = "[" + clazz.getName() + "] - " + message;
		if(logger.isDebugEnabled()){
			logger.debug( message);
		}else
		if(logger.isInfoEnabled()){
			logger.info(message);
		}else
		if(logger.isTraceEnabled()){
			logger.trace(message);
		}
	}

}
