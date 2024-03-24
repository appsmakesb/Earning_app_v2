package com.burhanstore.earningmaster.models;


public class Model_History {
    String desc,date,coin,time;

    public String getTime() {
        return time;
    }

    public Model_History() {
    }

    public Model_History(String desc, String date, String coin, String time) {
        this.desc = desc;
        this.date = date;
        this.coin = coin;
        this.time = time;

    }

    public String getDesc() {
        return desc;
    }

    public String getDate() {
        return date;
    }

    public String getCoin() {
        return coin;
    }

}

