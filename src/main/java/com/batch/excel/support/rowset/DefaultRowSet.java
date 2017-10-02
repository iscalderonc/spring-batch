package com.batch.excel.support.rowset;

import java.util.Properties;

import com.batch.excel.Sheet;

/**
 * Default implementation of the {@code RowSet} interface.
 *
 * @see com.batch.excel.support.rowset.DefaultRowSetFactory
 */
public class DefaultRowSet implements RowSet {

    private final Sheet sheet;
    private final RowSetMetaData metaData;

    private int currentRowIndex = -1;
    private String[] currentRow;

    DefaultRowSet(Sheet sheet, RowSetMetaData metaData) {
        this.sheet = sheet;
        this.metaData = metaData;
    }

    public RowSetMetaData getMetaData() {
        return metaData;
    }

    public boolean next() {
        currentRow = null;
        currentRowIndex++;
        if (currentRowIndex < sheet.getNumberOfRows()) {
            currentRow = sheet.getRow(currentRowIndex);
            return true;
        }
        return false;
    }

    public int getCurrentRowIndex() {
        return this.currentRowIndex;
    }

    public String[] getCurrentRow() {
        return this.currentRow;
    }

    public String getColumnValue(int idx) {
        return currentRow[idx];
    }

    public Properties getProperties() {
        final String[] names = metaData.getColumnNames();
        if (names == null) {
            throw new IllegalStateException("Cannot create properties without meta data");
        }

        Properties props = new Properties();
        for (int i = 0; i < currentRow.length; i++) {
            String value = currentRow[i];
            if (value != null) {
                props.setProperty(names[i], value);
            }
        }
        return props;
    }
    
    public long getNumberOfRows(){
    	return sheet.getNumberOfRows();
    } 
}
