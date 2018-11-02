package com.example.zh231.neteasenews;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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

import com.example.zh231.neteasenews.adapter.homeListAdapter;
import com.example.zh231.neteasenews.adapter.newsTypeAdapter;


public class fragment_home extends Fragment {

    private String[] newsTypeStrings={"头条","精选","娱乐","汽车","运动","平顶山","漫画","段子","北京","公开课","讲讲"};
    static int home_data_start=0;//逐层，
    static int home_data_count=20;//一次性获取的新闻条数


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getArguments();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_fragment_home, container, false);

        RelativeLayout statusBar = view.findViewById(R.id.statusBar);
        statusBar.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,new utils(getContext()).getStatusBarHeight()));
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView_news_type);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(new newsTypeAdapter(getContext(),newsTypeStrings));

        final String[] title=new String[utils.nd.size()];
        String[] replyCount=new String[utils.nd.size()];

        for (int i=0;i<utils.nd.size();i++){//取出list中的normalData数据
            title[i]=utils.nd.get(i).getTitle();
            replyCount[i]=String.valueOf(utils.nd.get(i).getReplyCount());
        }

        final homeListAdapter adapter = new homeListAdapter(getContext(),title,replyCount);
        ListView listView = view.findViewById(R.id.newsListView);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getContext(),title[position],Toast.LENGTH_SHORT).show();
            }
        });
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (firstVisibleItem+visibleItemCount==totalItemCount){
                    Log.d("fragment_home","数据加载中...");

                    //adapter.notifyDataSetChanged();
                }
            }
        });

//        home_data_start=home_data_end;
//        home_data_end=home_data_end+home_data_end;
        return view;
    }

}
