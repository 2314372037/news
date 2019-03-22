package com.example.zh231.neteasenews.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.example.zh231.neteasenews.R;
import com.example.zh231.neteasenews.jsonParse.homeListData;

import java.util.ArrayList;

public class fragment_home_adapter<T> extends commonAdapter<T> {

    public fragment_home_adapter(Context context, ArrayList<T> data){
        super(context,data);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder=ViewHolder.get(mContext,convertView,parent, R.layout.news_list_layout,position);

        ImageView image=viewHolder.getView(R.id.image);
        TextView title = viewHolder.getView(R.id.title);
        TextView source = viewHolder.getView(R.id.source);
        TextView replyCount = viewHolder.getView(R.id.replyCount);

        String replyText =((homeListData) data.get(position)).getReplyCount()+"跟帖";
        title.setText(((homeListData)data.get(position)).getTitle());
        source.setText(((homeListData) data.get(position)).getSource());
        replyCount.setText(replyText);
        Glide.with(mContext)
                .load(((homeListData)data.get(position)).getImgsrc())
                .into(image);

        return viewHolder.getConvertView();
    }
}
