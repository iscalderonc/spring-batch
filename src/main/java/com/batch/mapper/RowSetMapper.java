package com.batch.mapper;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Map;

import org.apache.commons.lang.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import com.batch.excel.RowMapper;
import com.batch.excel.layout.DefinitionLayoutFile;
import com.batch.excel.model.Cell;
import com.batch.excel.model.Sheet;
import com.batch.excel.support.rowset.RowSet;
import com.batch.exception.WorkBookException;
import com.batch.util.LoggerUtil;
import com.batch.util.Utils;

/**
 * Pass through {@link RowMapper} useful for passing the orginal String[]
 * back directly rather than a mapped object.
 *
 */
public class RowSetMapper implements RowMapper<Sheet>  {
	
	@Autowired
	DefinitionLayoutFile definitionLayoutFile;
	
	private Long idMassiveLoad;
	private LoggerUtil logger = LoggerUtil.getInstance();
	
    public Sheet mapRow(final RowSet rs, final Long idMassiveLoad_) throws Exception {
    	
    	idMassiveLoad = idMassiveLoad_;
    	Sheet sh = new Sheet(rs.getMetaData().getSheetName());
    	Utils.setTotalNumberOfRows(rs.getNumberOfRows());
    	
    	sh.setCurrentRow(rs.getCurrentRowIndex());
    	sh.setNumberOfRows(rs.getNumberOfRows());
    	sh.setCell(new ArrayList<Cell>());
		for(int i=0; i < rs.getMetaData().getColumnCount(); i++){
	    	sh.getCell().add(new Cell(rs.getMetaData().getColumnName(i), rs.getColumnValue(i)));
		}
		
		if(validateRow(sh, rs.getCurrentRowIndex())){
			;
		}
		
		if(Utils.isWrite(rs.getCurrentRowIndex(), rs.getNumberOfRows())){
    		String message = "CurrentSheetName[" + rs.getMetaData().getSheetName() + "] - CurrentRowIndex[" +  rs.getCurrentRowIndex() + " of " + (rs.getNumberOfRows()-1)+ "]"; 
    		writeMessage(sh, rs.getCurrentRowIndex(), message);
    	}
		
		return sh;
    }
    
    private boolean validateRow(Sheet sheet, int currentRowIndex) throws WorkBookException{
    	
    	writeLog("Start Validate Row");
    	
    	boolean validRow = Boolean.TRUE.booleanValue();
    	
    	Map<String,Cell> mapWB = null;
    	
    		mapWB = definitionLayoutFile.getMapWorkbook().get(sheet.getName());
    		
    		writeLog("Sheet [" + sheet.getName() + "]");
    		
    		// Para Probar: En el Array agregar la hoja a validar. En el break, poner el "breakpoint"
			// 			    De no haber hojas no pasaria nada.
			String []sheetTest = {"Customer"};
			for(String st: sheetTest){
				if(st.equals(sheet.getName())){
					break;
				}
			}
    	
	    	for(Cell cellIn : sheet.getCell()){
	    		Cell cellMap = null;
	    		if(mapWB.containsKey(cellIn.getName())){
	    			cellMap = mapWB.get(cellIn.getName());
	    		}
	    		
	    		if(cellMap == null){
	    				throw new WorkBookException("Column Unidentified [" + cellIn.getName() + "]");
	    		}else{
		    			// Para Probar: En el Array agregar las columnas a validar. En el break, poner el "breakpoint" 
		    			// 			    De no haber columnas no pasaria nada.
		    			String []columnsTest = {"CODE"};
		    			for(String colum: columnsTest){
		    				if(colum.equals(cellMap.getName())){
		    					break;
		    				}
		    			}
	    			
		    				boolean valid = Boolean.TRUE.booleanValue();
		    				String value = (String)cellIn.getValue();
		    				
		    				if(cellMap.getType() instanceof java.lang.String){
		    					if(StringUtils.hasText(value)){
		    						cellIn.setValue(value);
		    					}else{
		    						valid = Boolean.FALSE.booleanValue();
		    					}
		    				}else
		    				if(cellMap.getType() instanceof java.lang.Integer || cellMap.getType() instanceof java.lang.Long){
		    					
		    					if(NumberUtils.isNumber(value)){
		    						if(cellMap.getType() instanceof java.lang.Integer){
		    							if(Utils.isInteger(value)){
		    								cellIn.setValue(Integer.parseInt(value));
		    							}else{
		    								valid = Boolean.FALSE.booleanValue();		
		    							}
		    						}else{
		    							if(Utils.isLong(value)){
		    								cellIn.setValue(Long.parseLong(value));
		    							}else{
		    								valid = Boolean.FALSE.booleanValue();		
		    							}
		    						}
		    					}else{
		    						valid = Boolean.FALSE.booleanValue();
		    					}
		    					
		    				}else
	    					if(cellMap.getType() instanceof java.lang.Double ){
	    						
	    						if(Utils.isDouble(value)){
	    							cellIn.setValue(Double.parseDouble(value));
	    						}else{	
	    							valid = Boolean.FALSE.booleanValue();
	    						}
	    						
		    				}else
		    				if(cellMap.getType() instanceof java.util.Date){
		    					try{
		    						if(StringUtils.hasLength(value)){
		    							cellIn.setValue(Utils.dateFormat().parse(value));
		    						}
		    					}catch(ParseException p){
		    						valid = Boolean.FALSE.booleanValue();
		    					}
		    				}
		    				
		    				if(!valid){
		    					if(cellMap.isRequired()){
		    						String message = "Record is not considered due to the lack of important information on the Sheet [" + sheet.getName() + "] in the Field [" +  cellMap.getName() + "].";
		    			    		writeMessage(sheet, currentRowIndex, message);
		    			    		validRow = false;
		    					}
		    				}
		    				cellIn.setValid(valid);
	    		}
	    	}

	    	writeLog("Finish Validate Row.");
    	
    	return validRow;
    }
    
    private void writeLog(String message){
    	logger.write(getClass(), message);
    }
    
    private void writeLogger(Object customerCode, String message) {
		logger.write(getClass(), String.valueOf(customerCode) + message);
	}
    
    private String getCodeMessage(Sheet sheet, int currentRowIndex){
    	String code = "";
		Cell cell = sheet.getCellQueryItem("CUSTOMER_CODE");
		
		if(cell != null && cell.getValue() != null){
			code = cell.getValue().toString();
		}else{
			code = "Row [" +  currentRowIndex + "]";
		}
		
		return code;
    }
    
    private void writeMessage(Sheet sh, int currentRowIndex, String message){
		writeLog(message);
		writeLogger(getCodeMessage(sh,currentRowIndex), message);
    	
    }
}
