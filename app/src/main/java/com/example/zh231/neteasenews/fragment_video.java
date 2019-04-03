package com.example.zh231.neteasenews;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import com.example.zh231.neteasenews.adapter.fragment_video_adapter;
import com.example.zh231.neteasenews.bean.ListData;
import com.example.zh231.neteasenews.bean.videoListData;
import java.util.ArrayList;

public class fragment_video extends Fragment {

    private final String TAG = "fragment_video";

    private int start=0;//逐层，
    private int end=10;//一次性获取的视频条数 推荐10

    private fragment_video_adapter adapter;
    private ListView listView;
    private SwipeRefreshLayout swipeRefLayoutForVideo;
    private boolean isLoadingData=false;
    private final ListData listData=new ListData();


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_fragment_video,container,false);

        listView=view.findViewById(R.id.videoListView);

        swipeRefLayoutForVideo=view.findViewById(R.id.swipeRefLayoutForVideo);
        swipeRefLayoutForVideo.setColorSchemeResources(R.color.mainColor);
        swipeRefLayoutForVideo.setProgressViewOffset(false,0,50);
        swipeRefLayoutForVideo.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadingData(2);
            }
        });

        loadingData(0);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (listData.getVld()!=null){
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
        final videoHandle handle=new videoHandle(fragment_video.this);
        isLoadingData=true;
        new Thread(new Runnable() {
            @Override
            public void run() {
                String content="";

                //别干坏事--
                String cookie="_ntes_nnid=310a20ee31152c4c600f16d7b830b8f6,1553941006768; _ntes_nuid=310a20ee31152c4c600f16d7b830b8f6; _antanalysis_s_id=1553941008445;" +
                        " NTES_OSESS=iJSeI9DVqrBIT5Wlf0xTF8LHK5WfJVdd0gGxe24DCEULAAZ2MKT0DBwWuXgQhQ0m8q9v.ek7KBFFMfdC9pCbSkadD4JLKjQdiTg4drLL3316S81vpX4PVMhwG3xHolfVXcwwkcNDqNVlt0fXeESw7WxF.yylMBhrl1_my_y.JhIAO83eitkW6LPY6n3JlACbX;" +
                        " S_OINFO=1553941081|0|##|5976931763@sina.163.com; P_OINFO=5976931763@sina.163.com|1553941081|0|3g_163|00&99|null#0|null|3g_163|5976931763@sina.163.com";
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
                    case 2:
                        start=0;end=20;
                        content = utils.sendGet(utils.hostUrl163+utils.videoUrlBody+start+"-"+end+utils.videoUrlEnd,cookie);
                        content=utils.fixJson(content);
                        Log.d(TAG,"刷新数据"+content);
                        break;
                }
                Message message=new Message();
                message.what=what;
                message.obj=content;
                handle.sendMessage(message);
            }
        }).start();

    }

    private class videoHandle extends baseHandler{

        public videoHandle(Fragment fragment) {
            super(fragment);
        }

        @Override
        public void handleMessage(Message msg, int what) {
            switch (msg.what){
                case 0:
                    //Log.d("加载在线数据完成", String.valueOf(msg.obj));
                    listData.setVld(new utils(getContext()).parseJson_video(String.valueOf(msg.obj)));
                    adapter = new fragment_video_adapter<videoListData>(getContext(), listData.getVld());
                    listView.setAdapter(adapter);
                    break;
                case 1:
                    //Log.d("继续加载数据完成", String.valueOf(msg.obj));
                    if (!String.valueOf(msg.obj).equals("")) {
                        ArrayList<videoListData> tempList = new utils(getContext()).parseJson_video(String.valueOf(msg.obj));
                        listData.getVld().addAll(tempList);
                        adapter.notifyDataSetChanged();
                    } else {
                        Toast.makeText(getContext(), "网络故障", Toast.LENGTH_LONG).show();
                    }
                    break;
                case 2:
                    if (!String.valueOf(msg.obj).equals("")) {
                        ArrayList<videoListData> tempList = new utils(getContext()).parseJson_video(String.valueOf(msg.obj));
                        listData.getVld().clear();
                        listData.getVld().addAll(tempList);
                        adapter.notifyDataSetChanged();
                        swipeRefLayoutForVideo.setRefreshing(false);
                        Toast.makeText(getContext(),"刷新完成",Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getContext(), "网络故障", Toast.LENGTH_LONG).show();
                    }
                    break;
            }
            isLoadingData=false;
        }
    }

}
