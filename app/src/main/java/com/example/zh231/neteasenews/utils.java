package com.example.zh231.neteasenews;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.util.SparseArray;
import android.widget.TextView;

import com.example.zh231.neteasenews.jsonParse.homeListData;
import com.example.zh231.neteasenews.jsonParse.videoListData;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class utils {

    Context context;//注意使用弱引用
    static final String fileName="localJson.json";//本地json文件名

    static final String hostUrl163="https://3g.163.com";

    static final String UrlBody="/touch/reconstruct/article/list/";

    static final String videoUrl="/touch/nc/api/video/recommend/Video_Recom/0-10.do?callback=videoList";

    public enum newsTypeCode{
        BBM54PGAwangning,//新闻
        BA10TA81wangning,//娱乐
        BA8E6OEOwangning,//体育
        BA8EE5GMwangning,//财经
        BAI67OGGwangning,//军事
        BA8D4A3Rwangning,//科技
        BAI6I0O5wangning,//手机
        BAI6JOD9wangning,//数码
        BA8F6ICNwangning,//时尚
        BAI6RHDKwangning,//游戏
        BA8FF5PRwangning,//教育
        BDC4QSV3wangning,//健康
        BEO4GINLwangning,//旅游
    }

    public utils(Context context){
        this.context=context;
    }

    /**
     * 重置选择
     * @param textView
     */
    public void resetSeleceed(TextView...textView){
        for (int i=0;i<textView.length;i++){
            if (textView[i].isSelected()){
                textView[i].setSelected(false);
            }
        }
    }

    /**
     * 隐藏fragment
     * @param fragmentTransaction
     */
    public void hideFragment(Fragment fragment,FragmentTransaction fragmentTransaction){
        if (fragment!=null&&fragmentTransaction!=null){
            fragmentTransaction.hide(fragment);
        }
    }

    /**
     * 获取状态栏高度
     * @return
     */
    public int getStatusBarHeight(){
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    static String sendGet(String url){
        Log.d("url-------------",url);
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .header("User-Agent","Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/39.0.2171.71 Safari/537.36")
                .get().url(url).build();
        Call call=client.newCall(request);
        String con=null;
        try{
            Response response=call.execute();
            con=response.body().string();
        }catch (IOException io){
            io.printStackTrace();
        }
        //Log.d("response------",con);
        return con;
    }

    //修复格式坏掉的json，返回正确的json
    public static String fixJson(String json){
        if (json.contains("artiList(")){
            json=json.replace("artiList(","");
            json=json.substring(0,json.length()-1);
        }
        return json;
    }

    /**
     * 解析并把数据放到nd和ntd0静态变量
     * @param json 需要解析的json数据
     */
    public ArrayList<homeListData> parseJson_home(String json,String currentNewsType){

        JsonParser jsonParser=new JsonParser();
        JsonObject jsonObject=jsonParser.parse(json).getAsJsonObject();
        JsonArray jsonArray=jsonObject.getAsJsonArray(currentNewsType);

        Gson gson=new Gson();
        ArrayList<homeListData> ListItem=new ArrayList<>();//所有数据在这里

        for(JsonElement element : jsonArray){
            homeListData hld = gson.fromJson(element,homeListData.class);
            ListItem.add(hld);
        }

        return ListItem;
    }


    /**
     * 解析视频数据
     * @param json
     * @return videoTopic未使用，sparseArray的1为videoListData
     */
    public SparseArray<ArrayList> parseJson_video(String json){

//        ArrayList<videoTopic> vt=new ArrayList<>();
        ArrayList<videoListData> vld=new ArrayList<>();
//        try{
//            JSONObject vldObj=new JSONObject(json);
//            JSONArray vldArr = vldObj.getJSONArray("视频");
//            for (int i=0;i<vldArr.length();i++){
//                videoListData videoListTemp=new videoListData();
//                JSONObject itemObj = vldArr.getJSONObject(i);
//                videoListTemp.setCategory(itemObj.getString("category"));
//                videoListTemp.setFirstFrameImg(itemObj.getString("firstFrameImg"));
//                videoListTemp.setFullSizeImg(itemObj.getString("fullSizeImg"));
//                videoListTemp.setLength(itemObj.getInt("length"));
//                if (itemObj.has("mp4Hd_url"))
//                videoListTemp.setMp4_url(itemObj.getString("mp4Hd_url"));//这里为高清
//                else if (itemObj.has("mp4_url"))
//                    videoListTemp.setMp4_url(itemObj.getString("mp4_url"));//没有高清选择标清
//                videoListTemp.setPlayCount(itemObj.getInt("playCount"));
//                videoListTemp.setPtime(itemObj.getString("ptime"));
//                videoListTemp.setReplyCount(itemObj.getInt("replyCount"));
//                videoListTemp.setReplyid(itemObj.getString("replyid"));
//                videoListTemp.setSectiontitle(itemObj.getString("sectiontitle"));
//                videoListTemp.setTitle(itemObj.getString("title"));
//                videoListTemp.setTopicDesc(itemObj.getString("topicDesc"));
//                videoListTemp.setTopicImg(itemObj.getString("topicImg"));
//                videoListTemp.setTopicName(itemObj.getString("topicName"));
//                videoListTemp.setTopicSid(itemObj.getString("topicSid"));
//                videoListTemp.setVid(itemObj.getString("vid"));
//                videoListTemp.setVideoRatio(itemObj.getDouble("videoRatio"));
//                videoListTemp.setVoteCount(itemObj.getInt("voteCount"));
//                videoListTemp.setVideosource(itemObj.getString("videosource"));
//                vld.add(videoListTemp);
//            }
//
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//
        SparseArray<ArrayList> array=new SparseArray<>();
////        array.put(0,vt);
//        array.put(1,vld);
        return array;
    }

    /**
     * 解析网页上的文章
     * @param html
     */
    public String parseHtml(String html) throws Throwable{

        String main="";
        String pattern = "<main>[\\s\\S]*?</main>";
//        InputStream is=new ByteArrayInputStream(html.getBytes());
//        XmlPullParser xmlPullParser= Xml.newPullParser();
//        xmlPullParser.setInput(is,"UTF-8");
        Pattern p=Pattern.compile(pattern);
        Matcher matcher=p.matcher(html);
        if (matcher.find()){
            main=matcher.group();
        }
        int s=main.indexOf("<div class=\"footer\">");//需要删除的节点开始处
        int e=main.lastIndexOf("</div>");
        String delStr=main.substring(s,e+6);//待删除的节点,6为"</div>"的长度，直接写死了(懒(oﾟvﾟ)ノ)
        main=main.replace(delStr,"");
        return main;
    }


    /**
     * 文件位于/data/data/packagename/file
     * @param fileName
     * @return
     */
    public void saveFile(String fileName,String content){
        try {
            FileOutputStream outputStream=context.openFileOutput(fileName,Context.MODE_PRIVATE);
            outputStream.write(content.getBytes());
            outputStream.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 文件位于/data/data/packagename/file
     * @param fileName
     * @return
     */
    public String readFile(String fileName){
        String content="";
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        try{
            FileInputStream inputStream = context.openFileInput(fileName);
            int len=0;
            byte[] buffer = new byte[inputStream.available()];
            while((len=inputStream.read(buffer))!=-1){
                outStream.write(buffer, 0, len);
            }
            byte[] content_byte = outStream.toByteArray();
            content = new String(content_byte);
        }catch (Exception e){
            e.printStackTrace();
        }
        return content;
    }

    public String stringForTime(int timeMs){
        int seconds = timeMs % 60;
        int minutes = (timeMs/60)%60;
        int hours = timeMs/3600;
        StringBuilder mFormatBuilder=new StringBuilder();
        Formatter mFormatter=new Formatter(mFormatBuilder, Locale.getDefault());
        mFormatBuilder.setLength(0);
        if(hours>0){
            return mFormatter.format("%d:%02d:%02d",hours,minutes,seconds).toString();
        } else {
            return mFormatter.format("%02d:%02d",minutes,seconds).toString();
        }
    }




}
