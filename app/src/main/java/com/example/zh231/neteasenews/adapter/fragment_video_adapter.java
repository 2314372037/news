package com.example.zh231.neteasenews.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.zh231.neteasenews.R;
import com.example.zh231.neteasenews.jsonParse.videoListData;

import java.util.ArrayList;

public class fragment_video_adapter<T> extends commonAdapter<T>{


    public fragment_video_adapter(Context context, ArrayList<T> data) {
        super(context, data);

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder= ViewHolder.get(mContext,convertView,parent, R.layout.video_list_layout,position);
        ImageView videoimage = viewHolder.getView(R.id.videoImage);
        TextView videotitle = viewHolder.getView(R.id.videoText);
        TextView videoLength = viewHolder.getView(R.id.videoLength);
        TextView videoVoteCount = viewHolder.getView(R.id.videoVoteText);
        TextView videoReplyCount = viewHolder.getView(R.id.videoReplyText);

        videoListData vld=(videoListData)data.get(position);
        videotitle.setText(vld.getTitle());
        videoLength.setText(String.valueOf(vld.getLength()/60)+"分");
        videoVoteCount.setText(String.valueOf(vld.getVoteCount()));
        videoReplyCount.setText(String.valueOf(vld.getReplyCount()));
        Glide.with(mContext).load(vld.getFirstFrameImg()).into(videoimage);

        return viewHolder.getConvertView();
    }
}
