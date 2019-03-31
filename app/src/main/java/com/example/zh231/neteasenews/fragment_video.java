package com.example.zh231.neteasenews;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zh231.neteasenews.adapter.fragment_video_adapter;
import com.example.zh231.neteasenews.jsonParse.ListData;
import com.example.zh231.neteasenews.jsonParse.homeListData;
import com.example.zh231.neteasenews.jsonParse.videoListData;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import static com.example.zh231.neteasenews.utils.fixJson;

public class fragment_video extends Fragment {

    //https://3g.163.com/touch/nc/api/video/recommend/Video_Recom/0-10.do?callback=videoList
    private final String TAG = "fragment_video";

    private int start=0;//逐层，
    private int end=10;//一次性获取的视频条数 推荐10


    private fragment_video_adapter adapter;
    private ListView listView;
    private boolean isLoadingData=false;
    private videoHandle handle;
    private final ListData listData=new ListData();

    class videoHandle extends Handler{

        private final WeakReference<Context> context;

        public videoHandle(Context context) {
            super();
            this.context=new WeakReference<Context>(context);
        }

        @Override
        public void handleMessage(Message msg) {
            Context context=this.context.get();
            if (context==null){
                super.handleMessage(msg);
                return ;
            }
            switch (msg.what){
                case 0:
                    Log.d("加载在线数据完成",String.valueOf(msg.obj));
                    listData.setVld(new utils(context).parseJson_video(String.valueOf(msg.obj)));
                    adapter=new fragment_video_adapter(context,listData.getVld());
                    listView.setAdapter(adapter);
                    break;
                case 1:
                    Log.d("继续加载数据完成",String.valueOf(msg.obj));
                    if (!String.valueOf(msg.obj).equals("")){
                        ArrayList<videoListData> tempList= new utils(context).parseJson_video(String.valueOf(msg.obj));
                        listData.getVld().addAll(tempList);
                        adapter.notifyDataSetChanged();
                    }else{
                        Toast.makeText(context,"网络故障",Toast.LENGTH_LONG).show();
                    }
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

        handle=new videoHandle(getContext());

        View view=inflater.inflate(R.layout.fragment_fragment_video,container,false);

        listView=view.findViewById(R.id.videoListView);

        loadingData(0);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (listData.getVld()!=null){
//                    Toast.makeText(getContext(),videoList.get(position).getMp4_url(),Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent();
                    intent.setClass(getContext(),videoActivity.class);
                    intent.putExtra("url",listData.getVld().get(position).getMp4_url());
                    startActivity(intent);
                }else{
                    Toast.makeText(getContext(),"数据加载中",Toast.LENGTH_SHORT).show();
                }

            }
        });

        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            boolean ssc=false;
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                ssc=true;
            }
            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (ssc){
                    if (firstVisibleItem+visibleItemCount==totalItemCount){
                        if (!isLoadingData) {
                            //修改start and end，实现向后加载
                            start = start + end; //接着最后一条视频获取 例如： 第一次0-10，第二次10-10，第三次20-10
                            loadingData(1); //先破解接口协议
                        }
                    }
                    ssc=false;
                }
            }
        });


        return view;
    }

    private void loadingData(final int what){//视频加载需要cookie,否则无法加载出数据
        final videoHandle handle=this.handle;
        isLoadingData=true;
        new Thread(new Runnable() {
            @Override
            public void run() {
                String content="";
                String cookie="";
                switch (what){
                    case 0://在线加载
                        content = utils.sendGet(utils.hostUrl163+utils.videoUrlBody+start+"-"+end+utils.videoUrlEnd,cookie);
                        content=utils.fixJson(content);
                        Log.d(TAG,"加载在线数据"+content);
                        break;
                    case 1://继续加载
                        content = utils.sendGet(utils.hostUrl163+utils.videoUrlBody+start+"-"+end+utils.videoUrlEnd,cookie);
                        content=utils.fixJson(content);
                        Log.d(TAG,"继续加载数据"+content);
                        break;
                }
                Message message=new Message();
                message.what=what;
                message.obj=content;
                handle.sendMessage(message);
            }
        }).start();

    }
}
