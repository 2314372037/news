package com.example.zh231.neteasenews.customView;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;

import com.example.zh231.neteasenews.R;

public class homeListView extends ListView {

    private Context context;
    private int headlerHeight=0;
    private int downY;
    private int upY;
    private int moveY;

    public homeListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context=context;
        initView();
    }

//    @Override
//    public boolean onTouchEvent(MotionEvent ev) {
//        switch (ev.getAction()){
//            case MotionEvent.ACTION_DOWN:
//                downY=(int) ev.getY();
//                break;
//            case MotionEvent.ACTION_MOVE:
//                moveY=(int) ev.getY();
//                if (moveY-downY>headlerHeight*2){
//                    Log.d("-----------------------","释放刷新");
//                    Log.d("-----------------------","");
//                }
//                break;
//            case MotionEvent.ACTION_UP:
//                upY=(int) ev.getY();
//                break;
//        }
//        return super.onTouchEvent(ev);
//    }



    private void initView(){
        final View header= LayoutInflater.from(context).inflate(R.layout.listview_top_loading,null);
        final View footer=LayoutInflater.from(context).inflate(R.layout.listview_bottom_loading,null);
        header.measure(0,0);
        headlerHeight = header.getMeasuredHeight();
        header.setPadding(0,-headlerHeight,0,0);
        Log.d("homeListView",""+headlerHeight);
        this.addHeaderView(header);
        this.addFooterView(footer);
    }

}
