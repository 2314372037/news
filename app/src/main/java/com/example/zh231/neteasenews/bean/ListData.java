package com.example.zh231.neteasenews.bean;

import java.util.ArrayList;

public class ListData {

    private ArrayList<homeListData> hld;

    private ArrayList<videoListData> vld;

    public ArrayList<homeListData> getHld() {
        return hld;
    }

    public ArrayList<videoListData> getVld() {
        return vld;
    }

    public void setHld(ArrayList<homeListData> hld) {
        this.hld = hld;
    }

    public void setVld(ArrayList<videoListData> vld) {
        this.vld = vld;
    }
}
