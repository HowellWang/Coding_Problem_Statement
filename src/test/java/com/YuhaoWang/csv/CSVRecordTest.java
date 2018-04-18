package com.YuhaoWang.csv;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * Created by yuhaowang on 4/17/18.
 */
public class CSVRecordTest {

    CSVRecord csvRecord;

    @Before
    public void setUp() throws Exception {
        Map<String, Integer> map = new HashMap<>();
        map.put("name", 0);
        map.put("city", 1);
        map.put("address", 2);
        map.put("age", 3);
        map.put("city1", 4);
        map.put("zipcode", 5);
        String[] values = {"John Smith", "New York", "3 South Sherman Street", "35", "New York", "10028"};
        csvRecord = new CSVRecord(values, map);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetByIndex1() {
        csvRecord.get(-2);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetByIndex2() {
        csvRecord.get(6);
    }

    @Test
    public void testGetByIndex3() {
        assertEquals(csvRecord.get(1), "New York");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetByColumn1() {
        csvRecord.get("state");
    }

    @Test
    public void testGetByColumn2() {
        assertEquals(csvRecord.get("zipcode"), "10028");
    }

    @Test
    public void testSize() {
        assert csvRecord.size() == 6;
    }

    @After
    public void tearDown() throws Exception {
        csvRecord = null;
        assertNull(csvRecord);
    }

}