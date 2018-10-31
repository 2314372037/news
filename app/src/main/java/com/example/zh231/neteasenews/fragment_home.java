package com.example.zh231.neteasenews;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;


public class fragment_home extends Fragment {

    private String[] newsTypeStrings={"头条","军事","视频","娱乐","体育","新时代","要闻","段子","北京","公开课","讲讲"};
    private String ARG_PARAM1 = "param1";
    private String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fragment_home, container, false);
        RelativeLayout statusBar = view.findViewById(R.id.statusBar);
        statusBar.setLayoutParams(
                new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                new utils().getStatusBarHeight(getContext())));
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView_news_type);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(new newsTypeAdapter(getContext(),newsTypeStrings));

        return view;
    }
}
