package com.batch.reader;

import java.io.Closeable;
import java.io.InputStream;
import java.io.PushbackInputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.batch.item.file.ResourceAwareItemReaderItemStream;
import org.springframework.batch.item.support.AbstractItemCountingItemStreamItemReader;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;

import com.batch.excel.ExcelFileParseException;
import com.batch.excel.RowCallbackHandler;
import com.batch.excel.RowMapper;
import com.batch.excel.Sheet;
import com.batch.excel.layout.DefinitionLayoutFile;
import com.batch.excel.poi.PoiSheet;
import com.batch.excel.support.rowset.DefaultRowSetFactory;
import com.batch.excel.support.rowset.RowSet;
import com.batch.excel.support.rowset.RowSetFactory;
import com.batch.exception.WorkBookException;
import com.batch.util.LoggerUtil;
import com.bimbo.cuc.admin.service.MassiveLoadLoggerService;
import com.bimbo.cuc.admin.service.MassiveLoadService;
import com.bimbo.cuc.admin.service.MassiveOperationCountryService;
import com.bimbo.cuc.model.admin.MassiveLoad;

/**
 * {@link org.springframework.batch.item.ItemReader} implementation to read an Excel
 * file. It will read the file sheet for sheet and row for row. It is loosy based on
 * the {@link org.springframework.batch.item.file.FlatFileItemReader}
 *
 * @param <T> the type
 */
