package com.example.hw1_eylon;


import java.util.ArrayList;

public class Record_Manager {
    private ArrayList<Records> records;
    public Record_Manager() {
        this.records = new ArrayList<>();
    }
    public Record_Manager(ArrayList<Records> records) {
        this.records = records;
    }

    public ArrayList<Records> getRecords() {
        return records;
    }

    public Record_Manager setRecords(ArrayList<Records> records) {
        this.records = records;
        return this;
    }

    public void updateRecords(Records record){
        records.add(record);
        records.sort((a, b) -> (b.getCoins() - a.getCoins()));
        if(records.size() > 10){
            records.remove(10);
        }

    }

}
