package com.batch.excel.support.rowset;

import com.batch.excel.Sheet;

/**
 * Contract for extracting column names for a given {@code sheet}.
 *
 */
public interface ColumnNameExtractor {

    /**
     * Retrieves the names of the columns in the given Sheet.
     *
     * @param sheet the sheet
     * @return the column names
     */
    String[] getColumnNames(Sheet sheet);

}
