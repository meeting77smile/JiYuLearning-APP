package com.example.jiyulearning.entity;

public class ListInfo {
    public int id;
    public String date;
    public double time;//需要花费的时间
    public String description;//描述

    @Override
    public String toString() {
        return "ListInfo{" +
                "id=" + id +
                ", date='" + date + '\'' +
                ", time=" + time +
                ", description='" + description + '\'' +
                '}';
    }

    public ListInfo(){};

    public ListInfo(String date, double time, String description) {
        this.date = date;
        this.time = time;
        this.description = description;
    }
}
