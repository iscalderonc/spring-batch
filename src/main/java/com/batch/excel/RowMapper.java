package com.batch.excel;

import com.batch.excel.support.rowset.RowSet;

/**
 * Map rows from an excel sheet to an object.
 *
 * @param <T> the type
 */
public interface RowMapper<T> {

    /**
     * Implementations must implement this method to map the provided row to
     * the parameter type T.  The row number represents the number of rows
     * into a {@link Sheet} the current line resides.
     *
     * @param rs the RowSet used for mapping.
     * @return mapped object of type T
     * @throws Exception if error occured while parsing.
     */
    T mapRow(RowSet rs, Long idMassiveLoad) throws Exception;

}
