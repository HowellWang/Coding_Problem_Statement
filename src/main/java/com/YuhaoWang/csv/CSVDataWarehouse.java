package com.YuhaoWang.csv;

import proto.Csv;
import org.json.JSONArray;

import java.io.*;
import java.util.*;

/**
 * Created by yuhaowang on 4/16/18.
 */
public final class CSVDataWarehouse {

    private List<CSVRecord> res;


    /**
     * initializes the class
     * @param: csv file path
     * @return List<CSVRecord>
     */

    public List<CSVRecord> init(String path) {

        if (path == null || path.isEmpty()) {
            throw new IllegalArgumentException("Invalid path");
        }

        res = new ArrayList<>();
        Map<String, Integer> map = new HashMap<>();
        BufferedReader br = null;
        String line = "";
        int count = 0;
        try {
            br = new BufferedReader(new FileReader(path));
            while ((line = br.readLine()) != null) {
                count++;
                String[] values = line.split(",");

                //check if the line is the headline
                if (count == 1) {
                    Set<String> set = new HashSet<>();
                    for (int i = 0; i < values.length; i++) {
                        if (set.add(values[i])) {
                            map.put(values[i], i);
                        } else {
                            values[i] = values[i] + "1";
                            map.put(values[i], i);
                        }
                    }
                } else {
                    if (values.length == 6) {
                        res.add(new CSVRecord(values, map));
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return res;
    }

    /**
     *
     * @param: zipcode
     * @return a list of CSVRecords in Json format but sorted based
     * on the age and filtered based on input zip code
     */

    public JSONArray getYoungestDozenFromAZipCode(String zipcode) {


        if (zipcode == null || zipcode.isEmpty()) {
            throw new IllegalArgumentException("Invalid zipcode");
        }

        List<CSVRecord> list = new ArrayList<>();

        //filtered based on input zip code
        for (CSVRecord record : res) {
            if (record.get("zipcode").equals(zipcode)) {

                list.add(record);
            }
        }
        //check if the size of list
        if (list.size() > 12) {
            //use maxHeap to get top 12 minimum ages
            //time complexity should be O(12 + (n - 12) * log12)
            list = top12(list);
        }

        //sort the result list after filtering
        list.sort(((o1, o2) -> {
            Integer i = Integer.parseInt(o1.get("age"));
            Integer j = Integer.parseInt(o2.get("age"));
            return Integer.compare(i, j);
        }));

        //convert the filtered list to the JSONArray
        JSONArray jsonArray = new JSONArray();
        for (CSVRecord record : list) {
            Map<String, String> temp = new HashMap<>();
            for (String data : record.map.keySet()) {
                temp.put(data, record.get(data));
            }
            jsonArray.put(temp);

        }
        return jsonArray;
    }

    /**
     *
     * @param list
     * @return
     */
    private List<CSVRecord> top12(List<CSVRecord> list) {
        PriorityQueue<CSVRecord> maxHeap = new PriorityQueue<CSVRecord>(12, (o1, o2) -> {
            Integer i = Integer.parseInt(o1.get("age"));
            Integer j = Integer.parseInt(o2.get("age"));
            return -Integer.compare(i, j);
        });

        for (int i = 0; i < 12; i++) {
            maxHeap.offer(list.get(i));
        }

        for (int i = 12; i < list.size(); i++) {
            CSVRecord top = maxHeap.peek();
            CSVRecord cur = list.get(i);
            Integer j1= Integer.parseInt(top.get("age"));
            Integer j2 = Integer.parseInt(cur.get("age"));
            if (Integer.compare(j1, j2) > 0) {
                maxHeap.poll();
                maxHeap.offer(cur);
            }
        }
        return new ArrayList<>(maxHeap);
    }

    /**
     * writes a protobuf to the location in the input
     * @param location
     *
     * definition of protobuf:
     * Protobuf is similar to XML, JSON and other data representation languages.
     * Its biggest feature is based on binary,so it is much shorter and shorter
     * than the traditional XML representation.Although it is a binary data
     * format, it has not become complicated. Developers define a structured
     * message format according to a certain syntax and then send it to the
     * command line tool. The tool will automatically generate related classes.
     * By including these classes in the project,
     * it is easy to call related methods to complete
     * the serialization and deserialization of business messages.
     *
     */

    public void writeToProtoBuf(String location)  {

        if (location == null || location.isEmpty()) {
            throw new IllegalArgumentException("Invalid location");
        }

        for (CSVRecord record : res) {
            Csv.Builder csvBuilder = Csv.newBuilder();
            csvBuilder.setName(record.get("name"));
            csvBuilder.setCity(record.get("city"));
            csvBuilder.setAddress(record.get("address"));
            csvBuilder.setAge(Integer.parseInt(record.get("age")));
            csvBuilder.setCity1(record.get("city1"));
            csvBuilder.setZipcode(record.get("zipcode"));

            FileOutputStream output;
            try {
                output = new FileOutputStream(location, true);
                csvBuilder.build().writeDelimitedTo(output);
                output.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }


}

