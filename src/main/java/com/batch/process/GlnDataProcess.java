package com.batch.process;

import org.springframework.stereotype.Component;

import com.batch.excel.model.Sheet;
import com.batch.service.GlnServices;
import com.batch.util.LoggerUtil;
import com.bimbo.cuc.view.model.CustomerGlnVO;

@Component
public class GlnDataProcess extends GlnServices implements IProcessor<DataProcess>{
	
	private LoggerUtil logger = LoggerUtil.getInstance();
	
	public DataProcess setData(Sheet item, Long idMassiveLoad) {
		super.setIdMassiveLoad(idMassiveLoad);
		
		DataProcess process = new DataProcess(); 
		
		writeLog("In GlnDataProcess.");
		
		CustomerGlnVO customerTemp = new CustomerGlnVO();

		process.setData(customerTemp);
		process.setSheetName(item.getName());
		
		return process;
	}

	private void writeLog(String message){
    	logger.write(getClass(), message);
    }
}
