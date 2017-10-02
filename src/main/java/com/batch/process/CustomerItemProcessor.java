package com.batch.process;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import com.batch.excel.model.Sheet;
import com.batch.service.CommonsServices;
import com.batch.util.LoggerUtil;

public class CustomerItemProcessor extends CommonsServices implements ItemProcessor<Sheet, Object>{
	
	private LoggerUtil logger = LoggerUtil.getInstance();
	private Long idMassiveLoad;
	
	@Autowired
	private ApplicationContext context;
	
	public Object process(Sheet item) throws Exception {
		
		Object objResponse = null;
		IProcessor proc = null;
		
		proc = (IProcessor) context.getBean(getBean("process", item.getName()));
		
		/*
		if(item.getName().equals("Customer")){
			proc = (CustomerTempDataProcess) context.getBean("customerTempDataProcess");
		}else
		if(item.getName().equals("GLN")){
			proc = (GlnDataProcess) context.getBean("glnDataProcess");
		}
		
		*/
		
		writeLog("Load Class <<" + proc.getClass().getName() + ">>.");
		objResponse = proc.setData(item, idMassiveLoad);
		
		return objResponse;
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
