package com.example.zh231.neteasenews;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zh231.neteasenews.adapter.fragment_home_adapter;
import com.example.zh231.neteasenews.jsonParse.homeListData;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Arrays;


public class fragment_home extends Fragment {

    private String[] newsType = {"头条","精选","娱乐","汽车","运动","平顶山","漫画","段子","北京","公开课"};
    private final String url_main="http://c.m.163.com/nc/article/headline/T1348647853363/";//T1348647853363为新闻类型(不是很清楚)
    private final String url_video="http://c.m.163.com/recommend/getChanListNews?channel=T1457068979049&size=20";//视频
    private final String TAG = "fragment_home";
    static int home_data_start=0;//逐层，
    static int home_data_count=20;//一次性获取的新闻条数
    static boolean isLoadingData=false;//是否在加载数据
    static ListView listView;
    static fragment_home_adapter adapter;
    static ArrayList<homeListData> nd;


    static class homeHandle extends Handler{

        private final WeakReference<Context> context;

        public homeHandle(Context context){
            this.context=new WeakReference<Context>(context);
        }

        @Override
        public void handleMessage(Message msg) {
            Context context=this.context.get();
            if (context==null){
                super.handleMessage(msg);
                return;
            }
            switch (msg.what){
                case 0:
                    Log.d("加载在线数据完成",String.valueOf(msg.obj));
                    nd = new utils(context).parseJson(String.valueOf(msg.obj));
                    adapter=new fragment_home_adapter(context,nd);
                    listView.setAdapter(adapter);
                    break;
                case 1:
                    Log.d("下滑加载数据完成",String.valueOf(msg.obj));
                    //nd.clear();//发现不能对nd重新赋值(想着偷懒卡了几天t_t)
                    ArrayList<homeListData> tempData=new utils(context).parseJson(String.valueOf(msg.obj));
                    for (int i=0;i<tempData.size();i++){//最多循环home_data_count次，不用做优化
                        nd.add(tempData.get(i));
                    }
                    adapter.notifyDataSetChanged();
                    break;
            }
            isLoadingData=false;
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {

        final homeHandle handle = new homeHandle(this.getContext());

        View view = inflater.inflate(R.layout.fragment_fragment_home, container, false);

        LinearLayout homeTopView=view.findViewById(R.id.homeTopView);
        for (int i=0;i<newsType.length;i++){
            TextView textView=new TextView(getContext());
            textView.setTextSize(16);
            textView.setText("\t\t\t"+newsType[i]);
            textView.setTextColor(Color.BLACK);
            homeTopView.addView(textView);
        }

        listView = view.findViewById(R.id.newsListView);

        String con=new utils(getContext()).readFile(utils.fileName);//读取本地文件
        if (con==null||con==""||con.isEmpty()){
            Log.d(TAG,"加载在线数据");
            loadingData(0,handle);
        }else{
            Log.d(TAG,"加载本地数据");
            nd = new utils(getContext()).parseJson(con);
            adapter=new fragment_home_adapter(getContext(),nd);
            listView.setAdapter(adapter);
        }

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Toast.makeText(getContext(),nd.get(position).getUrl(), Toast.LENGTH_SHORT).show();

            }
        });
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (firstVisibleItem+visibleItemCount==totalItemCount){
                    if (!isLoadingData){
                        loadingData(1,handle);
                    }
                }
            }
        });
        return view;
    }


    private void loadingData(final int what,final homeHandle handler){

        isLoadingData=true;
        Log.d(TAG,"数据加载中...");
        new Thread(new Runnable() {
            @Override
            public void run() {
                String con = new utils(getContext()).sendGet(url_main+home_data_start+"-"+home_data_count+".html");
                home_data_start++;
                Log.d("fragment_home","--home_data_start:"+home_data_start);
                //home_data_start滑到列表最后一条+1，用于加载新数据
                Message message=new Message();
                message.what=what;
                message.obj=String.valueOf(con);
                handler.sendMessage(message);
            }
        }).start();
    }

}
