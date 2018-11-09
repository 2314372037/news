package com.example.zh231.neteasenews.adapter;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.zh231.neteasenews.R;
import com.example.zh231.neteasenews.customView.imageButton;
import com.example.zh231.neteasenews.jsonParse.videoListData;
import com.example.zh231.neteasenews.utils;

import java.util.ArrayList;

public class fragment_video_adapter<T> extends commonAdapter<T>{


    public fragment_video_adapter(Context context, ArrayList<T> data) {
        super(context, data);

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder= ViewHolder.get(mContext,convertView,parent, R.layout.video_list_layout,position);

        LinearLayout videoBtnLayout = viewHolder.getView(R.id.videoButtonLayout);

        imageButton ib_share=new imageButton(mContext);
        imageButton ib_voteCount=new imageButton(mContext);
        imageButton ib_replyCount=new imageButton(mContext);

        ImageView videoImage = viewHolder.getView(R.id.videoImage);
        TextView videoTitle = viewHolder.getView(R.id.videoText);
        TextView videoLength = viewHolder.getView(R.id.videoLength);

//        TextView videoVoteCount = viewHolder.getView(R.id.videoVoteText);
//        TextView videoReplyCount = viewHolder.getView(R.id.videoReplyText);

        videoListData vld=(videoListData)data.get(position);

        videoTitle.setText(vld.getTitle());
        videoLength.setText(new utils(mContext).stringForTime(vld.getLength()));


        ib_share.setImage(R.mipmap.zm);
        ib_voteCount.setText(String.valueOf(vld.getVoteCount()));
        ib_replyCount.setText(String.valueOf(vld.getReplyCount()));
        Glide.with(mContext).load(vld.getFirstFrameImg()).into(videoImage);

        videoBtnLayout.addView(ib_voteCount);
        videoBtnLayout.addView(ib_replyCount);
        videoBtnLayout.addView(ib_share);

//        videoVoteCount.setText(String.valueOf(vld.getVoteCount()));
//        videoReplyCount.setText(String.valueOf(vld.getReplyCount()));

        return viewHolder.getConvertView();
    }
}
