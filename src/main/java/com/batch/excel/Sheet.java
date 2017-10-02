package com.batch.excel;

/**
 * Interface to wrap different Excel implementations like JExcel, JXL or Apache POI.
 *
 */
public interface Sheet {

    /**
     * Get the number of rows in this sheet.
     *
     * @return the number of rows.
     */
    long getNumberOfRows();

    /**
     * Get the name of the sheet.
     *
     * @return the name of the sheet.
     */
    String getName();

    /**
     * Get the row as a String[]. Returns null if the row doesn't exist.
     *
     * @param rowNumber the row number to read.
     * @return a String[] or null
     */
    String[] getRow(int rowNumber);

    /**
     * The number of columns in this sheet.
     *
     * @return number of columns
     */
    int getNumberOfColumns();
}
