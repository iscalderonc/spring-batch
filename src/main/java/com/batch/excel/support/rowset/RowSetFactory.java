package com.batch.excel.support.rowset;

import com.batch.excel.Sheet;

/**
 * Contract for factories which will construct a {@code RowSet} implementation.
 *
 */
public interface RowSetFactory {

    /**
     * Create a rowset instance.
     *
     * @param sheet an Excel sheet.
     * @return a RowSet instance.
     */
    RowSet create(Sheet sheet);
}
