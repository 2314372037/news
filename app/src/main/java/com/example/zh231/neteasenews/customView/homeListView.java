package com.example.zh231.neteasenews.customView;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;

import com.example.zh231.neteasenews.R;

public class homeListView extends ListView {

    private Context context;
    private View footer;

    public homeListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context=context;
        initView();
    }

    private void initView(){
        footer = LayoutInflater.from(context).inflate(R.layout.listview_bottom_loading,null);
        this.addFooterView(footer);
    }

}
