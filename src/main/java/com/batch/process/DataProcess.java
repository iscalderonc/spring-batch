package com.batch.process;

import java.util.List;

public class DataProcess {
	
	private String sheetName;
	private Object data;
	private List<Object> datas;
	
	public String getSheetName() {
		return sheetName;
	}
	public void setSheetName(String sheetName) {
		this.sheetName = sheetName;
	}
	public Object getData() {
		return data;
	}
	
	public void setData(Object data) {
		this.data = data;
	}
	
	public List<Object> getDatas() {
		return datas;
	}
	
	public void setDatas(List<Object> datas) {
		this.datas = datas;
	}
}
