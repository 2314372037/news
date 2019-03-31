package com.example.zh231.neteasenews;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.support.design.widget.TabLayout;

import com.example.zh231.neteasenews.adapter.fragment_home_adapter;
import com.example.zh231.neteasenews.adapter.viewPagerAdapter;
import com.example.zh231.neteasenews.customView.homeListView;
import com.example.zh231.neteasenews.jsonParse.ListData;
import com.example.zh231.neteasenews.jsonParse.homeListData;

import java.lang.ref.WeakReference;
import java.util.ArrayList;



public class fragment_home extends Fragment {


    private String currentNewsType = utils.newsTypeCode.BBM54PGAwangning.toString();

    private final String TAG = "fragment_home";

    private int start=0;//逐层，
    private int end=20;//一次性获取的新闻条数 推荐20


    private boolean isLoadingData=false;//是否在加载数据
    private TabLayout homeTopLayout=null;
    private ViewPager viewPagerLayout=null;

    private homeListView listView;
    private fragment_home_adapter adapter;
    private homeHandle handle;
    private final ListData listData=new ListData();

    private Button search_Btn=null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        handle = new homeHandle(this.getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_fragment_home, container, false);

        homeTopLayout = view.findViewById(R.id.homeTopLayout);

        viewPagerLayout = view.findViewById(R.id.viewPagerLayout);

        listView = (homeListView)view.findViewById(R.id.newsListView);

        search_Btn = view.findViewById(R.id.search_Btn);

        initData();

        search_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(),searchActivity.class);
                intent.putExtra("from","home");
                startActivity(intent);
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getContext(),"url:"+listData.getHld().get(position-1).geturl(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getContext(),newsActivity.class);
                intent.putExtra("url",listData.getHld().get(position-1).geturl());
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
        return view; //返回给调用者
    }

    /**
     * 初始化相关
     */
    public void initData(){

        String titles[]={"新闻","娱乐","体育","财经","军事","科技","手机","数码","时尚","游戏","教育","健康","旅游"};

        for (String title:titles){
            homeTopLayout.addTab(homeTopLayout.newTab().setText(title));
        }

//        ArrayList<Fragment> list=new ArrayList<>();
//        for (int i=0;i<titles.length;i++){
//            list.add();
//        }
//
//        viewPagerLayout.setAdapter(new viewPagerAdapter(getFragmentManager(),list));
//        homeTopLayout.setupWithViewPager(viewPagerLayout);

        String con=new utils(getContext()).readFile(utils.fileName);//尝试读取本地文件
        if (con==null||con==""||con.isEmpty()){
            Log.d(TAG,"加载在线数据");
            loadingData(0);
        }else{
            Log.d(TAG,"加载本地数据");
            loadingData(1);
        }
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
                }
                Message message=new Message();
                message.what=what;
                message.obj=String.valueOf(content);
                handler.sendMessage(message);
            }
        }).start();
    }

    class homeHandle extends Handler{

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
                    listData.setHld(new utils(context).parseJson_home(String.valueOf(msg.obj),currentNewsType));
                    adapter=new fragment_home_adapter<homeListData>(context,listData.getHld());
                    listView.setAdapter(adapter);
                    break;
                case 1:
                    Log.d("加载本地数据完成",String.valueOf(msg.obj));
                    listData.setHld(new utils(context).parseJson_home(String.valueOf(msg.obj),currentNewsType));
                    adapter=new fragment_home_adapter<homeListData>(context,listData.getHld());
                    listView.setAdapter(adapter);
                    break;
                case 2:
                    Log.d("继续加载数据完成",String.valueOf(msg.obj));
                    if (!String.valueOf(msg.obj).equals("")){
                        new utils(context).saveFile(utils.fileName,String.valueOf(msg.obj));//每加载一次在线数据，就保存到本地
                        ArrayList<homeListData> tempList= new utils(context).parseJson_home(String.valueOf(msg.obj),currentNewsType);
                        listData.getHld().addAll(tempList);
                        adapter.notifyDataSetChanged();
                    }else{
                        Toast.makeText(context,"网络故障",Toast.LENGTH_LONG).show();
                    }
                    break;
            }
            isLoadingData=false;
        }
    }
}