public class ExcelPoiReader<T> extends AbstractItemCountingItemStreamItemReader<T> implements
        ResourceAwareItemReaderItemStream<T>, InitializingBean {
	
	private LoggerUtil logger = LoggerUtil.getInstance();

	private Workbook workbook;
	private InputStream workbookStream;
	private Long timeFinish;
    private Long timeInitial;
    
    private Long idMassiveLoad;
    private String fileName;
	
    private Resource resource;
    private int linesToSkip = 0;
    private int currentSheet = 0;
    private RowMapper<T> rowMapper;
    private RowCallbackHandler skippedRowsCallback;
    private boolean noInput = false;
    private boolean strict = true;
    private RowSetFactory rowSetFactory = new DefaultRowSetFactory();
    private RowSet rs;
    
    private static Map<Long,Set<String>> mapSheetAllow = new HashMap<Long, Set<String>>();
    
    @Autowired
	MassiveLoadService massivmLoadService;
    @Autowired
    MassiveLoadLoggerService massiveLoadLoggerService;
    @Autowired
    MassiveOperationCountryService massiveOperationCountryService;
    
    @Autowired
    DefinitionLayoutFile definitionLayoutFile; 
    
    private MassiveLoad massiveLoad;
    
    public ExcelPoiReader() {
        super();
        this.setName(ClassUtils.getShortName(this.getClass()));
    }

    /**
     * Retorna al rowMapper fila a fila leida
     *  
     * @return string corresponding to logical record according to
     * {@link #setRowMapper(RowMapper)} (might span multiple rows in file).
     */
    @Override
    protected T doRead() throws Exception {
    	
        if (this.noInput || this.rs == null) {
            return null;
        }

        if (rs.next()) {
            try {
                return this.rowMapper.mapRow(rs, idMassiveLoad);
            } catch (final Exception e) {
                throw new ExcelFileParseException("Exception parsing Excel file.", e, this.resource.getDescription(),
                        rs.getMetaData().getSheetName(), rs.getCurrentRowIndex(), rs.getCurrentRow());
            }
        } else {
        	if(nextSheet()){
        		return this.doRead();
        	}else{
        		return null;
        	}
        }
    }

    @Override
    protected void doOpen() throws Exception {
    	Long timeInitial = System.currentTimeMillis();
        Assert.notNull(this.resource, "Input resource must be set");
        writeLog("Resource: " + this.resource.getFilename());
        this.noInput = true;
        if (!this.resource.exists()) {
            if (this.strict) {
                throw new WorkBookException("Input resource must exist (reader is in 'strict' mode): " + this.resource);
            }
            throw new WorkBookException("Input resource does not exist '" + this.resource.getDescription() + "'.");
        }

        if (!this.resource.isReadable()) {
            if (this.strict) {
                throw new WorkBookException("Input resource must be readable (reader is in 'strict' mode): " + this.resource);
            }
            throw new WorkBookException("Input resource is not readable '" + this.resource.getDescription() + "'.");
        }

        writeLogger("Start reading the file");
        this.openExcelFile(this.resource,timeInitial);
        this.openSheet();
        this.noInput = false;
        writeLog("Opened workbook [" + this.resource.getFilename() + "] with " + this.getNumberOfSheets() + " sheets.");
    }
    
    @Override
    protected void doClose() throws Exception {
        // As of Apache POI 3.11 there is a close method on the Workbook, prior version
        // lack this method.
        if (workbook instanceof Closeable) {
            this.workbook.close();
            
            timeFinish = System.currentTimeMillis();
            writeLog("******************************************");
            writeLog("******************************************");
            writeLog("Time of reading the file [" + this.getResource().getFilename() + "]");
            writeLog("--> [" + (timeFinish - timeInitial) + "] milisecound.");
            writeLog("--> [" + ((timeFinish - timeInitial)/1000L) + "] secound.");
        }

        if (workbookStream != null) {
            workbookStream.close();
        }
        this.workbook=null;
        this.workbookStream=null;
    }

    private void openSheet() {
        final Sheet sheet = this.getSheet(this.currentSheet);

        if(getSheetsAllowed().contains(sheet.getName())){
	        this.rs =rowSetFactory.create(sheet);
	        writeLog("Opening sheet " + sheet.getName() + ".");
	
	        for (int i = 0; i < this.linesToSkip; i++) {
	            if (rs.next() && this.skippedRowsCallback != null) {
	                this.skippedRowsCallback.handleRow(rs);
	            }
	        }
	        writeLog("Openend sheet " + sheet.getName() + ", with " + sheet.getNumberOfRows() + " rows.");
        }else{
        	String message = "Sheet [" + sheet.getName() + "] will not be considered.";
        	writeLog(message);
    		writeLogger(message);
    		
    		nextSheet();
        }
    }
    
    private boolean nextSheet(){
    	this.currentSheet++;
        if (this.currentSheet >= this.getNumberOfSheets()) {
        	writeLog("No more sheets in '" + this.resource.getDescription() + "'.");
            return false;
        } else {
            this.openSheet();
            return true;
        }
    }
    
    /**
     * Public setter for the input resource.
     *
     * @param resource the {@code Resource} pointing to the Excelfile
     */
    public void setResource(final Resource resource) {
        this.resource = resource;
    }
    
    public Resource getResource() {
        return this.resource;
    }

    public void afterPropertiesSet() throws Exception {
        Assert.notNull(this.rowMapper, "RowMapper must be set");
    }

    /**
     * Set the number of lines to skip. This number is applied to all worksheet
     * in the excel file! default to 0
     *
     * @param linesToSkip number of lines to skip
     */
    public void setLinesToSkip(final int linesToSkip) {
        this.linesToSkip = linesToSkip;
    }

    /**
     *
     * @param sheet the sheet index
     * @return the sheet or <code>null</code> when no sheet available.
     */
    protected Sheet getSheet(int sheet){
    	return new PoiSheet(this.workbook.getSheetAt(sheet));
    }

    /**
     * The number of sheets in the underlying workbook.
     *
     * @return the number of sheets.
     */
    protected int getNumberOfSheets(){
    	return this.workbook.getNumberOfSheets();
    }
    
    /**
     * Open the underlying file using the {@code WorkbookFactory}. We keep track of the used {@code InputStream} so that
     * it can be closed cleanly on the end of reading the file. This to be able to release the resources used by
     * Apache POI.
     *
     * @param resource the {@code Resource} pointing to the Excel file.
     * @throws Exception is thrown for any errors.
     */
    protected void openExcelFile(final Resource resource,Long timeInitial) throws Exception {
    	this.timeInitial = timeInitial;
        workbookStream = resource.getInputStream();
        if (!workbookStream.markSupported() && !(workbookStream instanceof PushbackInputStream)) {
            throw new WorkBookException("InputStream MUST either support mark/reset, or be wrapped as a PushbackInputStream");
        }
        this.workbook = WorkbookFactory.create(workbookStream);
        this.workbook.setMissingCellPolicy(Row.CREATE_NULL_AS_BLANK);
        
    }
    
    private void writeLog(String message){
    	logger.write(getClass(), message);
    }
    
    private void writeLogger(String message){
    	logger.write(getClass(), message);
    }
    
    private Set<String> getSheetsAllowed(){
		
		Set<String> sheets = getSheetAllow(idMassiveLoad);

		if(sheets == null){
			sheets = definitionLayoutFile.getMapWorkbook().keySet();
			addSheet(idMassiveLoad, sheets);
		}
		
		return sheets;
	} 
	
	private Set<String> getSheetAllow(Long idMassiveLoad){
		if(mapSheetAllow.containsKey(idMassiveLoad)){
			return mapSheetAllow.get(idMassiveLoad);
		}
		return null;
	} 
	
	private void addSheet(Long idMassiveLoad, Set<String> sheets){
		mapSheetAllow.put(idMassiveLoad, sheets);
	}
    
    /**
     * In strict mode the reader will throw an exception on
     * {@link #open(org.springframework.batch.item.ExecutionContext)} if the input resource does not exist.
     *
     * @param strict true by default
     */
    public void setStrict(final boolean strict) {
        this.strict = strict;
    }

    /**
     * Public setter for the {@code rowMapper}. Used to map a row read from the underlying Excel workbook.
     *
     * @param rowMapper the {@code RowMapper} to use.
     */
    public void setRowMapper(final RowMapper<T> rowMapper) {
        this.rowMapper = rowMapper;
    }

    /**
     * Public setter for the <code>rowSetFactory</code>. Used to create a {@code RowSet} implemenation. By default the
     * {@code DefaultRowSetFactory} is used.
     *
     * @param rowSetFactory the {@code RowSetFactory} to use.
     */
    public void setRowSetFactory(RowSetFactory rowSetFactory) {
        this.rowSetFactory = rowSetFactory;
    }

    /**
     * @param skippedRowsCallback will be called for each one of the initial skipped lines before any items are read.
     */
    public void setSkippedRowsCallback(final RowCallbackHandler skippedRowsCallback) {
        this.skippedRowsCallback = skippedRowsCallback;
    }
    
    public Long getIdMassiveLoad() {
		return idMassiveLoad;
	}

	public void setIdMassiveLoad(Long idMassiveLoad) {
		this.idMassiveLoad = idMassiveLoad;
	}
    
	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
}
