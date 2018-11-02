package com.example.zh231.neteasenews;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;

import jsonParse.normalData;
import jsonParse.normal_top0_Data;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class utils {

    Context context;

    public static normal_top0_Data ntd0;
    public static ArrayList<normalData> nd;

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
        Log.d("response------",con);
        return con;
    }

    /**
     * 解析并把数据放到nd和ntd0静态变量
     * @param json 需要解析的json数据
     */
    public void parseJson(String json){
        if (nd==null){
            nd=new ArrayList<normalData>();
        }else{
            nd.clear();
        }
        ntd0=null;
        ntd0=new normal_top0_Data();


        try{
            JSONObject jsonObject=new JSONObject(json);
            JSONArray T1348647853363 = jsonObject.getJSONArray("T1348647853363");//T1348647853363新闻类型specialextra//特别的
            for (int i=0;i<T1348647853363.length();i++){//显示到页面和动态创建控件许需要再循环内完成
                JSONObject T= T1348647853363.getJSONObject(i);
                if (i==0){//0为通用参数，或者置顶参数（疑似）
                    ntd0.setSource(T.getString("source"));
                    ntd0.setTitle(T.getString("title"));
                    ntd0.setHasImg(T.getInt("hasImg"));
                    ntd0.setAlias(T.getString("alias"));
                    ntd0.setVotecount(T.getInt("votecount"));
                    ntd0.setReplyCount(T.getInt("replyCount"));
                    ntd0.setHasIcon(T.getBoolean("hasIcon"));
                    ntd0.setCid(T.getString("cid"));
                    ntd0.setImgsrc(T.getString("imgsrc"));
                }else{
                    if (T.has("specialextra")){//不解析specialextra(疑似专题)
                        Log.d("parseJson","忽略解析专题数据");
                        continue;
                    }
                    normalData bdTemp=new normalData();//存储每一个json数组的数据
                    bdTemp.setVotecount(T.getInt("votecount"));
                    bdTemp.setDocid(T.getString("docid"));
                    bdTemp.setLmodify(T.getString("lmodify"));
                    bdTemp.setUrl_3w(T.getString("url_3w"));
                    bdTemp.setSource(T.getString("source"));
                    bdTemp.setPostid(T.getString("postid"));
                    bdTemp.setPriority(T.getInt("priority"));
                    bdTemp.setTitle(T.getString("title"));
                    bdTemp.setMtime(T.getString("mtime"));
                    bdTemp.setUrl(T.getString("url"));
                    bdTemp.setReplyCount(T.getInt("replyCount"));
                    bdTemp.setLtitle(T.getString("ltitle"));
                    bdTemp.setSubtitle(T.getString("subtitle"));
                    bdTemp.setDigest(T.getString("digest"));
                    bdTemp.setBoardid(T.getString("boardid"));
                    bdTemp.setImgsrc(T.getString("imgsrc"));
                    bdTemp.setPtime(T.getString("ptime"));
                    if (T.has("pixel"))
                        bdTemp.setPixel(T.getString("pixel"));
                    bdTemp.setDaynum(T.getString("daynum"));
                    nd.add(bdTemp);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
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




}
