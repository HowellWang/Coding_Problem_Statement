package com.YuhaoWang.csv;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertNull;

/**
 * Created by yuhaowang on 4/17/18.
 */
public class CSVDataWarehouseTest {

    CSVDataWarehouse csvDataWarehouse;

    @Before
    public void setUp() throws Exception {
        csvDataWarehouse = new CSVDataWarehouse();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInit1() {
        csvDataWarehouse.init(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInit2() {
        csvDataWarehouse.init("");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testZipcode1() {
        csvDataWarehouse.getYoungestDozenFromAZipCode(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testZipcode2() {
        csvDataWarehouse.getYoungestDozenFromAZipCode("");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testWriteProtobuf1() {
        csvDataWarehouse.writeToProtoBuf(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testWriteProtobuf2() {
        csvDataWarehouse.writeToProtoBuf("");
    }

    @After
    public void tearDown() throws Exception {
        csvDataWarehouse = null;
        assertNull(csvDataWarehouse);
    }

}