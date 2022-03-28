package com.shion1305.route.analysis;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class Params implements Serializable {
    public Integer count = 1;
    public ArrayList<String> ips = new ArrayList<>();
    public Date first;
    public Date last;

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public ArrayList<String> getIps() {
        return ips;
    }

    public void setIps(ArrayList<String> ips) {
        this.ips = ips;
    }

    public Date getFirst() {
        return first;
    }

    public void setFirst(Date first) {
        this.first = first;
    }

    public Date getLast() {
        return last;
    }

    public void setLast(Date last) {
        this.last = last;
    }

    @Override
    public String toString() {
        return "Params{" +
                "count=" + count +
                ", ips=" + ips +
                ", first=" + first +
                ", last=" + last +
                '}';
    }
}
