package com.example.zh231.neteasenews.jsonParse;

import java.util.List;

public class videoListData {

//            "sizeHD": 5272,
//            "mp4Hd_url": "https://flv2.bn.netease.com/videolib1/1903/23/3pcn6qj4v/HD/3pcn6qj4v-mobile.mp4",
//            "description": "",
//            "title": "60岁阿姨未婚先孕，一上台，涂磊直言：这么有气质！",
//            "mp4_url": "https://flv2.bn.netease.com/videolib1/1903/23/3pcn6qj4v/SD/3pcn6qj4v-mobile.mp4",
//            "cover": "http://videoimg.ws.126.net/cover/20190323/q2vQMDFV1_cover.jpg",
//            "vid": "VVA112ARL",
//            "sizeSHD": 9885,
//            "playersize": 1,
//            "ptime": "2019-03-24 11:23:00",
//            "m3u8_url": "http://flv0.bn.netease.com/videolib1/1903/23/3pcn6qj4v/SD/movie_index.m3u8",
//            "topicImg": null,
//            "votecount": 500,
//            "length": 143,
//            "videosource": "新媒体",
//            "m3u8Hd_url": "http://flv0.bn.netease.com/videolib1/1903/23/3pcn6qj4v/HD/movie_index.m3u8",
//            "sizeSD": 4233,
//            "topicSid": "VDNOHVF4Q",
//            "playCount": 14539,
//            "replyCount": 105,
//            "replyBoard": "video_bbs",
//            "replyid": "VA112ARL050835RB",
//            "topicName": "花花娱乐星系",
//            "sectiontitle": null,
//            "topicDesc": "以短视频的形式第一时间分享最近剧集精彩片段，传递热门话题"

    private int sizeHD ;
    private String mp4Hd_url ;
    private String description ;
    private String title ;
    private String mp4_url ;
    private String cover ;
    private String vid ;
    private int sizeSHD ;
    private int playersize ;
    private String ptime ;
    private String m3u8_url ;
    private String topicImg ;
    private int votecount ;
    private int length ;//秒为单位
    private String videosource ;
    private String m3u8Hd_url ;
    private int sizeSD ;
    private String topicSid ;
    private int playCount ;
    private int replyCount ;
    private String replyBoard ;
    private String replyid ;
    private String topicName ;
    private String sectiontitle ;
    private String topicDesc ;

    public void setSizeHD(int sizeHD) {
        this.sizeHD = sizeHD;
    }

    public void setMp4Hd_url(String mp4Hd_url) {
        this.mp4Hd_url = mp4Hd_url;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setMp4_url(String mp4_url) {
        this.mp4_url = mp4_url;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public void setVid(String vid) {
        this.vid = vid;
    }

    public void setSizeSHD(int sizeSHD) {
        this.sizeSHD = sizeSHD;
    }

    public void setPlayersize(int playersize) {
        this.playersize = playersize;
    }

    public void setPtime(String ptime) {
        this.ptime = ptime;
    }

    public void setM3u8_url(String m3u8_url) {
        this.m3u8_url = m3u8_url;
    }

    public void setTopicImg(String topicImg) {
        this.topicImg = topicImg;
    }

    public void setVotecount(int votecount) {
        this.votecount = votecount;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public void setVideosource(String videosource) {
        this.videosource = videosource;
    }

    public void setM3u8Hd_url(String m3u8Hd_url) {
        this.m3u8Hd_url = m3u8Hd_url;
    }

    public void setSizeSD(int sizeSD) {
        this.sizeSD = sizeSD;
    }

    public void setTopicSid(String topicSid) {
        this.topicSid = topicSid;
    }

    public void setPlayCount(int playCount) {
        this.playCount = playCount;
    }

    public void setReplyCount(int replyCount) {
        this.replyCount = replyCount;
    }

    public void setReplyBoard(String replyBoard) {
        this.replyBoard = replyBoard;
    }

    public void setReplyid(String replyid) {
        this.replyid = replyid;
    }

    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }

    public void setSectiontitle(String sectiontitle) {
        this.sectiontitle = sectiontitle;
    }

    public void setTopicDesc(String topicDesc) {
        this.topicDesc = topicDesc;
    }

    public int getSizeHD() {
        return sizeHD;
    }

    public String getMp4Hd_url() {
        return mp4Hd_url;
    }

    public String getDescription() {
        return description;
    }

    public String getTitle() {
        return title;
    }

    public String getMp4_url() {
        return mp4_url;
    }

    public String getCover() {
        return cover;
    }

    public String getVid() {
        return vid;
    }

    public int getSizeSHD() {
        return sizeSHD;
    }

    public int getPlayersize() {
        return playersize;
    }

    public String getPtime() {
        return ptime;
    }

    public String getM3u8_url() {
        return m3u8_url;
    }

    public String getTopicImg() {
        return topicImg;
    }

    public int getVotecount() {
        return votecount;
    }

    public int getLength() {
        return length;
    }

    public String getVideosource() {
        return videosource;
    }

    public String getM3u8Hd_url() {
        return m3u8Hd_url;
    }

    public int getSizeSD() {
        return sizeSD;
    }

    public String getTopicSid() {
        return topicSid;
    }

    public int getPlayCount() {
        return playCount;
    }

    public int getReplyCount() {
        return replyCount;
    }

    public String getReplyBoard() {
        return replyBoard;
    }

    public String getReplyid() {
        return replyid;
    }

    public String getTopicName() {
        return topicName;
    }

    public String getSectiontitle() {
        return sectiontitle;
    }

    public String getTopicDesc() {
        return topicDesc;
    }



}
