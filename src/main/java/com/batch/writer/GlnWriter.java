package com.batch.writer;

import org.springframework.stereotype.Component;

import com.batch.excel.vo.CustomerGln;
import com.batch.process.DataProcess;
import com.batch.service.CommonsServices;
import com.batch.util.LoggerUtil;

@Component
public class GlnWriter extends CommonsServices implements IWrite<Long> {
	
	private LoggerUtil logger = LoggerUtil.getInstance();
	
	public Long writeData(Object item, Long idMassiveLoad) {
		super.setIdMassiveLoad(idMassiveLoad);
		DataProcess process = (DataProcess) item;
		CustomerGln customerGln = (CustomerGln) process.getData();
		
		writeLog("Customer GLN");
		writeLog("--> Code: [" + customerGln.getCustomerCode() + "]");
		writeLog("--> NameStore: [" + customerGln.getNameStore() + "]");
		writeLog("--> GLN code: [" + customerGln.getGlnCode()+ "]");

		return null;
	}
	
	private void writeLog(String message){
    	logger.write(getClass(), message);
    }
}	
