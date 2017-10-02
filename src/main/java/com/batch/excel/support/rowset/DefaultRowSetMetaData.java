package com.batch.excel.support.rowset;

import com.batch.excel.Sheet;

/**
 * Default implementation for the {@code RowSetMetaData} interface.
 *
 * Requires a {@code Sheet} and {@code ColumnNameExtractor} to operate correctly.
 * Delegates the retrieval of the column names to the {@code ColumnNameExtractor}.
 *
 */
public class DefaultRowSetMetaData implements RowSetMetaData {

    private final Sheet sheet;

    private ColumnNameExtractor columnNameExtractor;

    DefaultRowSetMetaData(Sheet sheet) {
        this.sheet = sheet;
    }

    public String[] getColumnNames() {
        return columnNameExtractor.getColumnNames(sheet);
    }

    public String getColumnName(int idx) {
        String[] names = getColumnNames();
        return names[idx];
    }

    public int getColumnCount() {
        return sheet.getNumberOfColumns();
    }

    public String getSheetName() {
        return sheet.getName();
    }

    public void setColumnNameExtractor(ColumnNameExtractor columnNameExtractor) {
        this.columnNameExtractor = columnNameExtractor;
    }
}
