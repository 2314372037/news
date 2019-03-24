package com.example.zh231.neteasenews;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;
import com.example.zh231.neteasenews.adapter.fragment_home_adapter;
import com.example.zh231.neteasenews.customView.typeView;
import com.example.zh231.neteasenews.jsonParse.homeListData;
import java.lang.ref.WeakReference;
import java.util.ArrayList;



public class fragment_home extends Fragment {


    static String currentNewsType = utils.newsTypeCode.BBM54PGAwangning.toString();

    private final String TAG = "fragment_home";

    static int start=0;//逐层，
    static int end=20;//一次性获取的新闻条数 推荐20


    static boolean isLoadingData=false;//是否在加载数据
    LinearLayout homeTopView=null;
    static ListView listView;
    static fragment_home_adapter adapter;
    private homeHandle handle;
    static ArrayList<homeListData> hdl=null;


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
                    new utils(context).saveFile(utils.fileName,String.valueOf(msg.obj));//每加载一次在线数据，就保存到本地
                    hdl = new utils(context).parseJson_home(String.valueOf(msg.obj),currentNewsType);
                    adapter=new fragment_home_adapter<homeListData>(context,hdl);
                    listView.setAdapter(adapter);
                    break;
                case 1:
                    Log.d("加载本地数据完成",String.valueOf(msg.obj));
                    hdl = new utils(context).parseJson_home(String.valueOf(msg.obj),currentNewsType);
                    adapter=new fragment_home_adapter<homeListData>(context,hdl);
                    listView.setAdapter(adapter);
                    break;
                case 2:
                    Log.d("继续加载数据完成",String.valueOf(msg.obj));
                    if (!String.valueOf(msg.obj).equals("")){
                        new utils(context).saveFile(utils.fileName,String.valueOf(msg.obj));//每加载一次在线数据，就保存到本地
                        ArrayList<homeListData> tempList= new utils(context).parseJson_home(String.valueOf(msg.obj),currentNewsType);
                        hdl.addAll(tempList);
                        adapter.notifyDataSetChanged();
                    }else{
                        Toast.makeText(context,"网络故障",Toast.LENGTH_LONG).show();
                    }
                    break;
            }
            isLoadingData=false;
        }
    }


    /**
     * 初始化相关
     */
    public void initData(){

        String newsType[]={"新闻","娱乐","体育","财经","军事","科技","手机","数码","时尚","游戏","教育","健康","旅游"};
        for (String temp:newsType){
            RelativeLayout.LayoutParams layoutParams=new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.MATCH_PARENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT);
            layoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL);

            typeView tv=new typeView(getContext(),newsType);

            homeTopView.addView(tv);
        }

        String con=new utils(getContext()).readFile(utils.fileName);//尝试读取本地文件
        if (con==null||con==""||con.isEmpty()){
            Log.d(TAG,"加载在线数据");
            loadingData(0);
        }else{
            Log.d(TAG,"加载本地数据");
            loadingData(1);
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        handle = new homeHandle(this.getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_fragment_home, container, false);

        homeTopView = view.findViewById(R.id.homeTopView);//重构

        listView = view.findViewById(R.id.newsListView);

        initData();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getContext(),"url:"+hdl.get(position-1).geturl(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getContext(),newsContent.class);
                intent.putExtra("url",hdl.get(position-1).geturl());
                startActivity(intent);
            }
        });

        listView.setOnScrollListener(new AbsListView.OnScrollListener() {//滚动到底部自动加载
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {}

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (firstVisibleItem+visibleItemCount==totalItemCount){
                    if (!isLoadingData){
                        //修改start and end，实现向后加载
                        start=start+end; //接着最后一条新闻获取 例如： 第一次0-20，第二次20-20，第三次40-20
                        loadingData(2);
                    }
                }
            }
        });
        return view; //返回给调用者
    }


    private void loadingData(final int what){
        final homeHandle handler=handle;
        isLoadingData=true;
        new Thread(new Runnable() {
            @Override
            public void run() {
                String content="";
                switch (what){
                    case 0://在线加载
                        content = utils.sendGet(utils.hostUrl163+utils.UrlBody+currentNewsType+"/"+start+"-"+end+".html");
                        content=utils.fixJson(content);
                        Log.d(TAG,"加载在线数据"+content);
                        break;
                    case 1://本地加载
                        content = new utils(getContext()).readFile(utils.fileName);
                        Log.d(TAG,"加载本地数据"+content);
                        break;
                    case 2://继续加载
                        content = utils.sendGet(utils.hostUrl163+utils.UrlBody+currentNewsType+"/"+start+"-"+end+".html");
                        content=utils.fixJson(content);
                        Log.d(TAG,"继续加载数据"+content);
                        break;
                }
                Message message=new Message();
                message.what=what;
                message.obj=String.valueOf(content);
                handler.sendMessage(message);
            }
        }).start();
    }

}
