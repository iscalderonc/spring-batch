package com.batch.process;

import java.util.Calendar;
import java.util.Date;

import com.batch.excel.model.Cell;
import com.batch.service.CommonsServices;

public class CommonsProcessor extends CommonsServices{
	
	public CommonsProcessor(){
		super();
	}
	
	public Object getValue(Cell cell){
		if(cell != null){
			if(cell.isValid()){
				return cell.getValue();
			}
		}
		return null;
	}
	
	public Date getStartDate(Date startDate) {
		if(startDate != null){
			return startDate;  
		}
		return Calendar.getInstance().getTime();
	}
	
	public Date getEndDate(Date endDate) {
		
		if(endDate != null){
			return endDate;
		}
		
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.YEAR, 100);
		
		return calendar.getTime();
	}
}
