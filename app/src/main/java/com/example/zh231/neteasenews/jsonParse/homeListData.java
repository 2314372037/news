package com.example.zh231.neteasenews.jsonParse;

public class homeListData {


//    	"liveInfo": null,
//                "docid": "EAT2S0DF0514DL55",
//                "source": "时间新闻",
//                "title": "爆炸后的不眠夜 毛毯堵住窗户夫妻俩凑合了一宿",
//                "priority": 150,
//                "hasImg": 1,
//                "url": "",
//                "commentCount": 1720,
//                "imgsrc3gtype": "1",
//                "stitle": "S1553156722721@",
//                "digest": "3月22日，江苏响水县化工厂爆炸事故已过去一天，据央视报道，",
//                "imgsrc": "http://cms-bucket.ws.126.net/2019/03/22/edaa11f17a2c458fb67472209785a463.png",
//                "ptime": "2019-03-22 18:17:29"


    private Object liveInfo;

    private String docid;

    private String source;

    private String title;

    private int priority;

    private int hasImg;

    private String url;

    private int commentCount;

    private String imgsrc3gtype;

    private String stitle;

    private String digest;

    private String imgsrc;

    private String ptime;


    public void setliveInfo(Object liveInfo){
        this.liveInfo = liveInfo;
    }
    public Object getliveInfo(){
        return this.liveInfo;
    }


    public void setdocid(String docid){
        this.docid = docid;
    }
    public String getdocid(){
        return this.docid;
    }


    public void setsource(String source){
        this.source = source;
    }
    public String getsource(){
        return this.source;
    }


    public void settitle(String title){
        this.title = title;
    }
    public String gettitle(){
        return this.title;
    }


    public void setpriority(int priority){
        this.priority = priority;
    }
    public int getpriority(){
        return this.priority;
    }


    public void sethasImg(int hasImg){
        this.hasImg = hasImg;
    }
    public int gethasImg(){
        return this.hasImg;
    }


    public void seturl(String url){
        this.url = url;
    }
    public String geturl(){
        return this.url;
    }


    public void setcommentCount(int commentCount){
        this.commentCount = commentCount;
    }
    public int getcommentCount(){
        return this.commentCount;
    }


    public void setimgsrc3gtype(String imgsrc3gtype){
        this.imgsrc3gtype = imgsrc3gtype;
    }
    public String getimgsrc3gtype(){
        return this.imgsrc3gtype;
    }


    public void setstitle(String stitle){
        this.stitle = stitle;
    }
    public String getstitle(){
        return this.stitle;
    }


    public void setdigest(String digest){
        this.digest = digest;
    }
    public String getdigest(){
        return this.digest;
    }


    public void setimgsrc(String imgsrc){
        this.imgsrc = imgsrc;
    }
    public String getimgsrc(){
        return this.imgsrc;
    }


    public void setptime(String ptime){
        this.ptime = ptime;
    }
    public String getptime(){
        return this.ptime;
    }
}
