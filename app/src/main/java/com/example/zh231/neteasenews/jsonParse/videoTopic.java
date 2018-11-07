package com.example.zh231.neteasenews.jsonParse;

public class videoTopic {
    /**
     * 此类未使用
     */

    private String alias;

    private String ename;

    private boolean followed;

    private String tid;

    private String tname;

    private String topic_icons;

    public void setAlias(String alias){
        this.alias = alias;
    }
    public String getAlias(){
        return this.alias;
    }
    public void setEname(String ename){
        this.ename = ename;
    }
    public String getEname(){
        return this.ename;
    }
    public void setFollowed(boolean followed){
        this.followed = followed;
    }
    public boolean getFollowed(){
        return this.followed;
    }
    public void setTid(String tid){
        this.tid = tid;
    }
    public String getTid(){
        return this.tid;
    }
    public void setTname(String tname){
        this.tname = tname;
    }
    public String getTname(){
        return this.tname;
    }
    public void setTopic_icons(String topic_icons){
        this.topic_icons = topic_icons;
    }
    public String getTopic_icons(){
        return this.topic_icons;
    }
}
