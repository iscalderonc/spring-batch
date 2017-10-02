package com.batch.excel.support.rowset;

import com.batch.excel.Sheet;

/**
 * {@code ColumnNameExtractor} implementation which returns a preset String[] to use as
 *  the column names. Useful for those situations in which an Excel file without a header row
 *  is read
 *
 */
public class StaticColumnNameExtractor implements ColumnNameExtractor {

    private final String[] columnNames;

    public StaticColumnNameExtractor(String[] columnNames) {
        this.columnNames = columnNames;
    }

    public String[] getColumnNames(Sheet sheet) {
        return this.columnNames;
    }

}
