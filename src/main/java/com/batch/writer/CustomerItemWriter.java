package com.batch.writer;

import java.util.List;

import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import com.batch.process.DataProcess;
import com.batch.service.CommonsServices;
import com.batch.util.LoggerUtil;

public class CustomerItemWriter extends CommonsServices implements ItemWriter<DataProcess> {

	@Autowired
	private ApplicationContext context;
	
	private LoggerUtil logger = LoggerUtil.getInstance();
	private Long idMassiveLoad;

	public void write(List<? extends DataProcess> items) throws Exception {
		
		for (DataProcess item : items) {
			
			IWrite proc =  (IWrite) context.getBean(getBean("write", item.getSheetName()));
			
			/*
			if(item instanceof CustomerTemp){
				proc = (CustomerTempWriter) context.getBean("customerTempWriter");
			}else
			if(item instanceof CustomerGlnVO){
				proc = (GlnWriter) context.getBean("glnWriter");
			}				
			*/
			
			writeLog("Load Class <<" + proc.getClass().getName() + ">>.");
			proc.writeData(item, idMassiveLoad);
		}
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
