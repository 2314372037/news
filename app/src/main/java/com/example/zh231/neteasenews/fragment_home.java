package com.example.zh231.neteasenews;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Toast;
import android.support.design.widget.TabLayout;

import com.example.zh231.neteasenews.adapter.fragment_home_adapter;
import com.example.zh231.neteasenews.adapter.viewPagerAdapter;
import com.example.zh231.neteasenews.customView.homeListView;
import com.example.zh231.neteasenews.bean.ListData;
import com.example.zh231.neteasenews.bean.homeListData;

import java.lang.ref.WeakReference;
import java.util.ArrayList;



public class fragment_home extends Fragment {

    private TabLayout homeTopLayout=null;
    private ViewPager viewPagerLayout=null;
    private Button search_Btn=null;
    private String args1Key="newsType";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_fragment_home, container, false);

        homeTopLayout = view.findViewById(R.id.homeTopLayout);

        viewPagerLayout = view.findViewById(R.id.viewPagerLayout);

        //listView = (homeListView)view.findViewById(R.id.newsListView);

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

        return view; //返回给调用者
    }

    /**
     * 初始化相关
     */
    public void initData(){

        String titles[]={"新闻","娱乐","体育","财经","军事","科技","手机","数码","时尚","游戏","教育","健康","旅游"};

        ArrayList<Fragment> list=new ArrayList<>();
        for (int i=0;i<titles.length;i++){
            fragment_home_list fhl=new fragment_home_list();
            Bundle bundle=new Bundle();
            bundle.putString(args1Key,String.valueOf(utils.newsTypeCode.values()[i]));
            fhl.setArguments(bundle);
            list.add(fhl);
        }

        viewPagerLayout.setAdapter(new viewPagerAdapter(getFragmentManager(),list,titles));
        homeTopLayout.setupWithViewPager(viewPagerLayout);


    }




}
