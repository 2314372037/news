package com.example.zh231.neteasenews;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Toast;
import com.example.zh231.neteasenews.adapter.fragment_home_adapter;
import com.example.zh231.neteasenews.bean.ListData;
import com.example.zh231.neteasenews.bean.homeListData;
import com.example.zh231.neteasenews.customView.homeListView;
import java.util.ArrayList;

public class fragment_home_list extends Fragment {

    private String currentNewsType = utils.newsTypeCode.BBM54PGAwangning.toString();

    private final String TAG = "fragment_home";

    private int start=0;//逐层，
    private int end=20;//一次性获取的新闻条数 推荐20
    private String args1Key="newsType";

    private boolean isLoadingData=false;//是否在加载数据
    private homeListView listView;
    private SwipeRefreshLayout swipeRefLayout;
    private fragment_home_adapter adapter;
    private final ListData listData=new ListData();


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void loadingData(final int what){
        final homeHandle handler=new homeHandle(fragment_home_list.this);
        isLoadingData=true;
        new Thread(new Runnable() {
            @Override
            public void run() {
                String content="";
                switch (what){
                    case 0://在线加载
                        content = utils.sendGet(utils.hostUrl163+utils.UrlBody+currentNewsType+"/"+start+"-"+end+".html",null);
                        content=utils.fixJson(content);
                        Log.d(TAG,"加载在线数据"+content);
                        break;
                    case 1://本地加载
                        content = new utils(getContext()).readFile(utils.fileName);
                        Log.d(TAG,"加载本地数据"+content);
                        break;
                    case 2://继续加载
                        content = utils.sendGet(utils.hostUrl163+utils.UrlBody+currentNewsType+"/"+start+"-"+end+".html",null);
                        content=utils.fixJson(content);
                        Log.d(TAG,"继续加载数据"+content);
                        break;
                    case 3://刷新
                        start=0;end=20;
                        content = utils.sendGet(utils.hostUrl163+utils.UrlBody+currentNewsType+"/"+start+"-"+end+".html",null);
                        content=utils.fixJson(content);
                        try {
                            Thread.sleep(1500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        Log.d(TAG,"刷新数据"+content);
                        break;
                }
                Message message=new Message();
                message.what=what;
                message.obj=String.valueOf(content);
                handler.sendMessage(message);
            }
        }).start();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_list,container,false);

        listView = view.findViewById(R.id.newsListView);

        swipeRefLayout = view.findViewById(R.id.swipeRefLayout);
        swipeRefLayout.setColorSchemeResources(R.color.mainColor);
        swipeRefLayout.setProgressViewOffset(false,0,50);
        swipeRefLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadingData(3);
            }
        });

        currentNewsType=getArguments().getString(args1Key);

        String con=new utils(getContext()).readFile(utils.fileName);//尝试读取本地文件
        if (con==null||con==""||con.isEmpty()){
            Log.d(TAG,"加载在线数据");
            loadingData(0);
        }else{
            Log.d(TAG,"加载本地数据");
            loadingData(1);
        }

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getContext(),"url:"+listData.getHld().get(position).geturl(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getContext(),newsActivity.class);
                intent.putExtra("url",listData.getHld().get(position).geturl());
                startActivity(intent);
            }
        });

        listView.setOnScrollListener(new AbsListView.OnScrollListener() {//滚动到底部自动加载
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (firstVisibleItem + visibleItemCount == totalItemCount) {
                    if (!isLoadingData) {
                        //修改start and end，实现向后加载
                        start = start + end; //接着最后一条新闻获取 例如： 第一次0-20，第二次20-20，第三次40-20
                        loadingData(2);
                    }
                }
            }
        });

        return view;
    }

    private class homeHandle extends baseHandler{

        public homeHandle(Fragment fragment) {
            super(fragment);
        }

        @Override
        public void handleMessage(Message msg, int what) {
            switch (msg.what){
                case 0:
                    Log.d("加载在线数据完成",String.valueOf(msg.obj));
                    //new utils(context).saveFile(utils.fileName,String.valueOf(msg.obj));//每加载一次在线数据，就保存到本地
                    listData.setHld(new utils(getContext()).parseJson_home(String.valueOf(msg.obj),currentNewsType));
                    adapter=new fragment_home_adapter<homeListData>(getContext(),listData.getHld());
                    listView.setAdapter(adapter);
                    break;
                case 1:
                    Log.d("加载本地数据完成",String.valueOf(msg.obj));
                    listData.setHld(new utils(getContext()).parseJson_home(String.valueOf(msg.obj),currentNewsType));
                    adapter=new fragment_home_adapter<homeListData>(getContext(),listData.getHld());
                    listView.setAdapter(adapter);
                    break;
                case 2:
                    Log.d("继续加载数据完成",String.valueOf(msg.obj));
                    if (!String.valueOf(msg.obj).equals("")){
                        //new utils(context).saveFile(utils.fileName,String.valueOf(msg.obj));//每加载一次在线数据，就保存到本地
                        ArrayList<homeListData> tempList= new utils(getContext()).parseJson_home(String.valueOf(msg.obj),currentNewsType);
                        listData.getHld().addAll(tempList);
                        adapter.notifyDataSetChanged();
                    }else{
                        Toast.makeText(getContext(),"网络故障",Toast.LENGTH_LONG).show();
                    }
                    break;
                case 3:
                    Log.d("刷新数据完成",String.valueOf(msg.obj));
                    if (!String.valueOf(msg.obj).equals("")){
                        //new utils(context).saveFile(utils.fileName,String.valueOf(msg.obj));//每加载一次在线数据，就保存到本地
                        ArrayList<homeListData> tempList= new utils(getContext()).parseJson_home(String.valueOf(msg.obj),currentNewsType);
                        listData.getHld().clear();
                        listData.getHld().addAll(tempList);
                        adapter.notifyDataSetChanged();
                        swipeRefLayout.setRefreshing(false);
                        Toast.makeText(getContext(),"刷新完成",Toast.LENGTH_LONG).show();
                    }else{
                        Toast.makeText(getContext(),"网络故障",Toast.LENGTH_LONG).show();
                    }
                    break;
        }
            isLoadingData=false;
    }
    }
}
