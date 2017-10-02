package com.batch.writer;

import org.springframework.stereotype.Component;

import com.batch.excel.vo.CustomerTemp;
import com.batch.process.DataProcess;
import com.batch.service.CommonsServices;
import com.batch.util.LoggerUtil;

@Component
public class CustomerTempWriter extends CommonsServices implements IWrite<Long> {

	private LoggerUtil logger = LoggerUtil.getInstance();
	private Long idMassiveLoad;
	
	public Long writeData(Object item, Long idMassiveLoad){
		super.setIdMassiveLoad(idMassiveLoad);
		
		DataProcess process = (DataProcess) item;
		CustomerTemp customer = (CustomerTemp) process.getData();
		
		writeLog("Customer");
		writeLog("--> Code: [" + customer.getCustomerCode() + "]");
		writeLog("--> NameStore: [" + customer.getNameStore() + "]");
		writeLog("--> Phone: [" + customer.getPhone()+ "]");
		writeLog("--> Email: [" + customer.getEmail()+ "]");
		
		return null;
	}
	
	private void writeLog(String message){
    	logger.write(getClass(), message);
    }
	
	public Long getIdMassiveLoad() {
		return idMassiveLoad;
	}

	public void setIdMassiveLoad(Long idMassiveLoad) {
		this.idMassiveLoad = idMassiveLoad;
	}
}


