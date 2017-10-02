package com.batch.excel;

import com.batch.excel.support.rowset.RowSet;

/**
 * Callback to handle skipped lines. Useful for header/footer processing.
 *
 */
public interface RowCallbackHandler {

    void handleRow(RowSet rs);

}
