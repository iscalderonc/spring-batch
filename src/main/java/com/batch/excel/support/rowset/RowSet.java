package com.batch.excel.support.rowset;

import java.util.Properties;

/**
 * Used by the {@code org.springframework.batch.item.excel.AbstractExcelItemReader} to abstract away
 * the complexities of the underlying Excel API implementations.
 *
 */
public interface RowSet {

    /**
     * Retrieves the meta data (name of the sheet, number of columns, names) of this row set.
     *
     * @return a corresponding {@code RowSetMetaData} instance.
     */
    RowSetMetaData getMetaData();


    /**
     * Move to the next row in the document.
     *
     * @return true if the row is valid, false if there are no more rows
     */
    boolean next();

    /**
     * Returns the current row number
     *
     * @return the current row number
     */
    int getCurrentRowIndex();

    /**
     * Return the current row as a String[].
     *
     * @return the row as a String[]
     */
    String[] getCurrentRow();

    /**
     * Retrieves the value of the indicated column in the current row as a String object.
     *
     * @param idx the column index, 0 based
     * @return a String objeect respresenting the column value.
     */
    String getColumnValue(int idx);


    /**
     * Construct name-value pairs from the column names and string values. Null
     * values are omitted.
     *
     * @return some properties representing the row set.
     * @throws IllegalStateException if the column name meta data is not
     *                               available.
     */
    Properties getProperties();
    
    /**
     * Retrieves the Number of Rows.
     *
     * @return a int representing the NumberOfRows.
     */
    public long getNumberOfRows();
}
