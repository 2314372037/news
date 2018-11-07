package com.example.zh231.neteasenews;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zh231.neteasenews.adapter.fragment_video_adapter;
import com.example.zh231.neteasenews.jsonParse.videoListData;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class fragment_video extends Fragment {

    private final String url_video="http://c.m.163.com/recommend/getChanListNews?channel=T1457068979049&size=20";//视频
    static fragment_video_adapter adapter;
    static ListView listView;
    static ArrayList<videoListData> videoList;
    static boolean isLoadingData=false;
    private videoHandle handle;

    static class videoHandle extends Handler{

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
                    String json0 = String.valueOf(msg.obj);
                    SparseArray<ArrayList> videoArray0 = new utils(context).parseJson_video(json0);
                    videoList=videoArray0.get(1);
                    adapter=new fragment_video_adapter(context,videoList);
                    listView.setAdapter(adapter);
                    break;
                case 1:
                    String json1 = String.valueOf(msg.obj);
                    SparseArray<ArrayList> videoArray1 = new utils(context).parseJson_video(json1);
                    ArrayList<videoListData> tempData= videoArray1.get(1);
                    for (int i=0;i<tempData.size();i++){
                        videoList.add(tempData.get(i));
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

        handle=new videoHandle(getContext());

        View view=inflater.inflate(R.layout.fragment_fragment_video,container,false);

        listView=view.findViewById(R.id.videoListView);

        loadingData(0);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (videoList!=null){
                    Toast.makeText(getContext(),videoList.get(position).getMp4_url(),Toast.LENGTH_SHORT).show();


                }else{
                    Toast.makeText(getContext(),"数据加载中",Toast.LENGTH_SHORT).show();
                }

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
                        loadingData(1);
                    }
                }
            }
        });


        return view;
    }

    private void loadingData(final int what){
        final videoHandle handle=this.handle;
        isLoadingData=true;
        new Thread(new Runnable() {
            @Override
            public void run() {
                String json=new utils(getContext()).sendGet(url_video);
                Message message=new Message();
                message.what=what;
                message.obj=json;
                handle.sendMessage(message);
            }
        }).start();

    }
}
