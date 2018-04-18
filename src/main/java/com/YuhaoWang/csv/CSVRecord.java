package com.YuhaoWang.csv;

import java.util.Map;

/**
 * Created by yuhaowang on 4/16/18.
 */
public final class CSVRecord {

    private final String[] values;

    final Map<String, Integer> map;



    CSVRecord(final String[] values, final Map<String, Integer> map) {
        this.values = values != null ? values : new String[0];
        this.map = map;
    }

    /**
     *
     * @param: index
     * @return the value at index "i" in the record
     */
    public String get(final int i) {
        if (i < 0 || i >= values.length) {
            throw new IllegalArgumentException("Invalid input, index is out of bound");
        }
        return values[i];
    }

    /**
     *
     * @param: columnName
     * @return value for the column named "columnOne"
     */
    public String get(final String columnOne) {
        Integer res = map.get(columnOne);
        if (res == null) {
            throw new IllegalArgumentException("Invalid input, there is no such column name");
        }

        return values[res];
    }


    /**
     *
     * @return the size of CSVRecord
     */

    public int size() {
        return values.length;
    }

}
