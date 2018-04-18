package com.YuhaoWang.csv;

/**
 * Created by yuhaowang on 4/17/18.
 */
public class Main {
    public static void main(String[] args) {
        CSVDataWarehouse test = new CSVDataWarehouse();
        test.init("/Users/yuhaowang/Downloads/oa.csv");
        System.out.println(test.getYoungestDozenFromAZipCode("85067").toString());
        test.writeToProtoBuf("/Users/yuhaowang/Downloads/oa.txt");
    }
}
