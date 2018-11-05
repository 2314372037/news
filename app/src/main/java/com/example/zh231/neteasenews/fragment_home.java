package com.example.zh231.neteasenews;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.zh231.neteasenews.adapter.homeListAdapter;
import com.example.zh231.neteasenews.adapter.newsTypeAdapter;

import java.lang.ref.WeakReference;


public class fragment_home extends Fragment {

    private String[] newsTypeStrings={"头条","精选","娱乐","汽车","运动","平顶山","漫画","段子","北京","公开课","讲讲"};
    private final String url_main="http://c.m.163.com/nc/article/headline/T1348647853363/";//T1348647853363为新闻类型(不是很清楚)
    private final String url_video="http://c.m.163.com/recommend/getChanListNews?channel=T1457068979049&size=20";//视频
    private String TAG = "fragment_home";
    static int home_data_start=0;//逐层，
    static int home_data_count=20;//一次性获取的新闻条数
    static ListView listView=null;
    static homeListAdapter adapter=null;
    static boolean isGetData=false;
    static String[] title;
    static String[] replyCount;


    static class myHandle extends Handler{
        private final WeakReference<Context> context;

        public myHandle(Context context){
            this.context=new WeakReference<Context>(context);
        }

        @Override
        public void handleMessage(Message msg) {
            isGetData=false;
            Context context=this.context.get();
            if (context==null){
                super.handleMessage(msg);
                return;
            }
            switch (msg.what){
                case 0:
                    new utils(context).parseJson(String.valueOf(msg.obj));
                    title=new String[utils.nd.size()];
                    replyCount=new String[utils.nd.size()];

                    for (int i=0;i<utils.nd.size();i++){//取出list中的normalData数据
                        title[i]=utils.nd.get(i).getTitle();
                        replyCount[i]=String.valueOf(utils.nd.get(i).getReplyCount());
                    }
                    listView.setAdapter(new homeListAdapter(context));

                    break;
                case 1:
                    new utils(context).parseJson(String.valueOf(msg.obj));
                    title=new String[utils.nd.size()];
                    replyCount=new String[utils.nd.size()];

                    for (int i=0;i<utils.nd.size();i++){//取出list中的normalData数据
                        title[i]=utils.nd.get(i).getTitle();
                        replyCount[i]=String.valueOf(utils.nd.get(i).getReplyCount());
                    }
                    adapter=new homeListAdapter(context);
                    adapter.notifyDataSetChanged();

                    break;
            }
        }
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getArguments();
    }


    private void initData(final Handler handle){
        String con=new utils(getContext()).readFile(utils.fileName);//读取本地文件
        if (con==null||con==""||con.isEmpty()){//加载在线文件
            Log.d(TAG,"加载在线文件");
            isGetData=true;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    String con = new utils(getContext()).sendGet(url_main+home_data_start+"-"+home_data_count+".html");
                    home_data_start++;
                    Log.d("fragment_home","--home_data_start:"+home_data_start);
                    //home_data_start滑到列表最后一条+1，用于加载新数据
                    Message message=new Message();
                    message.what=0;
                    message.obj=String.valueOf(con);
                    handle.sendMessage(message);
                }
            }).start();
        }else{//加载本地文件
            Log.d(TAG,"加载本地文件");
            new utils(getContext()).parseJson(con);
            title=new String[utils.nd.size()];
            replyCount=new String[utils.nd.size()];
            for (int i=0;i<utils.nd.size();i++){//取出list中的normalData数据
                title[i]=utils.nd.get(i).getTitle();
                replyCount[i]=String.valueOf(utils.nd.get(i).getReplyCount());
            }
            listView.setAdapter(new homeListAdapter(getContext()));
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {

        final myHandle handle=new myHandle(getContext());

        View view = inflater.inflate(R.layout.fragment_fragment_home, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.recyclerView_news_type);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(new newsTypeAdapter(getContext(),newsTypeStrings));

        listView = view.findViewById(R.id.newsListView);
        initData(handle);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getContext(),title[position],Toast.LENGTH_SHORT).show();
                //点击listview列表项时
            }
        });
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (firstVisibleItem+visibleItemCount==totalItemCount){
                    if (!isGetData){
                        isGetData=true;
                        Log.d("fragment_home","数据加载中...");
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                String con = new utils(getContext()).sendGet(url_main+home_data_start+"-"+home_data_count+".html");
                                home_data_start++;
                                Log.d("fragment_home","--home_data_start:"+home_data_start);
                                //home_data_start滑到列表最后一条+1，用于加载新数据
                                Message message=new Message();
                                message.what=1;
                                message.obj=String.valueOf(con);
                                handle.sendMessage(message);
                            }
                        }).start();
                    }
                }
            }
        });
        return view;
    }

}
