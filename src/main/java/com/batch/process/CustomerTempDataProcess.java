package com.batch.process;

import org.springframework.stereotype.Component;

import com.batch.excel.model.Sheet;
import com.batch.service.CustomerServices;
import com.batch.util.LoggerUtil;
import com.bimbo.cuc.model.customer.CustomerTemp;

@Component
public class CustomerTempDataProcess extends CustomerServices implements
		IProcessor<DataProcess> {

	private LoggerUtil logger = LoggerUtil.getInstance();
	
	public DataProcess setData(Sheet item, Long idMassiveLoad) {
		
		super.setIdMassiveLoad(idMassiveLoad);
		
		DataProcess process = new DataProcess();

		writeLog("In CustomerTempDataProcess");

		CustomerTemp customerTemp = new CustomerTemp();

		process.setData(customerTemp);
		process.setSheetName(item.getName());
		
		return process;
	}

	private void writeLog(String message) {
		logger.write(getClass(), message);
	}

}
