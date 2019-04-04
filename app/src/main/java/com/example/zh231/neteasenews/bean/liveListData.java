package com.example.zh231.neteasenews.bean;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class liveListData {

//            "startTime":"2019-04-04 09:00:00",
//            "roomId":203229,
//            "pano":false,
//            "liveType":0,
//            "liveStatus":1,
//            "source":"网易原创",
//            "holdTime":13224994,
//            "mutilVideo":true,
//            "pcImage":"http://cms-bucket.ws.126.net/2019/04/01/a3980372c92a40f8bee5c9d88593e2b2.jpg",
//            "userCount":75203,
//            "video":false,
//            "endTime":"2019-04-04 22:30:00",
//            "confirm":0,
//            "type":0,
//            "videos":[
//    {
//        "videoUrl":"http://pullhls1d41cc57.live.126.net/live/81c97163bb9d49bfa2923f94fadf1fa2/playlist.m3u8",
//            "videoType":2,
//            "flvUrl":"http://v1d41cc57.live.126.net/live/81c97163bb9d49bfa2923f94fadf1fa2.flv?netease=v1d41cc57.live.126.net"
//    },
//    {
//        "videoUrl":"http://flv.bn.netease.com/videolib1/1904/02/6pjz5x3iw/SD/6pjz5x3iw-mobile.mp4",
//            "videoType":1,
//            "flvUrl":""
//    }
//            ],
//                    "roomName":"2019《唱游丝路 壮美广西》三月三大直播",
//                    "image":"http://cms-bucket.ws.126.net/2019/04/01/a3980372c92a40f8bee5c9d88593e2b2.jpg"

    private String startTime;

    private String roomId;

    private String pano;

    private int liveType;

    private int liveStatus;

    private String source;

    private String holdTime;

    private boolean mutilVideo;

    private String pcImage;

    private int userCount;

    private boolean video;

    private String endTime;

    private int confirm;

    private String type;

    private JsonArray videos;

    private String roomName;

    private String image;

    private JsonObject sourceinfo;

    private int nextPage;

    private JsonObject match_info;

    public String getStartTime() {
        return startTime;
    }

    public String getRoomId() {
        return roomId;
    }

    public String getPano() {
        return pano;
    }

    public int getLiveType() {
        return liveType;
    }

    public int getLiveStatus() {
        return liveStatus;
    }

    public String getSource() {
        return source;
    }

    public String getHoldTime() {
        return holdTime;
    }

    public boolean isMutilVideo() {
        return mutilVideo;
    }

    public String getPcImage() {
        return pcImage;
    }

    public int getUserCount() {
        return userCount;
    }

    public boolean isVideo() {
        return video;
    }

    public String getEndTime() {
        return endTime;
    }

    public int getConfirm() {
        return confirm;
    }

    public String getType() {
        return type;
    }

    public JsonArray getVideos() {
        return videos;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public void setPano(String pano) {
        this.pano = pano;
    }

    public void setLiveType(int liveType) {
        this.liveType = liveType;
    }

    public void setLiveStatus(int liveStatus) {
        this.liveStatus = liveStatus;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public void setHoldTime(String holdTime) {
        this.holdTime = holdTime;
    }

    public void setMutilVideo(boolean mutilVideo) {
        this.mutilVideo = mutilVideo;
    }

    public void setPcImage(String pcImage) {
        this.pcImage = pcImage;
    }

    public void setUserCount(int userCount) {
        this.userCount = userCount;
    }

    public void setVideo(boolean video) {
        this.video = video;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public void setConfirm(int confirm) {
        this.confirm = confirm;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setVideos(JsonArray videos) {
        this.videos = videos;
    }

    public String getRoomName() {
        return roomName;
    }

    public String getImage() {
        return image;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public JsonObject getSourceinfo() {
        return sourceinfo;
    }

    public void setSourceinfo(JsonObject sourceinfo) {
        this.sourceinfo = sourceinfo;
    }

    public int getNextPage() {
        return nextPage;
    }

    public void setNextPage(int nextPage) {
        this.nextPage = nextPage;
    }

    public JsonObject getMatch_info() {
        return match_info;
    }

    public void setMatch_info(JsonObject match_info) {
        this.match_info = match_info;
    }
}
