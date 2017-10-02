package com.batch.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class Utils {
	
	private static Long numberOfLineRead;
	
	public static final String ID_MASSIVE_LOAD = "idMassiveLoad=";
	public static final String PATH = "resource=file:";

	public static final String OK = "OK";
	
	private static SimpleDateFormat dateFormat = new SimpleDateFormat("YYYY-MM-DD");
	
	public enum BATCH_CHUNK{
		READER("READER"),
		PROCESSOR("PROCESSOR"),
		WRITER("WRITER");
		
		private final String chunk;
		private BATCH_CHUNK(final String chunk) {
	        this.chunk = chunk;
	    }
	    public String getValue() {
	        return chunk;
	    }
	}
	
	public enum BATCH_PROCESS{
		CUSTOMER("CustomerTempDataProcess"),
		GLN("GlnDataProcess");
		
		private final String process;
		private BATCH_PROCESS(final String process) {
	        this.process = process;
	    }
	    public String getValue() {
	        return process;
	    }
	}
	
	public enum BATCH_WRITE{
		CUSTOMER("CustomerTempWriter"),
		GLN("GlnWriter");
		
		private final String write;
		private BATCH_WRITE(final String write) {
	        this.write = write;
	    }
	    public String getValue() {
	        return write;
	    }
	}
	
	public static void setTotalNumberOfRows(long numberOfLineRead_){
		if(numberOfLineRead == null){
			numberOfLineRead = (long) Math.ceil((numberOfLineRead_ / 10));
		}
	}
	
	public static boolean isWrite(long currentRow, long totalNumberOfRows){
		if(numberOfLineRead == 0 || currentRow % numberOfLineRead == 0 || currentRow == totalNumberOfRows){
			return Boolean.TRUE;
		}
		return Boolean.FALSE;
	}
	
	public static SimpleDateFormat dateFormat(){
		return dateFormat;
	}
	
	public static String getDateHHMMSSMLS(){
		Calendar calendar = GregorianCalendar.getInstance();
		return "" + calendar.get(Calendar.HOUR) + calendar.get(Calendar.MINUTE) + calendar.get(Calendar.SECOND) + calendar.get(Calendar.MILLISECOND); 
	}
	
	public static boolean isInteger(String value) {
	      boolean isValid = true;
	      try{
	         Integer.parseInt(value);
	      }
	      catch (NumberFormatException ex){
	    	  isValid = false;
	      }
	 
	      return isValid;
	}
	
	public static boolean isLong(String value) {
	      boolean isValid = true;
	      try{
	         Long.parseLong(value);
	      }
	      catch (NumberFormatException ex){
	    	  isValid = false;
	      }
	 
	      return isValid;
	}
	
	public static boolean isDouble(String value) {
	      boolean isValid = true;
	      try{
	         Double.parseDouble(value);
	      }
	      catch (NumberFormatException ex){
	    	  isValid = false;
	      }
	 
	      return isValid;
	}
}
