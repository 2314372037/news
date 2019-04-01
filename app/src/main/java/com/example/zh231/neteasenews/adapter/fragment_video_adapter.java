package com.example.zh231.neteasenews.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.zh231.neteasenews.R;
import com.example.zh231.neteasenews.bean.videoListData;
import com.example.zh231.neteasenews.utils;

import java.util.ArrayList;

public class fragment_video_adapter<T> extends commonAdapter<T>{


    public fragment_video_adapter(Context context, ArrayList<T> data) {
        super(context, data);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder= ViewHolder.get(mContext,convertView,parent, R.layout.video_list_layout,position);

        ImageView videoImage = viewHolder.getView(R.id.videoImage);
        TextView videoTitle = viewHolder.getView(R.id.videoText);
        TextView videoLength = viewHolder.getView(R.id.videoLength);

        videoListData vld=(videoListData)data.get(position);

        videoTitle.setText(vld.getTitle());
        videoLength.setText(new utils(mContext).stringForTime(vld.getLength()));
        Glide.with(mContext).load(vld.getCover()).into(videoImage);

        RelativeLayout ib_voteCount=viewHolder.getView(R.id.ib_voteCount);
        RelativeLayout ib_replyCount=viewHolder.getView(R.id.ib_replyCount);

        TextView voteCountText=viewHolder.getView(R.id.voteCountText);
        TextView replyCountText=viewHolder.getView(R.id.replyCountText);


        voteCountText.setText(String.valueOf(vld.getVotecount()));
        replyCountText.setText(String.valueOf(vld.getReplyCount()));

        return viewHolder.getConvertView();
    }
}
