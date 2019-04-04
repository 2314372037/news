package com.example.zh231.neteasenews.bean;

import java.util.ArrayList;

public class ListData {

    private ArrayList<homeListData> hld;

    private ArrayList<videoListData> vld;

    private ArrayList<liveListData> lld;

    public ArrayList<homeListData> getHld() {
        return hld;
    }

    public ArrayList<videoListData> getVld() {
        return vld;
    }

    public ArrayList<liveListData> getLld() {
        return lld;
    }

    public void setHld(ArrayList<homeListData> hld) {
        this.hld = hld;
    }

    public void setVld(ArrayList<videoListData> vld) {
        this.vld = vld;
    }

    public void setLld(ArrayList<liveListData> lld) {
        this.lld = lld;
    }
}
