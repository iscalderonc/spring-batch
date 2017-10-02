package com.batch.excel.layout;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.batch.excel.model.Cell;


@Component
public class DefinitionLayoutFile {
	
	private static Map<String, Map<String,Cell>> mapWorkbook = new HashMap<String, Map<String,Cell>>();
	
	static{
	
		// Sheet [Customer]
		Map<String,Cell> mapCustomerColumn = new HashMap<String,Cell>();
		mapCustomerColumn.put("CUSTOMER_CODE", new Cell("CUSTOMER_CODE", new Long(0), Boolean.TRUE));
		mapCustomerColumn.put("NAME_STORE", new Cell("NAME_STORE", new String(), Boolean.TRUE));
		mapCustomerColumn.put("PHONE", new Cell("PHONE_1", new String(), Boolean.TRUE));
		mapCustomerColumn.put("EMAIL", new Cell("EMAIL", new String(), Boolean.TRUE));
		
		// Sheet [GLN]
		Map<String,Cell> mapGlnColumn = new HashMap<String,Cell>();
		mapGlnColumn.put("CUSTOMER_CODE", new Cell("CUSTOMER_CODE", new Long(0), Boolean.TRUE));
		mapGlnColumn.put("NAME_STORE", new Cell("NAME_STORE", new String(), Boolean.TRUE));
		mapGlnColumn.put("GLN_CODE", new Cell("GLN_CODE", new String(), Boolean.TRUE));
		mapGlnColumn.put("GLN_VALUE", new Cell("GLN_VALUE", new String(), Boolean.TRUE));
		
		
		mapWorkbook.put("Customer", mapCustomerColumn);
		mapWorkbook.put("GLN", mapGlnColumn);
	}
	
	public Map<String, Map<String,Cell>> getMapWorkbook(){
		return mapWorkbook;
	}

}
