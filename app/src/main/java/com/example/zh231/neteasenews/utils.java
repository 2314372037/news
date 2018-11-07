package com.example.zh231.neteasenews;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.util.SparseArray;
import android.widget.TextView;

import com.example.zh231.neteasenews.jsonParse.homeListData;
import com.example.zh231.neteasenews.jsonParse.videoListData;

import org.json.JSONArray;
import org.json.JSONObject;

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

    Context context;
    static final String fileName="localJson.json";//本地json文件名

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

    public String sendGet(String url){//http://c.m.163.com/nc/article/headline/T1348647853363/0-40.html
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

    /**
     * 解析并把数据放到nd和ntd0静态变量
     * @param json 需要解析的json数据
     */
    public ArrayList<homeListData> parseJson_headline(String json){
        ArrayList<homeListData> nd=new ArrayList<>();
        try{
            JSONObject jsonObject=new JSONObject(json);
            JSONArray T1348647853363 = jsonObject.getJSONArray("T1348647853363");//T1348647853363新闻类型specialextra//特别的
            for (int i=0;i<T1348647853363.length();i++){//显示到页面和动态创建控件许需要再循环内完成
                JSONObject T= T1348647853363.getJSONObject(i);
                if (i==0){//0为通用参数，或者置顶参数（疑似）
//                    ntd0.setSource(T.getString("source"));
//                    ntd0.setTitle(T.getString("title"));
//                    ntd0.setHasImg(T.getInt("hasImg"));
//                    ntd0.setAlias(T.getString("alias"));
//                    ntd0.setVotecount(T.getInt("votecount"));
//                    ntd0.setReplyCount(T.getInt("replyCount"));
//                    ntd0.setHasIcon(T.getBoolean("hasIcon"));
//                    ntd0.setCid(T.getString("cid"));
//                    ntd0.setImgsrc(T.getString("imgsrc"));
                }else{
                    if (T.has("specialextra")){//不解析specialextra(疑似专题)
                        Log.d("parseJson","忽略解析专题数据");
                        continue;
                    }
                    homeListData bdTemp=new homeListData();//存储每一个json数组的数据
                    if (T.has("votecount"))
                    bdTemp.setVotecount(T.getInt("votecount"));
                    if (T.has("docid"))
                    bdTemp.setDocid(T.getString("docid"));
                    if (T.has("lmodify"))
                    bdTemp.setLmodify(T.getString("lmodify"));
                    if (T.has("url_3w"))
                    bdTemp.setUrl_3w(T.getString("url_3w"));
                    if (T.has("source"))
                    bdTemp.setSource(T.getString("source"));
                    if (T.has("postid"))
                    bdTemp.setPostid(T.getString("postid"));
                    if (T.has("priority"))
                    bdTemp.setPriority(T.getInt("priority"));
                    if (T.has("title"))
                    bdTemp.setTitle(T.getString("title"));
                    if (T.has("mtime"))
                    bdTemp.setMtime(T.getString("mtime"));
                    if (T.has("url"))
                    bdTemp.setUrl(T.getString("url"));
                    if (T.has("replyCount"))
                    bdTemp.setReplyCount(T.getInt("replyCount"));
                    if (T.has("ltitle"))
                    bdTemp.setLtitle(T.getString("ltitle"));
                    if (T.has("subtitle"))
                    bdTemp.setSubtitle(T.getString("subtitle"));
                    if (T.has("digest"))
                    bdTemp.setDigest(T.getString("digest"));
                    if (T.has("boardid"))
                    bdTemp.setBoardid(T.getString("boardid"));
                    if (T.has("imgsrc"))
                    bdTemp.setImgsrc(T.getString("imgsrc"));
                    if (T.has("ptime"))
                    bdTemp.setPtime(T.getString("ptime"));
                    if (T.has("pixel"))
                    bdTemp.setPixel(T.getString("pixel"));
                    if (T.has("daynum"))
                    bdTemp.setDaynum(T.getString("daynum"));
                    nd.add(bdTemp);
                }
            }
            new utils(context).saveFile(fileName,String.valueOf(json));//解析完成后保存json数据，下次打开加载
        }catch (Exception e){
            e.printStackTrace();
        }
        return nd;
    }


    /**
     * 解析视频数据
     * @param json
     * @return videoTopic未使用，sparseArray的1为videoListData
     */
    public SparseArray<ArrayList> parseJson_video(String json){
//        ArrayList<videoTopic> vt=new ArrayList<>();
        ArrayList<videoListData> vld=new ArrayList<>();
        try{
            JSONObject vldObj=new JSONObject(json);
            JSONArray vldArr = vldObj.getJSONArray("视频");
            for (int i=0;i<vldArr.length();i++){
                videoListData videoListTemp=new videoListData();
                JSONObject itemObj = vldArr.getJSONObject(i);
                videoListTemp.setCategory(itemObj.getString("category"));
                videoListTemp.setFirstFrameImg(itemObj.getString("firstFrameImg"));
                videoListTemp.setFullSizeImg(itemObj.getString("fullSizeImg"));
                videoListTemp.setLength(itemObj.getInt("length"));
                if (itemObj.has("mp4Hd_url"))
                videoListTemp.setMp4_url(itemObj.getString("mp4Hd_url"));//这里为高清
                else if (itemObj.has("mp4_url"))
                    videoListTemp.setMp4_url(itemObj.getString("mp4_url"));//没有高清选择标清
                videoListTemp.setPlayCount(itemObj.getInt("playCount"));
                videoListTemp.setPtime(itemObj.getString("ptime"));
                videoListTemp.setReplyCount(itemObj.getInt("replyCount"));
                videoListTemp.setReplyid(itemObj.getString("replyid"));
                videoListTemp.setSectiontitle(itemObj.getString("sectiontitle"));
                videoListTemp.setTitle(itemObj.getString("title"));
                videoListTemp.setTopicDesc(itemObj.getString("topicDesc"));
                videoListTemp.setTopicImg(itemObj.getString("topicImg"));
                videoListTemp.setTopicName(itemObj.getString("topicName"));
                videoListTemp.setTopicSid(itemObj.getString("topicSid"));
                videoListTemp.setVid(itemObj.getString("vid"));
                videoListTemp.setVideoRatio(itemObj.getDouble("videoRatio"));
                videoListTemp.setVoteCount(itemObj.getInt("voteCount"));
                videoListTemp.setVideosource(itemObj.getString("videosource"));
                vld.add(videoListTemp);
            }

        }catch (Exception e){
            e.printStackTrace();
        }

        SparseArray<ArrayList> array=new SparseArray<>();
//        array.put(0,vt);
        array.put(1,vld);
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
