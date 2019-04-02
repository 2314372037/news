package com.example.zh231.neteasenews.customView;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.zh231.neteasenews.R;

import org.w3c.dom.Text;

public class homeListView extends ListView {

    private Context context;
    private int headlerHeight=0;
    private int downY;
    private int upY;
    private int moveY;
    private View header;
    private View footer;
    private TextView textView;
    private ProgressBar refreshProgressBar;

    private final int down_refresh = 1;
    private final int releae_refresh = 2;
    private final int refershing = 3;
    private int currentState = down_refresh;

    public homeListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context=context;
        initView();
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                downY=(int) ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                moveY=(int) ev.getY();
                int startH=moveY-downY;
                if (startH>0&&getFirstVisiblePosition()==0&&currentState!=refershing){
                    int paddingTop = -headlerHeight + startH;
                    if (paddingTop<0){
                        currentState=down_refresh;
                        textView.setText("下拉刷新");
                    }else if (paddingTop>0){
                        currentState=releae_refresh;
                        textView.setText("释放刷新");
                    }
                    if (paddingTop>2*headlerHeight){
                        header.setPadding(0, 2*headlerHeight, 0, 0);
                    }else{
                        header.setPadding(0, paddingTop, 0, 0);
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                upY=(int) ev.getY();
                if (currentState==releae_refresh){
                    currentState=refershing;
                    header.setPadding(0,0,0,0);
                    textView.setText("刷新中");
                    refreshProgressBar.setVisibility(VISIBLE);
                    if (onRefreshing!=null){
                        onRefreshing.refresh();
                    }
                }else if (currentState == down_refresh) {
                    header.setPadding(0, -headlerHeight, 0, 0);
                }
                break;
        }
        return super.onTouchEvent(ev);
    }

    public void finishRefresh(){
        currentState=down_refresh;
        header.setPadding(0,-headlerHeight,0,0);
        refreshProgressBar.setVisibility(GONE);
        //刷新完成动画
    }

    public interface OnRefreshing{
        void refresh();
    }

    public OnRefreshing onRefreshing;

    public void setOnRefreshing(OnRefreshing onRefreshing){
        this.onRefreshing=onRefreshing;
    }

    private void initView(){
        header = LayoutInflater.from(context).inflate(R.layout.listview_top_loading,null);
        footer = LayoutInflater.from(context).inflate(R.layout.listview_bottom_loading,null);
        header.measure(0,0);
        headlerHeight = header.getMeasuredHeight();
        header.setPadding(0,-headlerHeight,0,0);
        Log.d("homeListView",""+headlerHeight);
        textView=header.findViewById(R.id.refreshText);
        refreshProgressBar=header.findViewById(R.id.refreshProgressBar);
        this.addHeaderView(header);
        this.addFooterView(footer);
    }

}
