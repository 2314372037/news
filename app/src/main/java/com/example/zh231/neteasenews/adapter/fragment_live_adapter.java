package com.example.zh231.neteasenews.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.zh231.neteasenews.R;
import com.example.zh231.neteasenews.bean.liveListData;

import java.util.ArrayList;

public class fragment_live_adapter<T> extends commonAdapter<T> {

    public fragment_live_adapter(Context context, ArrayList<T> data) {
        super(context, data);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder=ViewHolder.get(mContext,convertView,parent, R.layout.live_list_layout,position);

        liveListData lld=(liveListData)data.get(position);

        ImageView image=viewHolder.getView(R.id.liveImage);
        TextView title = viewHolder.getView(R.id.liveTitle);
        TextView source = viewHolder.getView(R.id.liveSource);
        TextView userCount = viewHolder.getView(R.id.liveUserCount);
        TextView state = viewHolder.getView(R.id.liveState);
        ImageView stateImg1=viewHolder.getView(R.id.liveStateImg1);
        ImageView stateImg2=viewHolder.getView(R.id.liveStateImg2);

        title.setText(lld.getRoomName());
        source.setText(lld.getSource());

        int UserCount=lld.getUserCount();
        if (UserCount>=10000){
            String str=String.valueOf(UserCount);
            userCount.setText(str.substring(0,str.length()-4)+"万人");
        }else{
            userCount.setText(UserCount+"人");
        }

        Glide.with(mContext)
                .load(lld.getPcImage())
                .into(image);
        if (lld.getLiveStatus()==1){//目前状态1为正在直播
            state.setText("正在直播");
            stateImg1.setVisibility(View.VISIBLE);
            stateImg2.setVisibility(View.GONE);
        }else{
            state.setTextColor(mContext.getResources().getColor(R.color.newsTypeColor));
            state.setText("回顾");//2为直播完成，可以回顾
            stateImg1.setVisibility(View.GONE);
            stateImg2.setVisibility(View.VISIBLE);
        }

        return viewHolder.getConvertView();
    }
}
