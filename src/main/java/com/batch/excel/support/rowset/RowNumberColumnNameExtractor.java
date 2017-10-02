package com.batch.excel.support.rowset;

import com.batch.excel.Sheet;

/**
 * {@code ColumnNameExtractor} which returns the values of a given row (default is 0) as the column
 * names.
 *
 */
public class RowNumberColumnNameExtractor implements ColumnNameExtractor {

    private int headerRowNumber;

    public String[] getColumnNames(final Sheet sheet) {
        return sheet.getRow(headerRowNumber);
    }

    public void setHeaderRowNumber(int headerRowNumber) {
        this.headerRowNumber = headerRowNumber;
    }
}
