package com.example.zh231.neteasenews;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.zh231.neteasenews.adapter.fragment_live_adapter;
import com.example.zh231.neteasenews.bean.ListData;
import com.example.zh231.neteasenews.bean.liveListData;

import java.io.IOException;
import java.util.ArrayList;

public class liveActivity extends AppCompatActivity {

    private SurfaceView surfaceview;
    private int nextList=1;//下一个要获取的列表索引
    private String liveListUrl="https://data.live.126.net/livechannel/previewlist/1.json?callback=previewlist_";

    private final String TAG = "fragment_live_list";

    private boolean isLoadingData=false;//是否在加载数据
    private ListView liveListView;
    private SwipeRefreshLayout swpieRefreshLayoutForLive;
    private fragment_live_adapter adapter;
    private final ListData listData=new ListData();//所有列表数据

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live);
        setDarkStatusIcon(true);

        liveListView = liveActivity.this.findViewById(R.id.liveListView);

        swpieRefreshLayoutForLive=liveActivity.this.findViewById(R.id.swpieRefreshLayoutForLive);
        swpieRefreshLayoutForLive.setColorSchemeResources(R.color.mainColor);
        swpieRefreshLayoutForLive.setProgressViewOffset(false,0,50);
        swpieRefreshLayoutForLive.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadingData(2);
            }
        });

        loadingData(0);

        liveListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (listData.getLld().get(position).getSourceinfo()!=null){
                    Toast.makeText(liveActivity.this,"json:"+listData.getLld().get(position).getSourceinfo(), Toast.LENGTH_SHORT).show();
                    //解析sourceinfo数据
                }else if (listData.getLld().get(position).getVideos()!=null){
                    Toast.makeText(liveActivity.this,"json:"+listData.getLld().get(position).getVideos(), Toast.LENGTH_SHORT).show();
                }else if (listData.getLld().get(position).getMatch_info()!=null){
                    Toast.makeText(liveActivity.this,"json:"+listData.getLld().get(position).getMatch_info(), Toast.LENGTH_SHORT).show();
                }

//                Intent intent = new Intent();
//                intent.putExtra("url",listData.getLld().get(position).getSourceinfo());
//                startActivity(intent);
            }
        });

        liveListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (firstVisibleItem + visibleItemCount == totalItemCount) {
                    if (!isLoadingData) {
                        loadingData(1);
                    }
                }
            }
        });

        Intent intent = getIntent();
        Log.d("调试",intent.getStringExtra("m3u8Url"));

//        MediaPlayer mediaPlayer=new MediaPlayer();
//
//        surfaceview=findViewById(R.id.surfaceview);
//        SurfaceHolder holder = surfaceview.getHolder();
//        holder.addCallback(new SurfaceHolder.Callback() {
//            @Override
//            public void surfaceCreated(SurfaceHolder holder) {//创建时
//
//            }
//
//            @Override
//            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {//改变时
//
//            }
//
//            @Override
//            public void surfaceDestroyed(SurfaceHolder holder) {//销毁时
//
//            }
//        });
//
//        try{
//            mediaPlayer.setDataSource(intent.getStringExtra("m3u8Url"));
//            mediaPlayer.prepare();
//            mediaPlayer.setDisplay(holder);
//            mediaPlayer.start();
//        }catch (IOException ioe){
//            ioe.printStackTrace();
//        }

    }

    protected void setDarkStatusIcon(boolean dark) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            View decorView = getWindow().getDecorView();
            if (decorView == null) return;
            int vis = decorView.getSystemUiVisibility();
            if (dark) {
                vis |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            } else {
                vis &= ~View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            }
            decorView.setSystemUiVisibility(vis);
        }
    }

    private void loadingData(final int what){
        final liveHandle handler=new liveHandle(liveActivity.this);
        isLoadingData=true;
        new Thread(new Runnable() {
            @Override
            public void run() {
                String content="";
                switch (what){
                    case 0://在线加载
                        content = utils.sendGet(liveListUrl+nextList,null);
                        content=utils.fixJson(content);
                        break;
                    case 1://继续加载
                        content = utils.sendGet(liveListUrl+nextList,null);
                        Log.d("调试","加载数据,nextList:"+nextList);
                        content=utils.fixJson(content);
                        break;
                    case 2://刷新
                        Log.d("调试","直播列表刷新");
                        nextList=1;
                        content = utils.sendGet(liveListUrl+nextList,null);//1假设为首页
                        content=utils.fixJson(content);
                        try {
                            Thread.sleep(1500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        break;
                }
                Message message=new Message();
                message.what=what;
                message.obj=String.valueOf(content);
                handler.sendMessage(message);
            }
        }).start();
    }

    private class liveHandle extends baseHandler {

        public liveHandle(Activity activity) {
            super(activity);
        }

        @Override
        public void handleMessage(Message msg, int what) {
            switch (msg.what) {
                case 0:
                    listData.setLld(new utils(liveActivity.this).parseJson_live(String.valueOf(msg.obj)));
                    adapter = new fragment_live_adapter<liveListData>(liveActivity.this, listData.getLld());
                    liveListView.setAdapter(adapter);
                    nextList=listData.getLld().get(0).getNextPage();//保存下一页索引
                    break;
                case 1:
                    if (!String.valueOf(msg.obj).equals("")) {
                        ArrayList<liveListData> tempList = new utils(liveActivity.this).parseJson_live(String.valueOf(msg.obj));
                        nextList=listData.getLld().get(0).getNextPage();//保存下一页索引
                        listData.getLld().addAll(tempList);
                        adapter.notifyDataSetChanged();
                    } else {
                        Toast.makeText(liveActivity.this, "网络故障", Toast.LENGTH_LONG).show();
                    }
                    break;
                case 2:
                    if (!String.valueOf(msg.obj).equals("")) {
                        ArrayList<liveListData> tempList = new utils(liveActivity.this).parseJson_live(String.valueOf(msg.obj));
                        listData.getLld().clear();
                        listData.getLld().addAll(tempList);
                        adapter.notifyDataSetChanged();
                        swpieRefreshLayoutForLive.setRefreshing(false);
                        Toast.makeText(liveActivity.this, "刷新完成", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(liveActivity.this, "网络故障", Toast.LENGTH_LONG).show();
                    }
                    break;
            }
            isLoadingData = false;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        setDarkStatusIcon(false);
    }
}
