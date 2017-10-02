package com.batch.excel.support.rowset;

import com.batch.excel.Sheet;

/**
 * {@code RowSetFactory} implementation which constructs a {@code DefaultRowSet} instance and
 * {@code DefaultRowSetMetaData} instance. The latter will have the {@code ColumnNameExtractor} configured
 * on this factory set (default {@code RowNumberColumnNameExtractor}.
 *
 */
public class DefaultRowSetFactory implements RowSetFactory {

    private ColumnNameExtractor columnNameExtractor = new RowNumberColumnNameExtractor();

    public RowSet create(Sheet sheet) {
        DefaultRowSetMetaData metaData = new DefaultRowSetMetaData(sheet);
        metaData.setColumnNameExtractor(columnNameExtractor);
        return new DefaultRowSet(sheet, metaData);
    }

    public void setColumnNameExtractor(ColumnNameExtractor columnNameExtractor) {
        this.columnNameExtractor = columnNameExtractor;
    }
}
